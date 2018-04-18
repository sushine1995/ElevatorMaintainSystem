package com.vino.maintain.service;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.MaintainOrder;
import com.vino.maintain.repository.EmployeeRepository;
import com.vino.maintain.repository.MaintainOrderRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("maintainOrderService")
public class MaintainOrderServiceImpl extends AbstractBaseServiceImpl<MaintainOrder, Long> implements MaintainOrderService {
	
	@Autowired
	private MaintainOrderRepository maintainOrderRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ElevatorRecordService elevatorRecordService;
	/**
     * 创建动态查询条件组合.
     */
    private Specification<MaintainOrder> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
			@Override
			public Predicate toPredicate(Root<MaintainOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String employeeName=(String) searchParams.get("employeeName");
				String maintainTypeName=(String) searchParams.get("maintainTypeName");
				String groupName=(String) searchParams.get("groupName");
				String finalTimeRange=(String) searchParams.get("finalTimeRange");
				String finished=(String)searchParams.get("finished");
				if(employeeName!=null&&!"".equals(employeeName)){
					Predicate condition=cb.like(root.get("employee").get("name").as(String.class),"%"+searchParams.get("employeeName")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(maintainTypeName!=null&&!"".equals(maintainTypeName)){
					Predicate condition=cb.like(root.get("maintainType").get("name").as(String.class),"%"+searchParams.get("maintainTypeName")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(finished!=null&&!"".equals(finished)){
					Predicate condition=cb.equal(root.get("finished").as(Boolean.class),finished.equals("true")?Boolean.TRUE:Boolean.FALSE);
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(groupName!=null&&!"".equals(groupName)){
					
					Predicate condition=cb.like(root.get("elevatorRecord").get("group").get("name").as(String.class),"%"+searchParams.get("groupName")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(finalTimeRange!=null&&!"".equals(finalTimeRange)){		
					//按前端传递过来的时间格式进行解析
					String finalTimeStartStr=finalTimeRange.split(" - ")[0]+":00:00:00";
					String finalTimeEndStr=finalTimeRange.split(" - ")[1]+":23:59:59";
					SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy:hh:mm:ss");
					try {
						Date finalTimeStart = format.parse(finalTimeStartStr);
						Date finalTimeEnd=format.parse(finalTimeEndStr);
						Predicate condition=cb.between(root.get("finalTime").as(Date.class),finalTimeStart, finalTimeEnd);
						if(null==allCondition)
							allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
						else
							allCondition=cb.and(allCondition,condition);
						
					} catch (ParseException e) {
						e.printStackTrace();
						Logger log =LoggerFactory.getLogger(this.getClass());
						log.error("finalTime 转换时间出错");
					}
					
				
				}				
			
											
				
				return allCondition;
			}
        	
        };
        return spec;
    }
    
    
	@Override
	public Page<MaintainOrder> findMaintainOrderByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return maintainOrderRepository.findAll(buildSpecification(searchParams), pageable);
	}
	@Override
	public void update(MaintainOrder obj) {
		MaintainOrder obj2=maintainOrderRepository.findOne(obj.getId());
	/*	if(null!=obj.getName()||!"".equals(obj))
			obj2.setName(obj.getName());*/
		try {
			@SuppressWarnings("unchecked")
			Class<MaintainOrder> clazz=(Class<MaintainOrder>) Class.forName("com.vino.maintain.entity.MaintainOrder");
			Method[] methods=clazz.getDeclaredMethods();
			for(Method m:methods){
				 if(m.getName().substring(0, 3).equals("get")){					
					Object value=m.invoke(obj);
					if(value!=null){
					Method setMethod=clazz.getDeclaredMethod("set"+m.getName().substring(3, 4).toUpperCase()+m.getName().substring(4),m.getReturnType());
					setMethod.invoke(obj2, value);
					}
				}		
			}
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void saveWithCreateTimeAndCreator(MaintainOrder maintainOrder) {
		
		String no=this.calculateNo();
		maintainOrder.setNo(no);
		maintainOrder.setCreateTime(new Date());
		maintainOrder.setCreatorId(userService.getCurrentUser().getId());
		maintainOrder.setCreatorName(userService.getCurrentUser().getUsername());
		
		maintainOrderRepository.save(maintainOrder);
	}
	public String calculateNo(){
		//生成工单号：MO+年+月+日+时间毫秒数(8位，不够的用零补齐)
				DateTime current=new DateTime();
				int year=current.getYear();
				int month=current.getMonthOfYear();
				int day=current.getDayOfMonth();
				int millis=current.getMillisOfDay();
				String millisStr=String.valueOf(millis);
				StringBuilder sb=new StringBuilder(millisStr);
				if(millisStr.length()<8){
					int temp=8-millisStr.length();
					for(int i=0;i<temp;i++){
						sb.insert(0, "0");
					}
				}
				millisStr=sb.toString();
				String no="MO"+year;
				if(month<10)
					no=no+"0"+month;
				else
					no=no+month;
				if(day<10)
					no=no+"0"+day;
				else
					no=no+day;
				no=no+millisStr;
			return no;
	}
	

	@Override
	public void save(MaintainOrder maintainOrder) {
		String no=this.calculateNo();
		maintainOrder.setNo(no);
		super.save(maintainOrder);
	}


	/**
	 * 通过finished与finalTime来查询超期任务
	 */
	@Override
	public Page<MaintainOrder> findExpiredOrderByGroup(Pageable pageable, final Long groupId) {
		if(groupId==null)
			return null;
		 Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
				@Override
				public Predicate toPredicate(Root<MaintainOrder> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {		
					//查找过期工单并且未完成
					Predicate condition=cb.lessThan(root.get("finalTime").as(Date.class), new LocalDate(new DateTime()).toDate());	
					Predicate condition2=cb.equal(root.get("elevatorRecord").get("group").get("id").as(Long.class), groupId);
					Predicate condition3=cb.equal(root.get("finished").as(Boolean.class), false);
					condition=cb.and(condition,condition2,condition3);
					return condition;
				}
	        	
	        };
			return maintainOrderRepository.findAll(spec, pageable);
	}
	
	@Override
	public Page<MaintainOrder> findFinishedOrderByGroup(Pageable pageable, final Long groupId) {
		if(groupId==null)
			return null;
		 Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
				@Override
				public Predicate toPredicate(Root<MaintainOrder> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {				
					Predicate condition=cb.equal(root.get("finished").as(Boolean.class),true);	
					Predicate condition2=cb.equal(root.get("elevatorRecord").get("group").get("id").as(Long.class), groupId);
					condition=cb.and(condition,condition2);
					return condition;
				}
	        	
	        };
			return maintainOrderRepository.findAll(spec, pageable);
	}
	/**
	 * 截止日期当日，算是未超期
	 */
	@Override
	public Page<MaintainOrder> findNotFinishedOrderByGroup(Pageable pageable, final Long groupId) {
		if(groupId==null)
			return null;
		Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
			@Override
			public Predicate toPredicate(Root<MaintainOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("finished").as(Boolean.class),false);	
				Predicate condition2=cb.equal(root.get("elevatorRecord").get("group").get("id").as(Long.class), groupId);
				Predicate condition3=cb.greaterThanOrEqualTo(root.get("finalTime").as(Date.class), new LocalDate(new DateTime()).toDate());	
				condition=cb.and(condition,condition2,condition3);
				return condition;
			}
        	
        };
		return maintainOrderRepository.findAll(spec, pageable);
	}
	@Override
	public String acceptOrder(long id, long employeeId) {
		
		Employee employee=employeeRepository.findOne(employeeId);
		if(employee==null)
			return "employeeNotExist";
		MaintainOrder maintainOrder=maintainOrderRepository.findOne(id);
		if(maintainOrder==null)
			return "maintainOrderNotExist";
		if(maintainOrder.getReceivingTime()!=null)
			return "repeat";
		maintainOrder.setReceivingTime(new Date());
		maintainOrder.setEmployee(employee);
		return "success";
	}
	@Override
	public String cancelOrder(long id, long employeeId) {
		Employee employee=employeeRepository.findOne(employeeId);
		if(employee==null)
			return "employeeNotExist";
		
		MaintainOrder maintainOrder=maintainOrderRepository.findOne(id);
		if(maintainOrder==null)
			return "maintainOrderNotExist";
		if(!employee.equals(maintainOrder.getEmployee()))
			return "notMatch";//该员工没有对应的工单
		if(maintainOrder.getReceivingTime()==null)
			return "notAccept";//还未接单
		//成功
		maintainOrder.setReceivingTime(null);
		maintainOrder.setEmployee(null);
		return "success";
	}
	@Override
	public String signIn(long id, String signInAddress) {
		MaintainOrder maintainOrder=maintainOrderRepository.findOne(id);
		if(maintainOrder==null)
			return "maintainOrderNotExist";
		maintainOrder.setSignInAddress(signInAddress);
		maintainOrder.setSignInTime(new Date());
		return "success";
	}

	/**
	 * 查找用户已经接单并且未完成的工单
	 * 根据finished receivingTime employeeId查找
	 */
	@Override
	public Page<MaintainOrder> findAcceptedOrder(Pageable pageable, final Long employeeId) {
		if(employeeId==null)
			return null;
		Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
			@Override
			public Predicate toPredicate(Root<MaintainOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("finished").as(Boolean.class),false);	
				Predicate condition2=cb.isNotNull(root.get("receivingTime").as(Date.class));	
				Predicate condition3=cb.equal(root.get("employee").get("id").as(Long.class),employeeId);	
				condition=cb.and(condition,condition2,condition3);
				return condition;
			}
        	
        };
		return maintainOrderRepository.findAll(spec, pageable);
	}


	@Override
	public Page<MaintainOrder> findSignedOrder(Pageable pageable, final Long employeeId) {
		if(employeeId==null)
			return null;
		Specification<MaintainOrder> spec = new Specification<MaintainOrder>(){           
			@Override
			public Predicate toPredicate(Root<MaintainOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("finished").as(Boolean.class),false);	
				Predicate condition2=cb.isNotNull(root.get("receivingTime").as(Date.class));	
				Predicate condition3=cb.equal(root.get("employee").get("id").as(Long.class),employeeId);	
				Predicate condition4=cb.isNotNull(root.get("signInAddress").as(String.class));
				condition=cb.and(condition,condition2,condition3,condition4);
				return condition;
			}
        	
        };
		return maintainOrderRepository.findAll(spec, pageable);
	}


	@Override
	public String feedback(MaintainOrder maintainOrder) {
		if(maintainOrder==null)
			throw new NullPointerException();
		MaintainOrder order=maintainOrderRepository.findOne(maintainOrder.getId());
		if(maintainOrder.getFinished()){			
			long elevatorId=order.getElevatorRecord().getId();
			ElevatorRecord elevator=elevatorRecordService.findOne(elevatorId);
			elevator.setLastMaintainTime(new Date());//更新上次维保时间
		}
		maintainOrder.setSignOutTime(new Date());//设置签退时间
		
		update(maintainOrder);
		
		return "success";
	}


	

}
