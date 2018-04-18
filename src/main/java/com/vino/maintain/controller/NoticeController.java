package com.vino.maintain.controller;

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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.notice.UserNotice;
import com.vino.maintain.service.notice.EmployeeNoticeService;
import com.vino.maintain.service.notice.UserNoticeService;
import com.vino.scaffold.controller.base.BaseController;
import com.vino.scaffold.entity.Constants;
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private EmployeeNoticeService employeeNoticeService;
	public Pageable buildPageRequestSortByCreateTimeDesc(int pageNumber) {
		
		return new PageRequest(pageNumber-1, Constants.PAGE_SIZE, new Sort(Direction.DESC,
				"createTime"));
	}
	
	
	@RequestMapping(value="/noRead",method=RequestMethod.GET)
	public String viewNoRead(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber){
	
		long noReadNoticeCount=userNoticeService.getNoReadNoticeCount();
		Page<UserNotice> noReadNoticePage=userNoticeService.findNoReadNotices(buildPageRequestSortByCreateTimeDesc(pageNumber));
		model.addAttribute("noReadNotices", noReadNoticePage.getContent());
		model.addAttribute("page", noReadNoticePage);
		model.addAttribute("noReadNoticeCount", noReadNoticeCount);
		return "notice/list";
	}
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public String viewRead(Model model,@RequestParam(value="pageNumber",defaultValue="1")int pageNumber){
		
		Page<UserNotice> readNoticePage=userNoticeService.findReadNotices(buildPageRequestSortByCreateTimeDesc(pageNumber));
		model.addAttribute("readNotices", readNoticePage.getContent());
		model.addAttribute("page", readNoticePage);
		long noReadNoticeCount=userNoticeService.getNoReadNoticeCount();
		model.addAttribute("noReadNoticeCount", noReadNoticeCount);
		return "notice/readList";
	}
	/**
	 * �����Ϣ���鿴����ͱ�Ϊ�Ѷ�֪ͨ
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/read/{id}",method=RequestMethod.GET)
	public String readNotice(Model model,@PathVariable long id){
		UserNotice notice=userNoticeService.findOne(id);		
		userNoticeService.setNoticeIsRead(id);//��Ϊ�Ѷ�
		model.addAttribute("notice", notice);
		//������Ϣ���������ݣ�ʹ��json����������
		List<UserNotice> notices=userNoticeService.findNoReadNotices();
		Map<String,Object> result=new HashMap<>();
		result.put("noReadCount",notices.size());
		result.put("noReadNotices", notices);
		
		template.convertAndSend("/notice/count", result);//convert���Զ�������ת����json��ʽ,������Ϣ�����Ǳ�
		return "notice/detail";
	}
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String detail(Model model,@PathVariable long id){
		UserNotice notice=userNoticeService.findOne(id);		
		model.addAttribute("notice", notice);
		return "notice/detail";
	}
	@RequestMapping(value="/preparePush",method=RequestMethod.GET)
	public String preparePush(Model model){
		long noReadNoticeCount=userNoticeService.getNoReadNoticeCount();
		model.addAttribute("noReadNoticeCount", noReadNoticeCount);
		return "notice/push";
	}
	@ResponseBody
	@RequestMapping(value="/push",method=RequestMethod.POST)
	public String push(@RequestParam(required=false,name="groupIds[]")Long[] groupIds,String title,String content,String type){
		if("group".equals(type)&&groupIds!=null){
			employeeNoticeService.pushGroups(groupIds, title, content);
		}else if("app".equals(type)){
			employeeNoticeService.pushApp(title, content);
		}else{
			
		}
		return "success";
	}
}
