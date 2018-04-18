package com.vino.maintain.repository.notice;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vino.maintain.entity.notice.UserNotice;
import com.vino.scaffold.repository.base.BaseRepository;

public interface UserNoticeRepository extends BaseRepository<UserNotice, Long>{
	@Query("select count(*) from UserNotice u where u.isRead=false")
	public Long getNoReadNoticeCount();
	@Query("select n from UserNotice n where n.isRead=?1 order by n.createTime desc")
	public List<UserNotice> findUserNoticesByIsReadOrderByCreateTime(Boolean isRead);
	@Modifying
	@Query("update UserNotice u set u.isRead=true where u.id=?1")
	public void setUserNoticeIsRead(long id);

}
