package com.vino.maintain.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vino.authentication.annotation.Authentication;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.Group;
import com.vino.maintain.service.GroupService;

@Controller
@RequestMapping("/api/group")
public class GroupAPIController {
	@Autowired
	private GroupService groupService;
	public Pageable buildPageRequest(int pageNumber, int pageSize) {
		return new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/employees",method=RequestMethod.GET)
	public List<Employee> getGroupMembers(long id){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		Group group=groupService.findOne(id);
		List<Employee> employees=group.getEmployees();
		return employees;
	}
	
}
