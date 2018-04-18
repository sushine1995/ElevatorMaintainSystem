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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.exception.MaintainPlanDuplicateException;
import com.vino.maintain.repository.MaintainOrderRepository;
import com.vino.maintain.repository.MaintainPlanRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Service
@Transactional
public class MaintainPlanServiceImpl extends AbstractBaseServiceImpl<MaintainPlan, Long> implements MaintainPlanService {
	@Autowired
	private MaintainPlanRepository maintainPlanRepository;
	@Autowired
	private MaintainOrderRepository maintainOrderRepository;
	@Autowired
	private UserService userService;
	/**
     * 创建动态查询条件组合.
     */
    private Specification<MaintainPlan> buildSpecification(final Map<String,Object> searchParams) {
        Specification<MaintainPlan> spec = new Specification<MaintainPlan>(){           
			@Override
			public Predicate toPredicate(Root<MaintainPlan> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String name=(String) searchParams.get("name");
				String no=(String) searchParams.get("no");
				String endDateRange=(String) searchParams.get("endDateRange");
				String groupName=(String) searchParams.get("groupName");
				if(name!=null&&!"".equals(name)){
					Predicate condition=cb.like(root.get("name").as(String.class),"%"+searchParams.get("name")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
		
				if(no!=null&&!"".equals(no)){
					Predicate condition=cb.equal(root.get("no").as(String.class),"%"+no+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(groupName!=null&&!"".equals(groupName)){
					
					Predicate condition=cb.like(root.get("group").get("name").as(String.class),"%"+searchParams.get("groupName")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(endDateRange!=null&&!"".equals(endDateRange)){		
					//按前端传递过来的时间格式进行解析
					String endDateStartStr=endDateRange.split(" - ")[0]+":00:00:00";
					String endDateEndStr=endDateRange.split(" - ")[1]+":23:59:59";
					SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy:hh:mm:ss");
					try {
						Date endDateStart = format.parse(endDateStartStr);
						Date endDateEnd=format.parse(endDateEndStr);
						Predicate condition=cb.between(root.get("endDate").as(Date.class),endDateStart, endDateEnd);
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
	public Page<MaintainPlan> findMaintainPlanByCondition(Map<String, Object> searchParams, Pageable pageable) {
		// TODO Auto-generated method stub
		return maintainPlanRepository.findAll(buildSpecification(searchParams), pageable);
	}
	@Override
	public void update(MaintainPlan obj) {
		MaintainPlan obj2=maintainPlanRepository.findOne(obj.getId());
	
		try {
			@SuppressWarnings("unchecked")
			Class<MaintainPlan> clazz=(Class<MaintainPlan>) Class.forName("com.vino.maintain.entity.MaintainPlan");
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
	public void saveWithCheckDuplicate(MaintainPlan maintainPlan) throws MaintainPlanDuplicateException {
		
		if(maintainPlan==null||maintainPlan.getName()==null)
			return ;
		MaintainPlan m=maintainPlanRepository.findByName(maintainPlan.getName());
		if(m!=null)
			throw new MaintainPlanDuplicateException();
		else{
			maintainPlan.setCreateTime(new Date());
			maintainPlan.setCreatorName(userService.getCurrentUser().getUsername());
			maintainPlanRepository.save(maintainPlan);
			//若立即生效，则生成保养工单
/*			if(Boolean.TRUE.equals(maintainPlan.getIsEffectiveImmediate())){
				//判断当前时间是上半月还是下半月
				DateTime current=new DateTime();
				DateTime lastDayOfCurrentMonth=current.dayOfMonth().withMaximumValue();//月末日期
				for(ElevatorRecord e:maintainPlan.getElevatorRecords()){//批量对所拥有的电梯设置保养任务
					MaintainOrder order=new MaintainOrder();
					order.setGroup(maintainPlan.getGroup());
					if(current.getDayOfMonth()<15){
						System.out.println(current.getDayOfMonth());
						//设置截止时间为14号23:59:59
						order.setFinalTime(new DateTime(current.getYear(),current.getMonthOfYear(),14,23,59,59).toDate());
					}else{
						//设置截止时间为月末的23:59:59
						order.setFinalTime(new DateTime(current.getYear(),current.getMonthOfYear(),lastDayOfCurrentMonth.getDayOfMonth(),
								23,59,59).toDate());
					}
					order.setElevatorRecord(e);
					order.setCreateTime(new Date());
					order.setCreatorName("保养计划创建");
				}
				
				
			}*/
		}
		
	}

}
