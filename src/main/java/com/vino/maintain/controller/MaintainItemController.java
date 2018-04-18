package com.vino.maintain.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vino.maintain.entity.MaintainItem;
import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.exception.MaintainItemDuplicateException;
import com.vino.maintain.service.MaintainItemService;
import com.vino.maintain.service.MaintainTypeService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
@Controller
@RequestMapping("/maintainItem")
public class MaintainItemController extends BaseController {
	@Autowired
	private MaintainItemService maintainItemService;
	@Autowired
	private MaintainTypeService maintainTypeService;
	@RequiresPermissions("maintainItem:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLMaintainItems(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("maintainItems", maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);
		//model.addAttribute("searchParams", "");
		return "maintainItem/list";
	}
	@RequiresPermissions("maintainItem:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getMaintainItemsByCondition(Model model,MaintainItem maintainItem,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<MaintainItem> maintainItemPage=maintainItemService.findMaintainItemByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("maintainItems",maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		
		model.addAttribute("searchParamsMap", searchParams);
		return "maintainItem/list";
	}
	@RequiresPermissions("maintainItem:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddMaintainItem(Model model ){
		List<MaintainType> maintainTypes=maintainTypeService.findAll();
		model.addAttribute("maintainTypes", maintainTypes);
		return "maintainItem/add";
	}
	@RequiresPermissions("maintainItem:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addMaintainItem(Model model,MaintainItem maintainItem){		
		try {
			maintainItemService.saveWithCheckDuplicate(maintainItem);
			Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(1));
			model.addAttribute("maintainItems", maintainItemPage.getContent());
			model.addAttribute("page", maintainItemPage);
			return "maintainItem/list";	
		} catch (MaintainItemDuplicateException e) {
			
			e.printStackTrace();
			return "forward:entityDuplicate";
		}					
	
	}
	@ResponseBody
	@RequestMapping("/entityDuplicate")
	public String addDuplicateEntity(){
		
		return "entityDuplicate";	
	}
	@RequiresPermissions("maintainItem:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteMaintainItems(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		maintainItemService.delete(deleteIds);
		Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(1));
		model.addAttribute("maintainItems", maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);
		return "maintainItem/list";
		
	}
	@RequiresPermissions("maintainItem:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateMaintainItem(Model model,MaintainItem maintainItem){
		maintainItemService.bindType(maintainItem.getId(),maintainItem.getMaintainType().getId());
		if(maintainItem.getMaintainType().getId()==0)
			maintainItem.setMaintainType(null);
		maintainItemService.update(maintainItem);
		Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(1));
		model.addAttribute("maintainItems", maintainItemPage.getContent());
		System.out.println(maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);
		return "maintainItem/list";
		
	}
	@RequiresPermissions("maintainItem:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateMaintainItem(Model model,@PathVariable("id") Long id){
		List<MaintainType> maintainTypes=maintainTypeService.findAll();
		MaintainItem maintainItem=maintainItemService.findOne(id);
		if(maintainItem.getMaintainType()!=null)
			maintainTypes.remove(maintainItem.getMaintainType());
		model.addAttribute("availableMaintainTypes",maintainTypes );
		model.addAttribute("maintainItem", maintainItem);
		return "maintainItem/edit";
		
	}
	@RequiresPermissions("maintainItem:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findMaintainItem(Model model,@PathVariable("id") Long id){
		model.addAttribute("maintainItem", maintainItemService.findOne(id));
		return "maintainItem/detail";
		
	}
	
	@RequiresPermissions("maintainItem:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	

		return "maintainItem/bind";
		
	}
	@RequiresPermissions("maintainItem:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("maintainItemId")Long maintainItemId,@RequestParam(value="maintainTypeId")Long maintainTypeId){
		boolean result=maintainItemService.bindType(maintainItemId, maintainTypeId);
		Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(1));
		model.addAttribute("maintainItems", maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);
		return "maintainItem/list";
		
	}
	@RequiresPermissions("maintainItem:upload")
	@RequestMapping(value="/prepareUpload",method=RequestMethod.GET)
	public String prepareUpload(){
		return "maintainItem/upload";
	}
	
/*	*//**
	 * 上传文件
	 * @param model
	 * @param file
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("maintainItem:upload")
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(Model model,@RequestParam("file")MultipartFile file,HttpServletRequest request){
		Page<MaintainItem> maintainItemPage=maintainItemService.findAll(buildPageRequest(1));
		model.addAttribute("maintainItems", maintainItemPage.getContent());
		model.addAttribute("page", maintainItemPage);
		
		if(!file.isEmpty()){
			 //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
            try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));
				List<MaintainItem> uploadMaintainItems=maintainItemExcelService.getFromExcel(new File(realPath+"\\"+file.getOriginalFilename()));		
				maintainItemService.saveWithCheckDuplicate(uploadMaintainItems);
				log.info("上传用户:"+Arrays.toString(uploadMaintainItems.toArray()));
			} catch (IOException e) {
				log.error("保存或读取文件出错");
				e.printStackTrace();
				return "saveFileError";
			} catch (BiffException e) {
				
				e.printStackTrace();
				return "fileStreamError";
			} catch (MaintainItemDuplicateException e) {
				e.printStackTrace();
				log.warn("上传文件包含与数据库重复的对象");
				return "entityDuplicate";				
			} 
		}else{
			return "fileEmpty";
		}
		
		return "uploadSuccess";
	}
	
	
	@RequiresPermissions("maintainItem:download")
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@RequestParam(value="downloadIds[]",required=false)Long[] downloadIds,HttpSession session) throws IOException{	
		String realPath=session.getServletContext().getRealPath("/WEB-INF/upload");
		String fileName="maintainItemExport"+System.currentTimeMillis()+".xls";
		maintainItemExcelService.saveToExcel(realPath+"\\"+fileName, downloadIds);
		HttpHeaders headers = new HttpHeaders();    
		headers.setContentDispositionFormData("attachment", fileName); 	
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	   // FileInputStream fin=new FileInputStream(new File(realPath+"\\"+fileName));	    
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(realPath+"\\"+fileName)),    
				                                  headers, HttpStatus.CREATED);
			
	}*/
}
