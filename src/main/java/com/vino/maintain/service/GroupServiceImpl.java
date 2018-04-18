package com.vino.maintain.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.Group;
import com.vino.maintain.exception.GroupDuplicateException;
import com.vino.maintain.repository.GroupRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("groupService")
public class GroupServiceImpl extends AbstractBaseServiceImpl<Group, Long> implements GroupService{
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserService userService;
	  /**
     * 创建动态查询条件组合.
     */
    private Specification<Group> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<Group> spec = new Specification<Group>(){           
			@Override
			public Predicate toPredicate(Root<Group> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String name=(String) searchParams.get("name");
				String createTimeRange=(String) searchParams.get("createTimeRange");
				if(name!=null&&!"".equals(name)){
					Predicate condition=cb.like(root.get("name").as(String.class),"%"+searchParams.get("name")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(createTimeRange!=null&&!"".equals(createTimeRange)){			
					String createTimeStartStr=createTimeRange.split(" - ")[0]+":00:00:00";
					String createTimeEndStr=createTimeRange.split(" - ")[1]+":23:59:59";
					SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy:hh:mm:ss");
					System.out.println(createTimeStartStr);
					try {
						Date createTimeStart = format.parse(createTimeStartStr);
						Date createTimeEnd=format.parse(createTimeEndStr);
						Predicate condition=cb.between(root.get("createTime").as(Date.class),createTimeStart, createTimeEnd);
						if(null==allCondition)
							allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
						else
							allCondition=cb.and(allCondition,condition);
						
					} catch (ParseException e) {
						e.printStackTrace();
						Logger log =LoggerFactory.getLogger(this.getClass());
						log.error("createTime 转换时间出错");
					}
					
				
				}					
				return allCondition;
			}
        	
        };
        return spec;
    }
    
    
	@Override
	public Page<Group> findGroupByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return groupRepository.findAll(buildSpecification(searchParams), pageable);
	}


	@Override
	public void saveWithCheckDuplicate(Group group) throws GroupDuplicateException {
			if(group.getName()==null)
				return;
			//校验是否用户重复
	    	if(groupRepository.findByName(group.getName())!=null)
	    		throw new GroupDuplicateException();
	    	
	        group.setCreateTime(new Date());
	        if(userService.getCurrentUser()!=null)
	        	group.setCreatorName(userService.getCurrentUser().getUsername());
	        groupRepository.save(group);
	}


	@Override
	public void update(Group obj) {
		Group obj2=groupRepository.findOne(obj.getId());
	/*	if(null!=obj.getName()||!"".equals(obj))
			obj2.setName(obj.getName());*/
		try {
			@SuppressWarnings("unchecked")
			Class<Group> clazz=(Class<Group>) Class.forName("com.vino.maintain.entity.Group");
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

	/**
	 * 删除元素之前要先清除外键的依赖
	 */
	@Override
	public void delete(Long... ids) {
		//删除元素之前要先清除外键的依赖
		for(Long id:ids){
			Group group=groupRepository.findOne(id);
			List<Employee> employees=group.getEmployees();
			List<ElevatorRecord> elevatorRecords=group.getElevatorRecords();
			for(Employee employee:employees){
				employee.setGroup(null);
			}
			for(ElevatorRecord record:elevatorRecords){
				record.setGroup(null);
			}
			
			
		}
		
		super.delete(ids);
	}
	
	
}
