package com.vino.maintain.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.Cacheable;
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

import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.exception.MaintainTypeDuplicateException;
import com.vino.maintain.repository.MaintainTypeRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Transactional
@Service("miantainTypeService")

public class MaintainTypeServiceImpl extends AbstractBaseServiceImpl<MaintainType, Long> implements MaintainTypeService{
	@Autowired
	private MaintainTypeRepository maintainTypeRepository;
	@Autowired
	private UserService userService;
	  /**
     * 创建动态查询条件组合.
     */
    private Specification<MaintainType> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<MaintainType> spec = new Specification<MaintainType>(){           
			@Override
			public Predicate toPredicate(Root<MaintainType> root,
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
	public Page<MaintainType> findMaintainTypeByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return maintainTypeRepository.findAll(buildSpecification(searchParams), pageable);
	}


	@Override
	public void saveWithCheckDuplicate(MaintainType maintainType) throws MaintainTypeDuplicateException {
			if(maintainType.getName()==null)
				return;
			//校验是否用户重复
	    	if(maintainTypeRepository.findByName(maintainType.getName())!=null)
	    		throw new MaintainTypeDuplicateException();
	        //加密密码
	        maintainType.setCreateTime(new Date());
	        if(userService.getCurrentUser()!=null)
	        	maintainType.setCreatorName(userService.getCurrentUser().getUsername());
	        maintainTypeRepository.save(maintainType);
	}


	@Override
	public void update(MaintainType obj) {
		MaintainType obj2=maintainTypeRepository.findOne(obj.getId());
	
		try {
			@SuppressWarnings("unchecked")
			Class<MaintainType> clazz=(Class<MaintainType>) Class.forName("com.vino.maintain.entity.MaintainType");
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
}
