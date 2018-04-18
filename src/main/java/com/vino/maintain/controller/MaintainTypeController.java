package com.vino.maintain.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.exception.MaintainTypeDuplicateException;
import com.vino.maintain.service.MaintainTypeService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
@Controller
@RequestMapping("/maintainType")
public class MaintainTypeController extends BaseController {
	@Autowired
	private MaintainTypeService maintainTypeService;
	
	@RequiresPermissions("maintainType:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLMaintainTypes(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("maintainTypes", maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);
		return "maintainType/list";
	}
	@RequiresPermissions("maintainType:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getMaintainTypesByCondition(Model model,MaintainType maintainType,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<MaintainType> maintainTypePage=maintainTypeService.findMaintainTypeByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("maintainTypes",maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println("返回到页面的搜索参数"+Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println(searchParams.toString());
		model.addAttribute("searchParamsMap", searchParams);
		return "maintainType/list";
	}
	@RequiresPermissions("maintainType:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddMaintainType(Model model ){
		return "maintainType/add";
	}
	@RequiresPermissions("maintainType:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addMaintainType(Model model ,MaintainType maintainType){		
		try {
			maintainTypeService.saveWithCheckDuplicate(maintainType);
			Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(1));
			model.addAttribute("maintainTypes", maintainTypePage.getContent());
			model.addAttribute("page", maintainTypePage);
			return "maintainType/list";	
		} catch (MaintainTypeDuplicateException e) {
			
			e.printStackTrace();
			return "forward:entityDuplicate";
		}					
	
	}
	@ResponseBody
	@RequestMapping("/entityDuplicate")
	public String addDuplicateEntity(){
		
		return "entityDuplicate";	
	}
	@RequiresPermissions("maintainType:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteMaintainTypes(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		maintainTypeService.delete(deleteIds);
		Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(1));
		model.addAttribute("maintainTypes", maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);
		return "maintainType/list";
		
	}
	@RequiresPermissions("maintainType:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateMaintainType(Model model,MaintainType maintainType){
		maintainTypeService.update(maintainType);
		Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(1));
		model.addAttribute("maintainTypes", maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);
		return "maintainType/list";
		
	}
	@RequiresPermissions("maintainType:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateMaintainType(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainType", maintainTypeService.findOne(id));
		return "maintainType/edit";
		
	}
	
	@RequiresPermissions("maintainType:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findMaintainType(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainType", maintainTypeService.findOne(id));
		return "maintainType/detail";
		
	}
	
	@RequiresPermissions("maintainType:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	

		return "maintainType/bind";
		
	}
	@RequiresPermissions("maintainType:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("maintainTypeId")Long maintainTypeId,@RequestParam(value="groupId")Long groupId){
		
		Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(1));
		model.addAttribute("maintainTypes", maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);
		return "maintainType/list";
		
	}
	@RequiresPermissions("maintainType:upload")
	@RequestMapping(value="/prepareUpload",method=RequestMethod.GET)
	public String prepareUpload(){
		return "maintainType/upload";
	}
	
/*	*//**
	 * 上传文件
	 * @param model
	 * @param file
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("maintainType:upload")
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(Model model,@RequestParam("file")MultipartFile file,HttpServletRequest request){
		Page<MaintainType> maintainTypePage=maintainTypeService.findAll(buildPageRequest(1));
		model.addAttribute("maintainTypes", maintainTypePage.getContent());
		model.addAttribute("page", maintainTypePage);
		
		if(!file.isEmpty()){
			 //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
            try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));
				List<MaintainType> uploadMaintainTypes=maintainTypeExcelService.getFromExcel(new File(realPath+"\\"+file.getOriginalFilename()));		
				maintainTypeService.saveWithCheckDuplicate(uploadMaintainTypes);
				log.info("上传用户:"+Arrays.toString(uploadMaintainTypes.toArray()));
			} catch (IOException e) {
				log.error("保存或读取文件出错");
				e.printStackTrace();
				return "saveFileError";
			} catch (BiffException e) {
				
				e.printStackTrace();
				return "fileStreamError";
			} catch (MaintainTypeDuplicateException e) {
				e.printStackTrace();
				log.warn("上传文件包含与数据库重复的对象");
				return "entityDuplicate";				
			} 
		}else{
			return "fileEmpty";
		}
		
		return "uploadSuccess";
	}
	
	
	@RequiresPermissions("maintainType:download")
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@RequestParam(value="downloadIds[]",required=false)Long[] downloadIds,HttpSession session) throws IOException{	
		String realPath=session.getServletContext().getRealPath("/WEB-INF/upload");
		String fileName="maintainTypeExport"+System.currentTimeMillis()+".xls";
		maintainTypeExcelService.saveToExcel(realPath+"\\"+fileName, downloadIds);
		HttpHeaders headers = new HttpHeaders();    
		headers.setContentDispositionFormData("attachment", fileName); 	
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	   // FileInputStream fin=new FileInputStream(new File(realPath+"\\"+fileName));	    
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(realPath+"\\"+fileName)),    
				                                  headers, HttpStatus.CREATED);
			
	}*/
}
