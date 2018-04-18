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

import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.Group;
import com.vino.maintain.entity.MaintainItem;
import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.exception.MaintainItemDuplicateException;
import com.vino.maintain.repository.MaintainItemRepository;
import com.vino.maintain.repository.MaintainTypeRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
import com.vino.scaffold.shiro.service.UserService;
@Service("maintainItemService")
@Transactional
public class MaintainItemServiceImpl extends AbstractBaseServiceImpl<MaintainItem, Long> implements MaintainItemService{
	@Autowired
	private MaintainItemRepository maintainItemRepository;
	@Autowired
	private MaintainTypeRepository maintainTypeRepository;
	@Autowired
	private UserService userService;
	  /**
     * 创建动态查询条件组合.
     */
    private Specification<MaintainItem> buildSpecification(final Map<String,Object> searchParams) {
    	
		
        Specification<MaintainItem> spec = new Specification<MaintainItem>(){           
			@Override
			public Predicate toPredicate(Root<MaintainItem> root,
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
	public Page<MaintainItem> findMaintainItemByCondition(Map<String,Object> searchParams, Pageable pageable) {
	
		return maintainItemRepository.findAll(buildSpecification(searchParams), pageable);
	}


	@Override
	public void saveWithCheckDuplicate(MaintainItem maintainItem) throws MaintainItemDuplicateException {
			if(maintainItem.getName()==null)
				return;
			//校验是否用户重复
	    	if(maintainItemRepository.findByName(maintainItem.getName())!=null)
	    		throw new MaintainItemDuplicateException();
	        //加密密码
	        maintainItem.setCreateTime(new Date());
	        if(userService.getCurrentUser()!=null)
	        	maintainItem.setCreatorName(userService.getCurrentUser().getUsername());
	        if(maintainItem.getMaintainType().getId()!=null)//更新维保类型
	        	maintainItem.setMaintainType(maintainTypeRepository.findOne(maintainItem.getMaintainType().getId()));
	        maintainItemRepository.save(maintainItem);
	}


	@Override
	public void update(MaintainItem obj) {
		MaintainItem obj2=maintainItemRepository.findOne(obj.getId());
		if(obj.getMaintainType()!=null){
		MaintainType type=maintainTypeRepository.findOne(obj.getMaintainType().getId());
		obj.setMaintainType(type);//获取外键的全部值，设置，避免前端获取到的数据不足
		}
		try {
			@SuppressWarnings("unchecked")
			Class<MaintainItem> clazz=(Class<MaintainItem>) Class.forName("com.vino.maintain.entity.MaintainItem");
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
	public boolean bindType(long maintainItemId, long maintainTypeId) {

		MaintainItem maintainItem=maintainItemRepository.findOne(maintainItemId);
		if(maintainTypeId==0)//当id=0时，取消绑定
			maintainItem.setMaintainType(null);
		MaintainType maintainType=maintainTypeRepository.findOne(maintainTypeId);
		
		if(maintainType!=null&&maintainItem!=null){
			maintainItem.setMaintainType(maintainType);
			return true;
		}else{
			return false;
		}
	}
}
