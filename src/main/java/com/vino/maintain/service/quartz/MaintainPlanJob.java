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
	 * �ճ��³�������ÿ��1���賿1:00���ɹ��� ���ݵ�ǰ�ı����ƻ��б������ɶ�Ӧ�ı������� �жϱ����ƻ��Ƿ����
	 * 
	 */
	@Scheduled(cron = "0 0 1 1 * ?")
	public void createMonthMidMaintainOrder() {
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.normal);
		DateTime current = new DateTime();
		int day = 15;
		int month = current.monthOfYear().get();
		int year = current.getYear();
		DateTime finalDate = new DateTime(year, month, day, 23, 59, 59);// ��ֹ����Ϊÿ��15�ŵ�23:59:59
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());

		// 1.��ȡ�����ƻ�2.��ȡ��Ӧ���豸3.���ɶ�Ӧ�ı�������
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
		log.info("�Զ������ϰ��±�������");
	}

	/**
	 * �ճ��°��±�����ÿ��16���賿1:00���ɹ��� ���ݵ�ǰ�ı����ƻ��б������ɶ�Ӧ�ı������� �жϱ����ƻ��Ƿ���� �� �� Сʱ �� �� �� ����
	 * 
	 */
	@Scheduled(cron = "0 0 1 16 * ?")
	public void createMonthEndMaintainOrder() {
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.normal);
		DateTime current = new DateTime();
		int day = current.dayOfMonth().withMaximumValue().getDayOfMonth();
		int month = current.monthOfYear().get();
		int year = current.getYear();
		DateTime finalDate = new DateTime(year, month, day, 23, 59, 59);// ��ֹ����Ϊÿ�����һ���23:59:59
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());
		// 1.��ȡ�����ƻ�2.��ȡ��Ӧ���豸3.���ɶ�Ӧ�ı�������
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
		log.info("�Զ������°��±�������");

	}

	/**
	 * ���,ÿ���賿2��ִ��
	 * 
	 */
	@Scheduled(cron="0 0 2 1/1 * ? ")
	public void createYearMaintainOrder() {
		/**
		 * 1.��ά����ͬ��ѡȡδ���ڵĺ�ͬ
		 * 2.ѡȡδ���ڵĺ�ͬ�е��豸
		 * 3.�ж��豸�ϴε����ʱ�䣬����ǰʱ�����ϴ����ʱ���Ѿ����11���£��򴴽���칤������������
		 */
		MaintainType maintainType = maintainTypeRepository.findByName(ElevatorConstants.annual_Inspection);
		
		DateTime current = new DateTime();
		int day = current.dayOfMonth().withMaximumValue().getDayOfMonth();
		int month = current.monthOfYear().get();
		int year = current.getYear();
		List<MaintainPlan> maintainPlans = maintainPlanRepository
				.findAvailalbeMaintainPlans(new LocalDate(year, month, day).toDate());
		// 1.��ȡ�����ƻ�2.��ȡ��Ӧ���豸3.���ɶ�Ӧ�ı�������
		for (MaintainPlan m : maintainPlans) {
			List<ElevatorRecord> elevatorRecords = m.getElevatorRecords();
			for (ElevatorRecord e : elevatorRecords) {
				if(e.getInspectionDate()==null){
					log.info("ʱ�䲻�Ϸ�");
					continue;
				}
				log.info("ʱ��Ϸ�");
				DateTime lastInspectionDate=new DateTime(e.getInspectionDate());			
				DateTime finalTime=new DateTime(lastInspectionDate.getYear(),lastInspectionDate.getMonthOfYear(),
						lastInspectionDate.getDayOfMonth(),23,59,59);
								
				int gapDays=Days.daysBetween(lastInspectionDate, current).getDays();//��ǰʱ�����ϴ����ʱ��ļ��
				log.info("���ʱ��"+gapDays);
				if(gapDays==(365-30)){//��ǰ30��������칤��
					MaintainOrder order = new MaintainOrder();
				
					order.setElevatorRecord(e);
					order.setFinalTime(finalTime.toDate());//��ֹ����Ϊ��һ�����ʱ���ƺ�һ��
					order.setMaintainType(maintainType);//�������
					order.setCreateTime(new Date());
					order.setCreatorName("system");
					maintainOrderRepository.save(order);
					log.info("������һ����칤��");
				}				
			}
		}
		log.info("��ʱɨ���Զ�������ȱ�������");
	}

	/**
	 * ÿ����12,13��14��15,27,28,29,30,31��ÿ�����Ͼŵ�����δ��ɵı���������Ϣ������Ա��
	 */
	@Scheduled(cron = "0 0 9 12,13,14,15,27,28,29,30,31 * ? ")
	public void deadlinePush() {
		List<Long> groupIds = maintainOrderRepository.notFinishedOrder(ElevatorConstants.normal);
		
		Map<Long, Integer> countMap = new HashMap<>();// groupIdΪ����δ���countΪֵ
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
		log.info("���� ����������ֹ���ڽ���");
	}
}
