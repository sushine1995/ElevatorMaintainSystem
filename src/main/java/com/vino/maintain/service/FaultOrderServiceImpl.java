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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.repository.EmployeeRepository;
import com.vino.maintain.repository.FaultOrderRepository;
import com.vino.maintain.service.notice.EmployeeNoticeService;
import com.vino.maintain.service.notice.UserNoticeService;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("faultOrderService")
public class FaultOrderServiceImpl extends AbstractBaseServiceImpl<FaultOrder, Long> implements FaultOrderService{
	@Autowired
	private FaultOrderRepository faultOrderRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ElevatorRecordService elevatorRecordService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmployeeNoticeService employeeNoticeService;
	@Autowired
	private UserNoticeService userNoticeService;
	/**
     * 创建动态查询条件组合.
     */
    private Specification<FaultOrder> buildSpecification(final Map<String,Object> searchParams) {
        Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String employeeName=(String) searchParams.get("employeeName");
				
				String groupName=(String) searchParams.get("groupName");
				String occuredTimeRange=(String) searchParams.get("occuredTimeRange");
				String fixed=(String)searchParams.get("fixed");
				if(employeeName!=null&&!"".equals(employeeName)){
					Predicate condition=cb.like(root.get("employee").get("name").as(String.class),"%"+searchParams.get("employeeName")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
		
				if(fixed!=null&&!"".equals(fixed)){
					Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),fixed.equals("true")?Boolean.TRUE:Boolean.FALSE);
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
				if(occuredTimeRange!=null&&!"".equals(occuredTimeRange)){		
					//按前端传递过来的时间格式进行解析
					String occuredTimeStartStr=occuredTimeRange.split(" - ")[0]+":00:00:00";
					String occuredTimeEndStr=occuredTimeRange.split(" - ")[1]+":23:59:59";
					SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy:hh:mm:ss");
					try {
						Date occuredTimeStart = format.parse(occuredTimeStartStr);
						Date occuredTimeEnd=format.parse(occuredTimeEndStr);
						Predicate condition=cb.between(root.get("occuredTime").as(Date.class),occuredTimeStart, occuredTimeEnd);
						if(null==allCondition)
							allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
						else
							allCondition=cb.and(allCondition,condition);
					} catch (ParseException e) {
						e.printStackTrace();
						Logger log =LoggerFactory.getLogger(this.getClass());
						log.error("occuredTime 转换时间出错");
					}
				}				
				return allCondition;
			}
        };
        return spec;
    }
	@Override
	public Page<FaultOrder> findFaultOrderByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return faultOrderRepository.findAll(buildSpecification(searchParams), pageable);
	}
	@Override
	public void update(FaultOrder obj) {
		FaultOrder obj2=faultOrderRepository.findOne(obj.getId());
	
		try {
			@SuppressWarnings("unchecked")
			Class<FaultOrder> clazz=(Class<FaultOrder>) Class.forName("com.vino.maintain.entity.FaultOrder");
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
	public void saveWithCreateTimeAndCreator(FaultOrder faultOrder) {
		String no=this.calculateNo();
		faultOrder.setNo(no);
		faultOrder.setOccuredTime(new Date());//设置任务发布时间
		faultOrder.setCreateTime(new Date());
		faultOrder.setCreatorId(userService.getCurrentUser().getId());
		faultOrder.setCreatorName(userService.getCurrentUser().getUsername());
		faultOrder=faultOrderRepository.save(faultOrder);
		//根据员工的CID给员工发送消息
		ElevatorRecord elevator=elevatorRecordService.findOne(faultOrder.getElevatorRecord().getId());
		
		employeeNoticeService.pushFaultOrderNotice(elevator.getGroup().getId(),faultOrder.getId());
		
	}
	public String calculateNo(){
		//生成工单号：FO+年+月+日+时间毫秒数(8位，不够的用零补齐)
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
				String no="FO"+year;
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
	public Page<FaultOrder> findFixedOrder(Pageable pageable) {
		 Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
				@Override
				public Predicate toPredicate(Root<FaultOrder> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {				
					Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),true);				
					return condition;
				}
	        	
	        };
			return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public Page<FaultOrder> findNotFixedOrder(Pageable pageable) {
		Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),false);				
				return condition;
			}
        	
        };
		return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public Page<FaultOrder> findFixedOrderByGroup(Pageable pageable, final long groupId) {
		 Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
				@Override
				public Predicate toPredicate(Root<FaultOrder> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {				
					Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),true);	
					Predicate condition2=cb.equal(root.get("elevatorRecord").get("group").get("id").as(Long.class), groupId);
					condition=cb.and(condition,condition2);
					return condition;
				}
	        	
	        };
			return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public Page<FaultOrder> findNotFixedOrderByGroup(Pageable pageable, final long groupId) {
		Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),false);	
				Predicate condition2=cb.equal(root.get("elevatorRecord").get("group").get("id").as(Long.class), groupId);
				condition=cb.and(condition,condition2);
				return condition;
			}
        	
        };
		return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public String acceptOrder(long id, long employeeId) {
		Employee employee=employeeRepository.findOne(employeeId);
		if(employee==null)
			return "employeeNotExist";
		FaultOrder faultOrder=faultOrderRepository.findOne(id);
		if(faultOrder==null)
			return "faultOrderNotExist";
		if(faultOrder.getReceivingTime()!=null)
			return "repeat";
		faultOrder.setReceivingTime(new Date());
		faultOrder.setEmployee(employee);
		return "success";
	}
	@Override
	public String cancelOrder(long id, long employeeId) {
		Employee employee=employeeRepository.findOne(employeeId);
		if(employee==null)
			return "employeeNotExist";
		
		FaultOrder faultOrder=faultOrderRepository.findOne(id);
		if(faultOrder==null)
			return "faultOrderNotExist";
		if(!employee.equals(faultOrder.getEmployee()))
			return "notMatch";//该员工没有对应的工单
		if(faultOrder.getReceivingTime()==null)
			return "notAccept";//还未接单
		//成功
		faultOrder.setReceivingTime(null);
		faultOrder.setEmployee(null);
		return "success";
	}
	@Override
	public String signIn(long id, String signInAddress) {
		FaultOrder faultOrder=faultOrderRepository.findOne(id);
		if(faultOrder==null)
			return "faultOrderNotExist";
		faultOrder.setSignInAddress(signInAddress);
		faultOrder.setSignInTime(new Date());
		return "success";
	}
	@Override
	public Page<FaultOrder> findAcceptedOrder(Pageable pageable, final long employeeId) {
		Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),false);	
				Predicate condition2=cb.isNotNull(root.get("receivingTime").as(Date.class));//已接单	
				Predicate condition3=cb.equal(root.get("employee").get("id").as(Long.class),employeeId);
				condition=cb.and(condition,condition2,condition3);
				return condition;
			}
        	
        };
		return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public Page<FaultOrder> findSignedOrder(Pageable pageable, final long employeeId) {
		
		Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("fixed").as(Boolean.class),false);	
				Predicate condition2=cb.isNotNull(root.get("receivingTime").as(Date.class));	
				Predicate condition3=cb.equal(root.get("employee").get("id").as(Long.class),employeeId);	
				Predicate condition4=cb.isNotNull(root.get("signInAddress").as(String.class));//已签到	
				condition=cb.and(condition,condition2,condition3,condition4);
				return condition;
			}
        	
        };
		return faultOrderRepository.findAll(spec, pageable);
	}
	@Override
	public void feedback(FaultOrder faultOrder) {
		faultOrder.setSignOutTime(new Date());//设置签退时间
		this.update(faultOrder);
		//如果反馈的时候已完成该工单，则发送通知
		if(faultOrder.getFixed()){
			log.info("故障工单完成，发通知给管理员");
			userNoticeService.sendFaultOrderFixedNotice(faultOrder);
		}
		
	}
	@Override
	public Page<FaultOrder> findFaultHistory(Pageable pageable, final long elevatorRecordId) {
		if(elevatorRecordId<=0)
			throw new IllegalArgumentException("elevatorRecordId 需要>0");
		Specification<FaultOrder> spec = new Specification<FaultOrder>(){           
			@Override
			public Predicate toPredicate(Root<FaultOrder> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("elevatorRecord").get("id").as(Long.class),elevatorRecordId);
				Predicate condition2=cb.equal(root.get("fixed").as(Boolean.class),true);
				return cb.and(condition,condition2);
			}
        	
        };
		return faultOrderRepository.findAll(spec, pageable);
	}
}
