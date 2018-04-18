package com.vino.maintain.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vino.authentication.annotation.Authentication;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.MaintainOrder;
import com.vino.maintain.service.FaultOrderService;
import com.vino.scaffold.controller.base.BaseController;
@Controller
@RequestMapping("/api/faultOrder")
public class FaultOrderAPIController extends BaseController{
	@Autowired
	private FaultOrderService faultOrderService;
	
	public Pageable buildPageRequest(int pageNumber,int pageSize) {
		
		return new PageRequest(pageNumber-1, pageSize, new Sort(Direction.ASC,
				"id"));
	}
	//根据接单时间排序
	public Pageable buildPageRequestSortByReceivingTime(int pageNumber,int pageSize) {
		
		return new PageRequest(pageNumber-1, pageSize, new Sort(Direction.DESC,
				"receivingTime"));
	}
	//根据签到时间排序
	public Pageable buildPageRequestSortBySignInTime(int pageNumber,int pageSize) {
		
		return new PageRequest(pageNumber-1, pageSize, new Sort(Direction.DESC,
				"signInTime"));
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public List<FaultOrder> listByPage(int pageNumber, int pageSize,int type,long groupId){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		List<FaultOrder> faultOrders=null;
		switch (type) {
		case 0://已完成
			Page<FaultOrder> fixedPage=faultOrderService.findFixedOrderByGroup(buildPageRequest(pageNumber,pageSize),groupId);
			faultOrders=fixedPage.getContent();
			
			break;
		case 1://未完成
			Page<FaultOrder> notFixedPage=faultOrderService.findNotFixedOrderByGroup(buildPageRequest(pageNumber,pageSize),groupId);
			faultOrders=notFixedPage.getContent();
			break;
		case 2://超期
			
			break;

		default:
			break;
		}
		
		return faultOrders;
		
	}
	/**
	 * 根据员工id查找已接单并且未完成的工单
	 * @param pageNumber
	 * @param pageSize
	 * @param employeeId
	 * @return
	 */
	@Authentication
	@ResponseBody
	@RequestMapping(value="/accept/list",method=RequestMethod.GET)
	public List<FaultOrder> findOrderByAccept(@RequestParam(required=true)int pageNumber,@RequestParam(defaultValue="10") int pageSize,@RequestParam(required=true)long employeeId){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		List<FaultOrder> faultOrders=null;
		
			Page<FaultOrder> page=faultOrderService.findAcceptedOrder(buildPageRequestSortByReceivingTime(pageNumber,pageSize),employeeId);
			faultOrders=page.getContent();
			
		
		return faultOrders;
		
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/feedback/list",method=RequestMethod.GET)
	public List<FaultOrder> findOrderBySigned(@RequestParam(required=true)int pageNumber,@RequestParam(defaultValue="10") int pageSize,@RequestParam(required=true)long employeeId){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		List<FaultOrder> faultOrders=null;		
		Page<FaultOrder> page=faultOrderService.findSignedOrder(buildPageRequestSortBySignInTime(pageNumber,pageSize),employeeId);
		faultOrders=page.getContent();
			
		
		return faultOrders;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public FaultOrder detail(long id){
		return faultOrderService.findOne(id);
		
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public Map<String,Object> acceptOrder(long id,long employeeId){		
		//若是接单过了，则返回repeat,错误的id,employeeId会返回不存在
		//成功返回success
		String result=faultOrderService.acceptOrder(id, employeeId);
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("result", result);
		if(result.equals("success"))
			resultMap.put("receivingTime",new Date());
		else
			resultMap.put("receivingTime",null);
		return resultMap;
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	public Map<String,String> cancelOrder(long id,long employeeId){		
		//若是接单过了，则返回repeat,错误的id,employeeId会返回不存在
		//成功返回success
		String result=faultOrderService.cancelOrder(id, employeeId);
		Map<String,String> resultMap=new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/signIn",method=RequestMethod.POST)
	public Map<String,String> signIn(long id,String signInAddress){		
		
		String result=faultOrderService.signIn(id, signInAddress);
		Map<String,String> resultMap=new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/feedback",method=RequestMethod.POST)
	public Map<String,String> feedback(FaultOrder faultOrder){	
		
		faultOrderService.feedback(faultOrder);
		String result="success";
		Map<String,String> resultMap=new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
	}
	@Authentication
	@ResponseBody
	@RequestMapping(value="/history",method=RequestMethod.GET)
	public List<FaultOrder> getFaultOrderHistory(int pageNumber,int pageSize,Long elevatorRecordId){
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
		List<FaultOrder> faultOrders=null;		
		Page<FaultOrder> page=faultOrderService.findFaultHistory(buildPageRequest(pageNumber, pageSize),elevatorRecordId);
		faultOrders=page.getContent();
		return faultOrders;
	}
}
