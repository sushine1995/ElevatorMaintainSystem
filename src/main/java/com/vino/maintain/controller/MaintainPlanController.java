package com.vino.maintain.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.Group;
import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.exception.MaintainPlanDuplicateException;
import com.vino.maintain.service.ElevatorRecordService;
import com.vino.maintain.service.GroupService;
import com.vino.maintain.service.MaintainPlanService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
@Controller
@RequestMapping("/maintainPlan")
public class MaintainPlanController extends BaseController{
	@Autowired
	private MaintainPlanService maintainPlanService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ElevatorRecordService elevatorRecordService;
	@RequiresPermissions("maintainPlan:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLMaintainPlans(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("maintainPlans", maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);
		//model.addAttribute("searchParams", "");
		return "maintainPlan/list";
	}
	@RequiresPermissions("maintainPlan:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getMaintainPlansByCondition(Model model,MaintainPlan maintainPlan,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findMaintainPlanByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("maintainPlans",maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		model.addAttribute("searchParamsMap", searchParams);
		return "maintainPlan/list";
	}
	
	@RequiresPermissions("maintainPlan:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddMaintainPlan(Model model){
		List<Group> groups=groupService.findAll();
		model.addAttribute("groups",groups);//可供选择的小组
		List<ElevatorRecord> elevatorRecords=elevatorRecordService.findAll();
		model.addAttribute("elevatorRecords",elevatorRecords);//可供选择的小组
		return "maintainPlan/add";
	}
	
	@RequiresPermissions("maintainPlan:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addMaintainPlan(Model model ,MaintainPlan maintainPlan){
		/*	List<ElevatorRecord> elevatorRecords=new ArrayList<>();
			for(long eId:elevatorRecordIds){
				elevatorRecords.add(elevatorRecordService.findOne(eId));
			}
			maintainPlan.setElevatorRecords(elevatorRecords);*/
			try {
				maintainPlanService.saveWithCheckDuplicate(maintainPlan);
			} catch (MaintainPlanDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(1));
			model.addAttribute("maintainPlans", maintainPlanPage.getContent());
			model.addAttribute("page", maintainPlanPage);
			return "maintainPlan/list";	
		
		
		
	}
	
	
	@RequiresPermissions("maintainPlan:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteMaintainPlans(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		//要先解除关联才能删除
		maintainPlanService.delete(deleteIds);
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(1));
		model.addAttribute("maintainPlans", maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);
		return "maintainPlan/list";
		
	}
	@RequiresPermissions("maintainPlan:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateMaintainPlan(Model model,MaintainPlan maintainPlan){
		maintainPlanService.update(maintainPlan);
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(1));
		model.addAttribute("maintainPlans", maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);
		return "maintainPlan/list";
		
	}
	@RequiresPermissions("maintainPlan:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateMaintainPlan(Model model,@PathVariable("id") Long id){
		List<Group> groups=groupService.findAll();
		model.addAttribute("groups",groups);//可供选择的小组
		model.addAttribute("maintainPlan", maintainPlanService.findOne(id));
		return "maintainPlan/edit";
		
	}
	@RequiresPermissions("maintainPlan:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findMaintainPlan(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainPlan", maintainPlanService.findOne(id));
		return "maintainPlan/detail";
		
	}
	
	@RequiresPermissions("maintainPlan:bindGroup")
	@RequestMapping(value="/prepareBindGroup/{id}",method=RequestMethod.GET)
	public String prepareBindGroup(Model model,@PathVariable("id") Long id){
	
		MaintainPlan maintainPlan=maintainPlanService.findOne(id);
		model.addAttribute("maintainPlan", maintainPlan);
		List<Group> groups=groupService.findAll();
		if(maintainPlan.getGroup()!=null)
			groups.remove(maintainPlan.getGroup());
		model.addAttribute("availableGroups",groups);//可供选择的小组
		return "maintainPlan/bind";
		
	}
	@RequiresPermissions("maintainPlan:bindGroup")
	@RequestMapping(value="/bindGroup",method=RequestMethod.POST)
	public String bindGroup(Model model,@RequestParam("maintainPlanId")Long maintainPlanId,@RequestParam(value="groupId")Long groupId){
		MaintainPlan maintainPlan=maintainPlanService.findOne(maintainPlanId);
		if(groupId==0)//当id=0时，取消绑定
			maintainPlan.setGroup(null);
		Group group=groupService.findOne(groupId);
		
		if(group!=null&&maintainPlan!=null){
			maintainPlan.setGroup(group);			
		}
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(1));
		model.addAttribute("maintainPlans", maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);
		return "maintainPlan/list";
		
	}
	
	@RequiresPermissions("maintainPlan:bindElevator")
	@RequestMapping(value="/prepareBindElevator/{id}",method=RequestMethod.GET)
	public String prepareBindElevator(Model model,@PathVariable("id") Long id){
	
		MaintainPlan maintainPlan=maintainPlanService.findOne(id);
		model.addAttribute("maintainPlan", maintainPlan);
		List<Group> groups=groupService.findAll();
		if(maintainPlan.getGroup()!=null)
			groups.remove(maintainPlan.getGroup());
		model.addAttribute("availableGroups",groups);//可供选择的小组
		return "maintainPlan/bind";
		
	}
	@RequiresPermissions("maintainPlan:bindElevator")
	@RequestMapping(value="/bindElevator",method=RequestMethod.POST)
	public String bindElevator(Model model,@RequestParam("maintainPlanId")Long maintainPlanId,@RequestParam(value="groupId")Long groupId){
		MaintainPlan maintainPlan=maintainPlanService.findOne(maintainPlanId);
		if(groupId==0)//当id=0时，取消绑定
			maintainPlan.setGroup(null);
		Group group=groupService.findOne(groupId);
		
		if(group!=null&&maintainPlan!=null){
			maintainPlan.setGroup(group);			
		}
		Page<MaintainPlan> maintainPlanPage=maintainPlanService.findAll(buildPageRequest(1));
		model.addAttribute("maintainPlans", maintainPlanPage.getContent());
		model.addAttribute("page", maintainPlanPage);
		return "maintainPlan/list";
		
	}
}
