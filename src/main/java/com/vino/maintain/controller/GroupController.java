package com.vino.maintain.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

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

import com.vino.maintain.entity.Group;
import com.vino.maintain.exception.GroupDuplicateException;
import com.vino.maintain.service.GroupService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
import com.vino.scaffold.utils.Servlets;
import com.vino.scaffold.utils.Tree;
import com.vino.scaffold.utils.TreeUtils;
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController{
	@Autowired
	private GroupService groupService;
	@RequiresPermissions("group:menu")
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public String getALLGroups(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE+"") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<Group> groupPage=groupService.findAll(buildPageRequest(pageNumber));
		model.addAttribute("groups", groupPage.getContent());
		model.addAttribute("page", groupPage);
		//model.addAttribute("searchParams", "");
		return "group/list";
	}
	@RequiresPermissions("group:view")
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String getGroupsByCondition(Model model,Group group,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,ServletRequest request){
		Map<String,Object> searchParams=Servlets.getParametersStartingWith(request, "search_");
		log.info("搜索参数="+searchParams.toString());				
		Page<Group> groupPage=groupService.findGroupByCondition(searchParams, buildPageRequest(pageNumber));
		model.addAttribute("groups",groupPage.getContent());
		model.addAttribute("page", groupPage);	
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println("返回到页面的搜索参数"+Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		System.out.println(searchParams.toString());
		model.addAttribute("searchParamsMap", searchParams);
		return "group/list";
	}
	@RequiresPermissions("group:create")
	@RequestMapping(value="/prepareAdd",method=RequestMethod.GET)
	public String prepareAddGroup(Model model ){
		return "group/add";
	}
	@RequiresPermissions("group:create")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addGroup(Model model ,Group group,HttpSession session){
		try {
			groupService.saveWithCheckDuplicate(group);
			Page<Group> groupPage=groupService.findAll(buildPageRequest(1));
			model.addAttribute("groups", groupPage.getContent());
			model.addAttribute("page", groupPage);
			return "group/list";
			
		} catch (GroupDuplicateException e) {
			e.printStackTrace();
			return "forward:entityDuplicate";
		}
		
	}
	@ResponseBody
	@RequestMapping("/entityDuplicate")
	public String addDuplicateEntity(){
		
		return "entityDuplicate";	
	}
	
	/**
	 * 删除元素之前要先清除外键的依赖
	 */
	@RequiresPermissions("group:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public  String deleteGroups(Model model,@RequestParam("deleteIds[]")Long[] deleteIds){
	
		groupService.delete(deleteIds);
		Page<Group> groupPage=groupService.findAll(buildPageRequest(1));
		model.addAttribute("groups", groupPage.getContent());
		model.addAttribute("page", groupPage);
		return "group/list";
		
	}
	@RequiresPermissions("group:update")
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateGroup(Model model,Group group){
		groupService.update(group);
		Page<Group> groupPage=groupService.findAll(buildPageRequest(1));
		model.addAttribute("groups", groupPage.getContent());
		model.addAttribute("page", groupPage);
		return "group/list";
		
	}
	@RequiresPermissions("group:update")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateGroup(Model model,@PathVariable("id") Long id){
		model.addAttribute("group", groupService.findOne(id));
		return "group/edit";
		
	}
	@RequiresPermissions("group:view")
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findGroup(Model model,@PathVariable("id") Long id){
		model.addAttribute("group", groupService.findOne(id));
		return "group/detail";
		
	}
	/**
	 * 返回ZTREE格式group
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/json/all",method=RequestMethod.GET)
	public List<Tree> getGroupJson(){
		List<Group> groups=groupService.findAll();
		return TreeUtils.formatGroupsToTree(groups);
	}
	

}
