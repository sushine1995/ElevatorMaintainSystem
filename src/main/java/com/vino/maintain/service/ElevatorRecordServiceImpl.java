package com.vino.maintain.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.Group;
import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.repository.ElevatorRecordRepository;
import com.vino.maintain.repository.GroupRepository;
import com.vino.maintain.repository.MaintainPlanRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("elevatorRecordService")
public class ElevatorRecordServiceImpl extends AbstractBaseServiceImpl<ElevatorRecord, Long> implements ElevatorRecordService{
	@Autowired
	private ElevatorRecordRepository elevatorRecordRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private MaintainPlanRepository maintainPlanRepository;
	@Autowired
	private UserService userService;
	/**
     * 创建动态查询条件组合.
     */
    private Specification<ElevatorRecord> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<ElevatorRecord> spec = new Specification<ElevatorRecord>(){           
			@Override
			public Predicate toPredicate(Root<ElevatorRecord> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String address=(String) searchParams.get("address");
				String unit=(String) searchParams.get("unit");
				String modelNumber=(String) searchParams.get("modelNumber");
				String groupName=(String) searchParams.get("groupName");
				//String createTimeRange=(String) searchParams.get("createTimeRange");
				
				
				
				if(address!=null&&!"".equals(address)){
					Predicate condition=cb.like(root.get("address").as(String.class),"%"+searchParams.get("address")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(unit!=null&&!"".equals(unit)){
					Predicate condition=cb.like(root.get("unit").as(String.class),"%"+searchParams.get("unit")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(modelNumber!=null&&!"".equals(modelNumber)){
					Predicate condition=cb.like(root.get("modelNumber").as(String.class),"%"+searchParams.get("modelNumber")+"%");
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
											
				
				return allCondition;
			}
        	
        };
        return spec;
    }
	
    
    
	@Override
	public Page<ElevatorRecord> findElevatorRecordByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return elevatorRecordRepository.findAll(buildSpecification(searchParams), pageable);
	}
	@Override
	public void update(ElevatorRecord obj) {
		ElevatorRecord obj2=elevatorRecordRepository.findOne(obj.getId());
	/*	if(null!=obj.getName()||!"".equals(obj))
			obj2.setName(obj.getName());*/
		try {
			@SuppressWarnings("unchecked")
			Class<ElevatorRecord> clazz=(Class<ElevatorRecord>) Class.forName("com.vino.maintain.entity.ElevatorRecord");
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
	public void saveWithCreateTimeAndCreator(ElevatorRecord elevatorRecord) {
		elevatorRecord.setCreateTime(new Date());
		elevatorRecord.setCreatorId(userService.getCurrentUser().getId());
		elevatorRecord.setCreatorName(userService.getCurrentUser().getUsername());
		elevatorRecordRepository.save(elevatorRecord);
	}


	@Override
	public void bindMaintainPlan(long[] elevatorRecordIds, long maintainPlanId) {
		MaintainPlan plan=maintainPlanRepository.findOne(maintainPlanId);
		Group group=plan.getGroup();
		for(long id:elevatorRecordIds){
			ElevatorRecord e=elevatorRecordRepository.findOne(id);
			if(group!=null){//将电梯负责小组绑定为合同的负责小组
				e.setGroup(group);
			}
			e.setMaintainPlan(plan);
		}
	}
	@Override
	public Page<ElevatorRecord> findElevatorRecordByGroup(Pageable pageable,final Long groupId) {
		if(groupId==null)
			return null;
		Specification<ElevatorRecord> spec = new Specification<ElevatorRecord>(){           
			@Override
			public Predicate toPredicate(Root<ElevatorRecord> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("group").get("id").as(Long.class), groupId);				
				return condition;
			}
        	
        };
		return elevatorRecordRepository.findAll(spec, pageable);
	}
	/**
     * 为移动端创建动态查询条件组合.
     */
    private Specification<ElevatorRecord> buildSpecificationAPI(final String param) {
    	
		
        Specification<ElevatorRecord> spec = new Specification<ElevatorRecord>(){           
			@Override
			public Predicate toPredicate(Root<ElevatorRecord> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate condition1=cb.like(root.get("address").as(String.class),"%"+param+"%");
				Predicate condition2=cb.like(root.get("unit").as(String.class),"%"+param+"%");
				Predicate condition3=cb.like(root.get("no").as(String.class),"%"+param+"%");																		
				return cb.or(condition1,condition2,condition3);
			}
        	
        };
        return spec;
    }
	@Override
	public Page<ElevatorRecord> findElevatorRecordByCondition(String param, Pageable pageable) {
		// TODO Auto-generated method stub
		return elevatorRecordRepository.findAll(buildSpecificationAPI(param), pageable);
	}
}
