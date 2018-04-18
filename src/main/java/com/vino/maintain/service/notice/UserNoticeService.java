package com.vino.maintain.service.notice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.notice.UserNotice;
import com.vino.scaffold.service.base.BaseService;

public interface UserNoticeService extends BaseService<UserNotice, Long>{
	/**
	 * 
	 * @param faultOrder
	 */
	void sendFaultOrderFixedNotice(FaultOrder faultOrder);
	/**
	 * 获取未读通知的条数
	 * @return
	 */
	Long getNoReadNoticeCount();
	/**
	 * 获取未读通知
	 * @return
	 */
	List<UserNotice> findNoReadNotices();//查找未读通知
	/**
	 * 获取已读通知
	 * @return
	 */
	List<UserNotice> findReadNotices();//查找已读通知
	/**
	 * 通过通知ID设置该通知为已读
	 * @param id
	 */
	void setNoticeIsRead(long id);
	/**
	 * 获取未读通知，返回分页形式
	 * @param pageable
	 * @return
	 */
	Page<UserNotice> findNoReadNotices(Pageable pageable);
	/**
	 * 获取已读通知，返回分页形式
	 * @param pageable
	 * @return
	 */
	Page<UserNotice> findReadNotices(Pageable pageable);
}
