package com.vino.maintain.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vino.authentication.annotation.Authentication;
import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.service.ElevatorRecordService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;

@Controller
@RequestMapping("/api/elevatorRecord")
public class ElevatorRecordAPIController extends BaseController {
	@Autowired
	private ElevatorRecordService elevatorRecordService;
	
	public Pageable buildPageRequest(int pageNumber, int pageSize) {
		return new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
	}
	/**
	 * 若groupId不为null，则查询该组的电梯，否则查询所有电梯
	 * @param pageNumber
	 * @param pageSize
	 * @param groupId
	 * @return
	 */
	@Authentication
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ElevatorRecord> listByPage(int pageNumber, @RequestParam(defaultValue = "10") int pageSize,Long groupId) {
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		List<ElevatorRecord> elevatorRecords = null;
		Page<ElevatorRecord> page=null;
		if(groupId==null){
			page=elevatorRecordService.findAll(buildPageRequest(pageNumber));
		}else{
			page=elevatorRecordService.findElevatorRecordByGroup(buildPageRequest(pageNumber, pageSize), groupId);
		}
		
		if(page!=null)
			elevatorRecords=page.getContent();
		return elevatorRecords;

	}
	@Authentication
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<ElevatorRecord> getElevatorRecordsByCondition(int pageNumber,int pageSize,String searchParam){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findElevatorRecordByCondition(searchParam, buildPageRequest(pageNumber,pageSize));
		List<ElevatorRecord> elevatorRecords=elevatorRecordPage.getContent();
		
		return elevatorRecords;
	}
	
}
