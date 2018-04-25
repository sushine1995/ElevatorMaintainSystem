package com.vino.maintain.service.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vino.maintain.common.ElevatorConstants;
import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.MaintainOrder;
import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.repository.MaintainOrderRepository;
import com.vino.maintain.repository.MaintainPlanRepository;
import com.vino.maintain.repository.MaintainTypeRepository;
import com.vino.maintain.repository.notice.EmployeeNoticeRepository;
import com.vino.maintain.service.notice.EmployeeNoticeService;

@Service
@Transactional
public class MaintainPlanJob {
	@Autowired
	private MaintainPlanRepository maintainPlanRepository;
	@Autowired
	private MaintainOrderRepository maintainOrderRepository;
	@Autowired
	private MaintainTypeRepository maintainTypeRepository;
	@Autowired
	private EmployeeNoticeService employeeNoticeService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 日常月初保养，每月1日凌晨1:00生成工单 根据当前的保养计划列表来生成对应的保养工单 判断保养计划是否过期
	 * 
	 */
	@Scheduled(cron = "0 0 1 1 * ?")
	public void createMonthMidMaintainOrder() {
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.normal);
		DateTime current = new DateTime();
		int day = 15;
		int month = current.monthOfYear().get();
		int year = current.getYear();
		DateTime finalDate = new DateTime(year, month, day, 23, 59, 59);// 截止日期为每月15号的23:59:59
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());

		// 1.获取保养计划2.获取对应的设备3.生成对应的保养工单
		for (MaintainPlan m : maintainPlans) {
			List<ElevatorRecord> elevatorRecords = m.getElevatorRecords();
			for (ElevatorRecord e : elevatorRecords) {
				MaintainOrder order = new MaintainOrder();

				order.setElevatorRecord(e);
				order.setFinalTime(finalDate.toDate());
				//order.setGroup(e.getGroup());
				order.setMaintainType(maintainType);
				order.setCreateTime(new Date());
				order.setCreatorName("system");
				maintainOrderRepository.save(order);
			}
		}
		log.info("自动生成上半月保养工单");
	}

	/**
	 * 日常下半月保养，每月16日凌晨1:00生成工单 根据当前的保养计划列表来生成对应的保养工单 判断保养计划是否过期 秒 分 小时 天 月 年 星期
	 * 
	 */
	@Scheduled(cron = "0 0 1 16 * ?")
	public void createMonthEndMaintainOrder() {
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.normal);
		DateTime current = new DateTime();
		int day = current.dayOfMonth().withMaximumValue().getDayOfMonth();
		int month = current.monthOfYear().get();
		int year = current.getYear();
		DateTime finalDate = new DateTime(year, month, day, 23, 59, 59);// 截止日期为每月最后一天的23:59:59
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());
		// 1.获取保养计划2.获取对应的设备3.生成对应的保养工单
		for (MaintainPlan m : maintainPlans) {
			List<ElevatorRecord> elevatorRecords = m.getElevatorRecords();
			for (ElevatorRecord e : elevatorRecords) {
				MaintainOrder order = new MaintainOrder();

				order.setElevatorRecord(e);
				order.setFinalTime(finalDate.toDate());
				//order.setGroup(e.getGroup());
				order.setMaintainType(maintainType);
				order.setCreateTime(new Date());
				order.setCreatorName("system");
				maintainOrderRepository.save(order);
			}
		}
		log.info("自动生成下半月保养工单");

	}

	/**
	 * 年检,每天凌晨2点执行
	 * 
	 */
	@Scheduled(cron="0 0 2 1/1 * ? ")
	public void createYearMaintainOrder() {
		/**
		 * 1.从维保合同中选取未过期的合同
		 * 2.选取未过期的合同中的设备
		 * 3.判断设备上次的年检时间，若当前时间与上次年检时间已经差距11个月，则创建年检工单并进行提醒
		 */
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.annual_Inspection);
		
		DateTime current = new DateTime();
		int day = current.dayOfMonth().withMaximumValue().getDayOfMonth();
		int month = current.monthOfYear().get();
		int year = current.getYear();
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());
		// 1.获取保养计划2.获取对应的设备3.生成对应的保养工单
		for (MaintainPlan m : maintainPlans) {
			List<ElevatorRecord> elevatorRecords = m.getElevatorRecords();
			for (ElevatorRecord e : elevatorRecords) {
				if(e.getInspectionDate()==null){
					log.info("时间不合法");
					continue;
				}
				log.info("时间合法");
				DateTime lastInspectionDate=new DateTime(e.getInspectionDate());			
				DateTime finalTime=new DateTime(lastInspectionDate.getYear(),lastInspectionDate.getMonthOfYear(),
						lastInspectionDate.getDayOfMonth(),23,59,59);
								
				int gapDays=Days.daysBetween(lastInspectionDate, current).getDays();//当前时间与上次年检时间的间隔
				log.info("间隔时间"+gapDays);
				if(gapDays==(365-30)){//提前30天生成年检工单
					MaintainOrder order = new MaintainOrder();
				
					order.setElevatorRecord(e);
					order.setFinalTime(finalTime.toDate());//截止日期为上一次年检时间推后一年
					order.setMaintainType(maintainType);//年检类型
					order.setCreateTime(new Date());
					order.setCreatorName("system");
					maintainOrderRepository.save(order);
					log.info("生成了一个年检工单");
				}				
			}
		}
		log.info("定时扫描自动生成年度保养工单");
	}

	/**
	 * 每个月12,13，14，15,27,28,29,30,31号每天早上九点推送未完成的保养工单信息，提醒员工
	 */
	@Scheduled(cron = "0 0 9 12,13,14,15,27,28,29,30,31 * ? ")
	public void deadlinePush() {
		List<Long> groupIds = maintainOrderRepository.notFinishedOrder(ElevatorConstants.normal);
		
		Map<Long, Integer> countMap = new HashMap<>();// groupId为键，未完成count为值
		for (long groupId : groupIds) {
			if (countMap.containsKey(groupId)) {
				int count = countMap.get(groupId);
				count++;
				countMap.put(groupId, count);
			} else {
				countMap.put(groupId, 1);
			}
		}
		employeeNoticeService.pushMaintainDeadlineNotice(countMap);
		log.info("推送 保养工单截止日期将近");
	}
}
