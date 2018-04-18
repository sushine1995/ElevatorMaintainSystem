package com.vino.maintain.service;

import java.lang.reflect.Field;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.Group;
import com.vino.maintain.exception.EmployeeDuplicateException;
import com.vino.maintain.repository.EmployeeRepository;
import com.vino.maintain.repository.GroupRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.PasswordHelper;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("employeeService")
public class EmployeeServiceImpl extends AbstractBaseServiceImpl<Employee, Long> implements EmployeeService{
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private PasswordHelper passwordHelper;
	@Autowired
	private UserService userService;
	  /**
     * 创建动态查询条件组合.
     */
    private Specification<Employee> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<Employee> spec = new Specification<Employee>(){           
			@Override
			public Predicate toPredicate(Root<Employee> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate allCondition = null;
				String name=(String) searchParams.get("name");
				String username=(String) searchParams.get("username");
				String createTimeRange=(String) searchParams.get("createTimeRange");
				if(name!=null&&!"".equals(name)){
					Predicate condition=cb.like(root.get("name").as(String.class),"%"+searchParams.get("name")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
				if(username!=null&&!"".equals(username)){
					Predicate condition=cb.like(root.get("username").as(String.class),"%"+searchParams.get("username")+"%");
					if(null==allCondition)
						allCondition=cb.and(condition);//此处初始化allCondition,若按cb.and(allCondition,condition)这种写法，会导致空指针
					else
						allCondition=cb.and(allCondition,condition);
					}
											
				if(createTimeRange!=null&&!"".equals(createTimeRange)){		
					//按前端传递过来的时间格式进行解析
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
	public Page<Employee> findEmployeeByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return employeeRepository.findAll(buildSpecification(searchParams), pageable);
	}

	
	@Override
	public void saveWithCheckDuplicate(Employee employee) throws EmployeeDuplicateException {
			if(employee.getUsername()==null)
				return;
			//校验是否用户重复
	    	if(employeeRepository.findByUsername(employee.getUsername())!=null)
	    		throw new EmployeeDuplicateException();
	        //加密密码
	        passwordHelper.encryptPassword(employee);
	        employee.setCreateTime(new Date());
	        if(userService.getCurrentUser()!=null)
	        	employee.setCreatorName(userService.getCurrentUser().getUsername());
	        employeeRepository.save(employee);
	}


	@Override
	public void update(Employee obj) {
		Employee obj2=employeeRepository.findOne(obj.getId());
	/*	if(null!=obj.getName()||!"".equals(obj))
			obj2.setName(obj.getName());*/
		try {
			@SuppressWarnings("unchecked")
			Class<Employee> clazz=(Class<Employee>) Class.forName("com.vino.maintain.entity.Employee");
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
	public Employee findByUsername(String username) {
		// TODO Auto-generated method stub
		return employeeRepository.findByUsername(username);
	}


	@Override
	public boolean bindGroup(long employeeId, long groupId) {
		// TODO Auto-generated method stub
		Employee employee=employeeRepository.findOne(employeeId);
		if(groupId==0)//当id=0时，取消绑定
			employee.setGroup(null);
		Group group=groupRepository.findOne(groupId);
		
		if(group!=null&&employee!=null){
			employee.setGroup(group);	
			return true;
		}else{
			return false;
		}
	}


	@Override
	public void updateCid(String cid,String username) {
		employeeRepository.updateCid(cid, username);
		
	}
	

}
