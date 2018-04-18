package com.vino.maintain.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.Group;
import com.vino.maintain.entity.MaintainOrder;
import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.service.ElevatorRecordService;
import com.vino.maintain.service.FaultOrderService;
import com.vino.maintain.service.GroupService;
import com.vino.maintain.service.MaintainOrderService;
import com.vino.maintain.service.MaintainPlanService;
import com.vino.maintain.service.MaintainTypeService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.shiro.entity.Resource;
import com.vino.scaffold.shiro.entity.Role;
import com.vino.scaffold.utils.Servlets;
import com.vino.scaffold.utils.Tree;
import com.vino.scaffold.utils.TreeUtils;

@Controller
@RequestMapping("/elevatorRecord")
public class ElevatorRecordController extends BaseController{
	@Autowired
	private ElevatorRecordService elevatorRecordService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private FaultOrderService faultOrderService;
	@Autowired
	private MaintainPlanService maintainPlanService;
	@Autowired
	private MaintainOrderService maintainOrderService;
	@Autowired
	private MaintainTypeService maintainTypeService;
	@RequiresPermissions("elevatorRecord:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLElevatorRecords(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(pageNumber,pageSize));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		System.out.println("================pagesize"+pageSize);
		//model.addAttribute("searchParams", "");
		return "elevatorRecord/list";
	}
	@RequiresPermissions("elevatorRecord:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getElevatorRecordsByCondition(Model model,ElevatorRecord elevatorRecord,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE+"") int pageSize,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());	
		System.out.println("================pagesize"+pageSize);
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findElevatorRecordByCondition(searchParams, buildPageRequest(pageNumber,pageSize));
		model.addAttribute("elevatorRecords",elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		
		model.addAttribute("searchParamsMap", searchParams);
		return "elevatorRecord/list";
	}
	@RequiresPermissions("elevatorRecord:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddElevatorRecord(Model model ){
		return "elevatorRecord/add";
	}
	@RequiresPermissions("elevatorRecord:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addElevatorRecord(Model model ,ElevatorRecord elevatorRecord){		
		elevatorRecordService.saveWithCreateTimeAndCreator(elevatorRecord);					
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		return "elevatorRecord/list";	
	}
	@RequiresPermissions("elevatorRecord:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteElevatorRecords(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		elevatorRecordService.delete(deleteIds);
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		return "elevatorRecord/list";
		
	}
	@RequiresPermissions("elevatorRecord:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateElevatorRecord(Model model,ElevatorRecord elevatorRecord){
		elevatorRecordService.update(elevatorRecord);
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		return "elevatorRecord/list";
		
	}
	@RequiresPermissions("elevatorRecord:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateElevatorRecord(Model model,@PathVariable("id") Long id){
		model.addAttribute("elevatorRecord", elevatorRecordService.findOne(id));
		return "elevatorRecord/edit";
		
	}
	@RequiresPermissions("elevatorRecord:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findElevatorRecord(Model model,@PathVariable("id") Long id){
		model.addAttribute("elevatorRecord", elevatorRecordService.findOne(id));
		return "elevatorRecord/detail";
		
	}
	
	@RequiresPermissions("elevatorRecord:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	
		ElevatorRecord elevatorRecord=elevatorRecordService.findOne(id);
		model.addAttribute("elevatorRecord", elevatorRecord);
		List<Group> groups=groupService.findAll();
		if(elevatorRecord.getGroup()!=null)
			groups.remove(elevatorRecord.getGroup());
		model.addAttribute("availableGroups",groups);
		return "elevatorRecord/bind";
		
	}
	@RequiresPermissions("elevatorRecord:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("elevatorRecordId")Long elevatorRecordId,@RequestParam(value="groupId")Long groupId){
		ElevatorRecord elevatorRecord=elevatorRecordService.findOne(elevatorRecordId);
		if(groupId==0)//当id=0时，取消绑定
			elevatorRecord.setGroup(null);
		Group group=groupService.findOne(groupId);
		
		if(group!=null&&elevatorRecord!=null){
			elevatorRecord.setGroup(group);			
		}
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		return "elevatorRecord/list";
		
	}
	/**
	 * 发布故障工单
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("elevatorRecord:addFaultOrder")
	@RequestMapping(value="/prepareAddFaultOrder/{id}",method=RequestMethod.GET)
	public String prepareAddFaultOrder(Model model,@PathVariable("id") Long id){
	
		//ElevatorRecord elevatorRecord=elevatorRecordService.findOne(id);
		model.addAttribute("elevatorRecordId", id);
		return "elevatorRecord/addFaultOrder";
		
	}
	@ResponseBody
	@RequiresPermissions("elevatorRecord:addFaultOrder")
	@RequestMapping(value="/addFaultOrder",method=RequestMethod.POST)
	public String addFaultOrder(Model model,FaultOrder faultOrder){	
		
		faultOrderService.saveWithCreateTimeAndCreator(faultOrder);	
		return "success";
		
	}
	@RequiresPermissions("elevatorRecord:addFaultOrder")
	@RequestMapping(value="/prepareAddMaintainOrder/{id}",method=RequestMethod.GET)
	public String prepareAddMaintainOrder(Model model,@PathVariable("id") Long id){
		model.addAttribute("elevatorRecordId", id);
		model.addAttribute("maintainTypes", maintainTypeService.findAll());
		return "elevatorRecord/addMaintainOrder";
		
	}
	@ResponseBody
	@RequiresPermissions("elevatorRecord:addMaintainOrder")
	@RequestMapping(value="/addMaintainOrder",method=RequestMethod.POST)
	public String addMaintainOrder(Model model,MaintainOrder maintainOrder){	
		
		maintainOrderService.saveWithCreateTimeAndCreator(maintainOrder);	
		return "success";
		
	}
	
	
	@RequiresPermissions("elevatorRecord:upload")
	@RequestMapping(value="/prepareUpload",method=RequestMethod.GET)
	public String prepareUpload(){
		return "elevatorRecord/upload";
	}
	
	@RequiresPermissions("elevatorRecord:bindMaintainPlan")
	@RequestMapping(value="/prepareBindMaintainPlan",method=RequestMethod.GET)
	public String prepareBindMaintainPlan(@RequestParam("elevatorRecordIds[]")long[] elevatorRecordIds,Model model){
		Map<String,Object> elevatorRecordMap=new HashMap<>();
		elevatorRecordMap.put("elevatorRecordIds", elevatorRecordIds);
		String elevatorRecordIdsStr=JSON.toJSONString(elevatorRecordMap);
		model.addAttribute("elevatorRecordIds", elevatorRecordIdsStr);
		return "elevatorRecord/bindMaintainPlan";
	}
	@RequiresPermissions("elevatorRecord:bindMaintainPlan")
	@RequestMapping(value="/bindMaintainPlan",method=RequestMethod.POST)
	public String bindMaintainPlan(@RequestParam("elevatorRecordIds[]")long[] elevatorRecordIds,long maintainPlanId,Model model){
		elevatorRecordService.bindMaintainPlan(elevatorRecordIds, maintainPlanId);
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		return "elevatorRecord/list";
	}
	@RequiresPermissions("elevatorRecord:bindMaintainPlan")
	@ResponseBody
	@RequestMapping(value="/json/getMaintainPlans/",method=RequestMethod.GET)
	public List<Tree> getMaintainPlans(){
		List<MaintainPlan> maintainPlans=maintainPlanService.findAll();
		return TreeUtils.formatMaintainPlansToTree(maintainPlans);		
	}
	
	
	
	
/*	*//**
	 * 上传文件
	 * @param model
	 * @param file
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("elevatorRecord:upload")
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(Model model,@RequestParam("file")MultipartFile file,HttpServletRequest request){
		Page<ElevatorRecord> elevatorRecordPage=elevatorRecordService.findAll(buildPageRequest(1));
		model.addAttribute("elevatorRecords", elevatorRecordPage.getContent());
		model.addAttribute("page", elevatorRecordPage);
		
		if(!file.isEmpty()){
			 //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
            try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));
				List<ElevatorRecord> uploadElevatorRecords=elevatorRecordExcelService.getFromExcel(new File(realPath+"\\"+file.getOriginalFilename()));		
				elevatorRecordService.saveWithCheckDuplicate(uploadElevatorRecords);
				log.info("上传用户:"+Arrays.toString(uploadElevatorRecords.toArray()));
			} catch (IOException e) {
				log.error("保存或读取文件出错");
				e.printStackTrace();
				return "saveFileError";
			} catch (BiffException e) {
				
				e.printStackTrace();
				return "fileStreamError";
			} catch (ElevatorRecordDuplicateException e) {
				e.printStackTrace();
				log.warn("上传文件包含与数据库重复的对象");
				return "entityDuplicate";				
			} 
		}else{
			return "fileEmpty";
		}
		
		return "uploadSuccess";
	}
	
	
	@RequiresPermissions("elevatorRecord:download")
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@RequestParam(value="downloadIds[]",required=false)Long[] downloadIds,HttpSession session) throws IOException{	
		String realPath=session.getServletContext().getRealPath("/WEB-INF/upload");
		String fileName="elevatorRecordExport"+System.currentTimeMillis()+".xls";
		elevatorRecordExcelService.saveToExcel(realPath+"\\"+fileName, downloadIds);
		HttpHeaders headers = new HttpHeaders();    
		headers.setContentDispositionFormData("attachment", fileName); 	
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	   // FileInputStream fin=new FileInputStream(new File(realPath+"\\"+fileName));	    
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(realPath+"\\"+fileName)),    
				                                  headers, HttpStatus.CREATED);
			
	}*/
}
