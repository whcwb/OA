package com.ldz.biz.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizExceptionMapper;
import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.BizExceptionConfigService;
import com.ldz.biz.service.BizExceptionService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class BizExceptionServiceImpl extends BaseServiceImpl<BizException, java.lang.String> implements BizExceptionService {

	@Autowired
	private BizExceptionMapper baseMapper;
	@Autowired
	private BizExceptionConfigService exceptionConfigService;
	@Autowired
	private TraineeInformationService traineeInfoService;
	
	@Override
	protected Mapper<BizException> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<List<BizExceptionConfig>> getAllConfig() {
		return ApiResponse.success(exceptionConfigService.findAll());
	}
	
	@Override
	public ApiResponse<String> saveException(BizException exception) {
		if (StringUtils.isBlank(exception.getSfzmhm())){
			return ApiResponse.fail("证件号码不能为空");
		}
		if (StringUtils.isBlank(exception.getCode())){
			return ApiResponse.fail("异常码不能为空");
		}
		SysYh user = getCurrentUser();
		exception.setId(String.valueOf(idGenerator.nextId()));
		exception.setCjsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		exception.setCjr(user.getZh()+"-"+user.getXm());
		exception.setBz(exceptionConfigService.getExpNameByCode(exception.getCode()));
		exception.setZt("00");
		save(exception);
		//给学员标记异常备注
		TraineeInformation traineeInfo = traineeInfoService.findByIdCardNo(exception.getSfzmhm());
		if (traineeInfo != null){
			traineeInfo.setCode(exception.getCode());
			traineeInfo.setErrorMessage(exception.getBz());
			traineeInfoService.update(traineeInfo);
		}
		
		return ApiResponse.success();
	}
	
	@Override
	public List<BizException> findByCode(String code) {
		Example condition = new Example(BizException.class);
		condition.and().andEqualTo(BizException.InnerColumn.code.name(), code);
		condition.setOrderByClause(BizException.InnerColumn.cjsj.asc());
		return baseMapper.selectByExample(condition);
	}

	@Override
	public void clearException(BizException info, String code) {
		if (StringUtils.isBlank(info.getSfzmhm())){
			return;
		}
		if (StringUtils.isBlank(info.getCode())){
			return;
		}
		SysYh user = getCurrentUser();
		//1.查询在办学员信息，只对在办学员进行信息异常处理
		TraineeInformation traineeInfo = traineeInfoService.findByIdCardNo(info.getSfzmhm());
		if (traineeInfo != null){
			//查询学员是否有相关类型未处理的异常信息
			Example condition = new Example(BizException.class);
			condition.and()
					.andEqualTo(BizException.InnerColumn.sfzmhm.name(), info.getSfzmhm())
					.andEqualTo(BizException.InnerColumn.zt.name(), "00");
			List<BizException> exps = baseMapper.selectByExample(condition);
			if (CollectionUtils.isNotEmpty(exps)){
				BizException otherEntity = null;
				for (int i=0; i<exps.size(); i++){
					BizException entity = exps.get(i);
					//将相同类型的异常标记为已处理
					if (code.equals(entity.getCode())){
						entity.setGxsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
						entity.setGxr(user.getZh()+"-"+user.getXm());
						entity.setZt("10");
						
						baseMapper.updateByPrimaryKey(entity);
					}else{
						otherEntity = entity;
					}
				}
				//将学员主表信息异常也标记为已处理，如果学员同时有其他异常信息，则更新其他异常信息
				if (code.equals(traineeInfo.getCode())){
					if (otherEntity == null){
						traineeInfo.setCode("");
						traineeInfo.setErrorMessage("");
					}else{
						traineeInfo.setCode(otherEntity.getCode());
						traineeInfo.setErrorMessage(otherEntity.getBz());
					}
					
					traineeInfoService.update(traineeInfo);
				}
			}
		}
	}

	@Override
	public void expJobSave(BizExceptionConfig config) {
		//SELECT * FROM `trainee_information` WHERE STATUS NOT IN ('50','60')
		if ("001".equals(config.getCode())){
			//报名审核
			
		}else if ("002".equals(config.getCode())){
			//收费确认
			
		}else if ("003".equals(config.getCode())){
			//受理确认
			
		}else if ("101".equals(config.getCode())){
			//科目一约考
			
		}else if ("201".equals(config.getCode())){
			//科目二约考
			
		}else if ("301".equals(config.getCode())){
			//科目三约考
			
		}else if ("102".equals(config.getCode())){
			//科目一成绩确认
			
		}else if ("202".equals(config.getCode())){
			//科目二成绩确认
			
		}else if ("302".equals(config.getCode())){
			//科目三成绩确认
			
		}
	}
}