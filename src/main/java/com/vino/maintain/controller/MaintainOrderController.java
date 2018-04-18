package com.vino.maintain.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vino.maintain.entity.MaintainOrder;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.Group;
import com.vino.maintain.service.MaintainOrderService;
import com.vino.maintain.service.GroupService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
@Controller
@RequestMapping("/maintainOrder")
public class MaintainOrderController extends BaseController {
	@Autowired
	private MaintainOrderService maintainOrderService;
	@Autowired
	private GroupService groupService;
	@RequiresPermissions("maintainOrder:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLMaintainOrders(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("maintainOrders", maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);
		//model.addAttribute("searchParams", "");
		return "maintainOrder/list";
	}
	@RequiresPermissions("maintainOrder:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getMaintainOrdersByCondition(Model model,MaintainOrder maintainOrder,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findMaintainOrderByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("maintainOrders",maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		model.addAttribute("searchParamsMap", searchParams);
		return "maintainOrder/list";
	}
	@RequiresPermissions("maintainOrder:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddMaintainOrder(Model model ){
		return "maintainOrder/add";
	}
	
	@RequiresPermissions("maintainOrder:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addMaintainOrder(Model model ,MaintainOrder maintainOrder,HttpServletResponse response){
		
			maintainOrderService.saveWithCreateTimeAndCreator(maintainOrder);
			Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(1));
			model.addAttribute("maintainOrders", maintainOrderPage.getContent());
			model.addAttribute("page", maintainOrderPage);
			return "maintainOrder/list";	
		
		
		
	}
	
	
	@RequiresPermissions("maintainOrder:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteMaintainOrders(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		maintainOrderService.delete(deleteIds);
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(1));
		model.addAttribute("maintainOrders", maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);
		return "maintainOrder/list";
		
	}
	@RequiresPermissions("maintainOrder:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateMaintainOrder(Model model,MaintainOrder maintainOrder){
		maintainOrderService.update(maintainOrder);
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(1));
		model.addAttribute("maintainOrders", maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);
		return "maintainOrder/list";
		
	}
	@RequiresPermissions("maintainOrder:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateMaintainOrder(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainOrder", maintainOrderService.findOne(id));
		return "maintainOrder/edit";
		
	}
	@RequiresPermissions("maintainOrder:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findMaintainOrder(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainOrder", maintainOrderService.findOne(id));
		return "maintainOrder/detail";
		
	}
	
	/*@RequiresPermissions("maintainOrder:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	
		MaintainOrder maintainOrder=maintainOrderService.findOne(id);
		model.addAttribute("maintainOrder", maintainOrder);
		List<Group> groups=groupService.findAll();
		if(maintainOrder.getGroup()!=null)
			groups.remove(maintainOrder.getGroup());
		model.addAttribute("availableGroups",groups);//可供选择的小组
		return "maintainOrder/bind";
		
	}
	@RequiresPermissions("maintainOrder:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("maintainOrderId")Long maintainOrderId,@RequestParam(value="groupId")Long groupId){
		MaintainOrder maintainOrder=maintainOrderService.findOne(maintainOrderId);
		if(groupId==0)//当id=0时，取消绑定
			maintainOrder.setGroup(null);
		Group group=groupService.findOne(groupId);
		
		if(group!=null&&maintainOrder!=null){
			maintainOrder.setGroup(group);			
		}
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(1));
		model.addAttribute("maintainOrders", maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);
		return "maintainOrder/list";
		
	}*/
	@RequiresPermissions("maintainOrder:feedback")
	@RequestMapping(value="/prepareFeedback/{id}",method=RequestMethod.GET)
	public String prepareFeedback(Model model,@PathVariable("id") Long id){
		MaintainOrder maintainOrder=maintainOrderService.findOne(id);
		long groupId=maintainOrder.getElevatorRecord().getGroup().getId();		
		List<Employee> employees=groupService.findOne(groupId).getEmployees();
		model.addAttribute("maintainOrder",maintainOrder );
		model.addAttribute("employees",employees );
		return "maintainOrder/feedback";
		
	}
	@RequiresPermissions("maintainOrder:feedback")
	@RequestMapping(value="/feedback",method=RequestMethod.POST)
	public String feedback(Model model,MaintainOrder maintainOrder){
		maintainOrderService.feedback(maintainOrder);
		Page<MaintainOrder> maintainOrderPage=maintainOrderService.findAll(buildPageRequest(1));
		model.addAttribute("maintainOrders", maintainOrderPage.getContent());
		model.addAttribute("page", maintainOrderPage);
		return "maintainOrder/list";
		
	}
}
