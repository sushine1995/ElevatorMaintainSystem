package com.vino.maintain.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vino.authentication.annotation.Authentication;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.Group;
import com.vino.maintain.service.FaultOrderService;
import com.vino.maintain.service.GroupService;
import com.vino.maintain.service.MaintainPlanService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
@Controller
@RequestMapping("/faultOrder")
public class FaultOrderController extends BaseController {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private FaultOrderService faultOrderService;
	@Autowired
	private GroupService groupService;
	@RequiresPermissions("faultOrder:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLFaultOrders(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("faultOrders", faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);
		//model.addAttribute("searchParams", "");
		return "faultOrder/list";
	}
	@RequiresPermissions("faultOrder:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getFaultOrdersByCondition(Model model,FaultOrder faultOrder,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<FaultOrder> faultOrderPage=faultOrderService.findFaultOrderByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("faultOrders",faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println("返回到页面的搜索参数"+Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println(searchParams.toString());
		model.addAttribute("searchParamsMap", searchParams);
		return "faultOrder/list";
	}
	@RequiresPermissions("faultOrder:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddFaultOrder(Model model ){
		return "faultOrder/add";
	}
	
	@RequiresPermissions("faultOrder:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addFaultOrder(Model model ,FaultOrder faultOrder,HttpServletResponse response){
		
			faultOrderService.saveWithCreateTimeAndCreator(faultOrder);
			Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(1));
			model.addAttribute("faultOrders", faultOrderPage.getContent());
			model.addAttribute("page", faultOrderPage);
			return "faultOrder/list";	
		
		
		
	}
	
	
	@RequiresPermissions("faultOrder:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteFaultOrders(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		faultOrderService.delete(deleteIds);
		Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(1));
		model.addAttribute("faultOrders", faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);
		return "faultOrder/list";
		
	}
	@RequiresPermissions("faultOrder:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateFaultOrder(Model model,FaultOrder faultOrder){
		faultOrderService.update(faultOrder);
		Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(1));
		model.addAttribute("faultOrders", faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);
		return "faultOrder/list";
		
	}
	@RequiresPermissions("faultOrder:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateFaultOrder(Model model,@PathVariable("id") Long id){
		model.addAttribute("faultOrder", faultOrderService.findOne(id));
		
		return "faultOrder/edit";
		
	}
	@RequiresPermissions("faultOrder:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findFaultOrder(Model model,@PathVariable("id") Long id){
		model.addAttribute("faultOrder", faultOrderService.findOne(id));
		return "faultOrder/detail";
		
	}
	
	@RequiresPermissions("faultOrder:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	
		return "faultOrder/bind";
		
	}
	@RequiresPermissions("faultOrder:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("faultOrderId")Long faultOrderId,@RequestParam(value="groupId")Long groupId){
		FaultOrder faultOrder=faultOrderService.findOne(faultOrderId);

		Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(1));
		model.addAttribute("faultOrders", faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);
		return "faultOrder/list";
		
	}
	@RequiresPermissions("faultOrder:feedback")
	@RequestMapping(value="/prepareFeedback/{id}",method=RequestMethod.GET)
	public String prepareFeedback(Model model,@PathVariable("id") Long id){
		FaultOrder faultOrder=faultOrderService.findOne(id);
		long groupId=faultOrder.getElevatorRecord().getGroup().getId();		
		List<Employee> employees=groupService.findOne(groupId).getEmployees();
		model.addAttribute("faultOrder",faultOrder );
		model.addAttribute("employees",employees );
		return "faultOrder/feedback";
		
	}
	
	@RequiresPermissions("faultOrder:feedback")
	@RequestMapping(value="/feedback",method=RequestMethod.POST)
	public String feedback(Model model,FaultOrder faultOrder){
		faultOrderService.update(faultOrder);
		Page<FaultOrder> faultOrderPage=faultOrderService.findAll(buildPageRequest(1));
		model.addAttribute("faultOrders", faultOrderPage.getContent());
		model.addAttribute("page", faultOrderPage);
		return "faultOrder/list";
		
	}
	
}
