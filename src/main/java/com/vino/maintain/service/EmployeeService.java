package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.Employee;
import com.vino.maintain.exception.EmployeeDuplicateException;
import com.vino.scaffold.service.base.BaseService;

public interface EmployeeService extends BaseService<Employee, Long>{
	public Page<Employee> findEmployeeByCondition(Map<String,Object> searchParams,Pageable pageable);
	
	public void saveWithCheckDuplicate(Employee employee) throws EmployeeDuplicateException;
	public Employee findByUsername(String username);
	public boolean bindGroup(long employeeId,long groupId);
	/**
	 * ¸üÐÂCID
	 * @param cid
	 * @param username
	 */
	void updateCid(String cid,String username);
	
}
