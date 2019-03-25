package com.ldz.wechat.service.impl;

import com.google.common.collect.Maps;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.mapper.TraineeInformationMapper;
import com.ldz.wechat.mapper.TrainingRecordMapper;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.model.TrainingRecord;
import com.ldz.wechat.service.TraineeInformationService;
import com.ldz.wechat.service.TrainingRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainingRecordServiceImpl extends BaseServiceImpl<TrainingRecord, String> implements TrainingRecordService {

	@Autowired
	private TrainingRecordMapper baseMapper;

	@Autowired
	private TrainingRecordService trainingRecordService;

	@Autowired
	public TraineeInformationMapper informationMapper;

	@Autowired
	public TraineeInformationService traineeInformationService;
	
	@Override
	protected Mapper<TrainingRecord> getBaseMapper() {
		return baseMapper;
	}


	/**
	 * 查询培训记录，按时间倒序
	 * @param record
	 * @return
	 */
	@Override
	public ApiResponse<List<TrainingRecord>> getList(TrainingRecord record) {
		SimpleCondition condition = new SimpleCondition(TrainingRecord.class);
		condition.eq(TrainingRecord.InnerColumn.traineeId, record.getTraineeId());
		condition.setOrderByClause(TraineeInformation.InnerColumn.cjsj.desc());
		List<TrainingRecord> list = this.findByCondition(condition);
		return ApiResponse.success(list);

	}

	@Override
	public ApiResponse<Map> queryRecordByDay(String traineeId,String sub) {
		//查询所有培训时间
		List<String> dayList = baseMapper.countTrainRecordDays(traineeId,sub);
		Map repMap = Maps.newConcurrentMap();
		List repList = new ArrayList<>();
		for(String day :dayList){
			List<TrainingRecord> recordList = baseMapper.countTrainRecordByDay(day);
			repList.add(recordList);
		}
		repMap.put("repList",repList);
		return ApiResponse.success(repMap);
	}

	/**
	 * 学员确认培训
	 * @param trainingRecord
	 * @return
	 */
	@Override
	public ApiResponse<String> saveEntity(TrainingRecord trainingRecord) {
		RuntimeCheck.ifNull(trainingRecord, "参数不能为空");
		TrainingRecord record = baseMapper.selectByPrimaryKey(trainingRecord);
//		TrainingRecord record = baseMapper.queryByTraineeId(trainingRecord.getTraineeId(),trainingRecord.getTrainEndTime());
		record.setTrainEndTime(DateUtils.getNowTime());
		SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Long startTime = null;
		Long endTime = null;
		try {
			//计算学时 保存分钟
			endTime = inputDate.parse(DateUtils.getNowTime()).getTime();
			startTime = inputDate.parse(record.getTrainStartTime()).getTime();
			Double time = (double) (endTime - startTime)/(1000*60);
			int trainTimes = (int)Math.round(time);
			record.setTrainTimes(trainTimes);
		} catch (ParseException e) {
			e.printStackTrace();
			return ApiResponse.error();
		}
		baseMapper.updateByPrimaryKey(record);
		return ApiResponse.saveSuccess();
	}

	/**
	 *
	 * 保存用户培训信息
	 * @param eventKey
	 * @param openId
	 * @return
	 */
	@Override
	public String afterScan(String eventKey, String openId,String userId) {
		RuntimeCheck.ifBlank(openId,"用户openId为空");
//		TraineeInformation traineeInformation = informationMapper.queryByOpenId(openId);
		//这里根据用户id查询，会出现一个openid被两个用户绑定的情况
		TraineeInformation traineeInformation = traineeInformationService.findById(userId);
		//如果不是科目二或者科目三，就无法打卡
		RuntimeCheck.ifFalse("20".equals(traineeInformation.getStatus()) || "30".equals(traineeInformation.getStatus()), "您不是科目二或者科目三，无法签到！");
		TrainingRecord record = new TrainingRecord();
		record.setTraineeId(traineeInformation.getId());
		record.setId(genId());
		record.setCoachId(eventKey);
		record.setSub(traineeInformation.getStatus());
		record.setTrainStartTime(DateUtils.getNowTime());
		trainingRecordService.save(record);
		return "ok";
	}
	/**
	 *  该方法仅限从WEB端口请求的业务。 从微信事件触发的请求，请使用：afterScan 方法
	 * 从页发起的保存用户培训信息
	 * @param coachId  练ID
	 * @return
	 */
	@Override
	public void afterScanWeb(String coachId){
		TraineeInformation traineeInformation=getTraineeCurrentUser(true);
		TrainingRecord record = new TrainingRecord();
		record.setTraineeId(traineeInformation.getId());
		record.setId(genId());
		record.setCoachId(coachId);
//		record.setSub(traineeInformation.getStatus()); // TODO 这里的状态是不对的 目前扫码签到不能确定科目
		record.setTrainStartTime(DateUtils.getNowTime());
		trainingRecordService.save(record);
		return ;
	}

	/**
	 * 获取当前登录学员用户信息
	 * @return
	 */
	public static TraineeInformation getTraineeCurrentUser(boolean require) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		TraineeInformation userInfo = (TraineeInformation) request.getAttribute("wechatTraineeUserInfo");
		RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
		return userInfo;
	}
}