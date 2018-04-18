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

import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.Group;
import com.vino.maintain.exception.EmployeeDuplicateException;
import com.vino.maintain.service.EmployeeService;
import com.vino.maintain.service.GroupService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;


import com.vino.scaffold.utils.Servlets;


@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController{
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private GroupService groupService;
	@RequiresPermissions("employee:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLEmployees(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<Employee> employeePage=employeeService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("employees", employeePage.getContent());
		model.addAttribute("page", employeePage);
		//model.addAttribute("searchParams", "");
		return "employee/list";
	}
	@RequiresPermissions("employee:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getEmployeesByCondition(Model model,Employee employee,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<Employee> employeePage=employeeService.findEmployeeByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("employees",employeePage.getContent());
		model.addAttribute("page", employeePage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println("返回到页面的搜索参数"+Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println(searchParams.toString());
		model.addAttribute("searchParamsMap", searchParams);
		return "employee/list";
	}
	@RequiresPermissions("employee:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddEmployee(Model model ){
		return "employee/add";
	}
	
	@RequiresPermissions("employee:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addEmployee(Model model ,Employee employee,HttpServletResponse response){
		try {
			if(null==employee.getPassword()||"".equals(employee.getPassword()))//当没有设置密码的时候，使用默认密码
				employee.setPassword(Constants.DEFAULT_PASSWORD);
			employeeService.saveWithCheckDuplicate(employee);
			Page<Employee> employeePage=employeeService.findAll(buildPageRequest(1));
			model.addAttribute("employees", employeePage.getContent());
			model.addAttribute("page", employeePage);
			return "employee/list";	
		} catch (EmployeeDuplicateException e) {				
			e.printStackTrace();						
			return "forward:entityDuplicate";
		}
		
		
	}
	@ResponseBody
	@RequestMapping("/entityDuplicate")
	public String addDuplicateEntity(){
		
		return "entityDuplicate";	
	}
	
	@RequiresPermissions("employee:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteEmployees(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
		employeeService.delete(deleteIds);
		Page<Employee> employeePage=employeeService.findAll(buildPageRequest(1));
		model.addAttribute("employees", employeePage.getContent());
		model.addAttribute("page", employeePage);
		return "employee/list";
		
	}
	@RequiresPermissions("employee:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateEmployee(Model model,Employee employee){
		employeeService.update(employee);
		Page<Employee> employeePage=employeeService.findAll(buildPageRequest(1));
		model.addAttribute("employees", employeePage.getContent());
		model.addAttribute("page", employeePage);
		return "employee/list";
		
	}
	@RequiresPermissions("employee:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateEmployee(Model model,@PathVariable("id") Long id){
		model.addAttribute("employee", employeeService.findOne(id));
		return "employee/edit";
		
	}
	@RequiresPermissions("employee:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findEmployee(Model model,@PathVariable("id") Long id){
		model.addAttribute("employee", employeeService.findOne(id));
		return "employee/detail";
		
	}
	
	@RequiresPermissions("employee:bind")
	@RequestMapping(value="/prepareBind/{id}",method=RequestMethod.GET)
	public String prepareBind(Model model,@PathVariable("id") Long id){
	
		Employee employee=employeeService.findOne(id);
		model.addAttribute("employee", employee);
		List<Group> groups=groupService.findAll();
		if(employee.getGroup()!=null)
			groups.remove(employee.getGroup());
		model.addAttribute("availableGroups",groups);//可供选择的小组
		return "employee/bind";
		
	}
	@RequiresPermissions("employee:bind")
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public String bind(Model model,@RequestParam("employeeId")Long employeeId,@RequestParam(value="groupId")Long groupId){
		boolean result=employeeService.bindGroup(employeeId, groupId);
	
		Page<Employee> employeePage=employeeService.findAll(buildPageRequest(1));
		model.addAttribute("employees", employeePage.getContent());
		model.addAttribute("page", employeePage);
		return "employee/list";
		
	}
	@RequiresPermissions("employee:upload")
	@RequestMapping(value="/prepareUpload",method=RequestMethod.GET)
	public String prepareUpload(){
		return "employee/upload";
	}
	
/*	*//**
	 * 上传文件
	 * @param model
	 * @param file
	 * @param request
	 * @return
	 *//*
	@RequiresPermissions("employee:upload")
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(Model model,@RequestParam("file")MultipartFile file,HttpServletRequest request){
		Page<Employee> employeePage=employeeService.findAll(buildPageRequest(1));
		model.addAttribute("employees", employeePage.getContent());
		model.addAttribute("page", employeePage);
		
		if(!file.isEmpty()){
			 //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
            try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));
				List<Employee> uploadEmployees=employeeExcelService.getFromExcel(new File(realPath+"\\"+file.getOriginalFilename()));		
				employeeService.saveWithCheckDuplicate(uploadEmployees);
				log.info("上传用户:"+Arrays.toString(uploadEmployees.toArray()));
			} catch (IOException e) {
				log.error("保存或读取文件出错");
				e.printStackTrace();
				return "saveFileError";
			} catch (BiffException e) {
				
				e.printStackTrace();
				return "fileStreamError";
			} catch (EmployeeDuplicateException e) {
				e.printStackTrace();
				log.warn("上传文件包含与数据库重复的对象");
				return "entityDuplicate";				
			} 
		}else{
			return "fileEmpty";
		}
		
		return "uploadSuccess";
	}
	
	
	@RequiresPermissions("employee:download")
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(@RequestParam(value="downloadIds[]",required=false)Long[] downloadIds,HttpSession session) throws IOException{	
		String realPath=session.getServletContext().getRealPath("/WEB-INF/upload");
		String fileName="employeeExport"+System.currentTimeMillis()+".xls";
		employeeExcelService.saveToExcel(realPath+"\\"+fileName, downloadIds);
		HttpHeaders headers = new HttpHeaders();    
		headers.setContentDispositionFormData("attachment", fileName); 	
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	   // FileInputStream fin=new FileInputStream(new File(realPath+"\\"+fileName));	    
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(realPath+"\\"+fileName)),    
				                                  headers, HttpStatus.CREATED);
			
	}*/
}
