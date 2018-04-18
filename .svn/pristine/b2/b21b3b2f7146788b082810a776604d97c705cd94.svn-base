package com.vino.maintain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vino.maintain.entity.Employee;
import com.vino.scaffold.repository.base.BaseRepository;

public interface EmployeeRepository extends BaseRepository<Employee, Long>{

	Employee findByUsername(String username);
	@Query("select e from Employee e where e.group.id=?1")
	List<Employee> findEmployeesByGroup(Long groupId);
	@Modifying
	@Query("update Employee e set e.cid=?1 where e.username=?2")
	void updateCid(String cid,String username);
}
