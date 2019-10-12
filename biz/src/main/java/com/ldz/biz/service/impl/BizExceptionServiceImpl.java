package com.ldz.biz.service.impl;

import java.util.List;

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
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class BizExceptionServiceImpl extends BaseServiceImpl<BizException, java.lang.String> implements BizExceptionService {

	@Autowired
	private BizExceptionMapper baseMapper;
	@Autowired
	private BizExceptionConfigService exceptionConfigService;
	
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
		if (StringUtils.isBlank(exception.getXm())){
			return ApiResponse.fail("学员姓名不能为空");
		}
		if (StringUtils.isBlank(exception.getSfzmhm())){
			return ApiResponse.fail("证件号码不能为空");
		}
		if (StringUtils.isBlank(exception.getCode())){
			return ApiResponse.fail("异常码不能为空");
		}
		
		exception.setId(String.valueOf(idGenerator.nextId()));
		exception.setCjsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		exception.setBz(exceptionConfigService.getExpNameByCode(exception.getCode()));
		exception.setZt("00");
		save(exception);
		
		return ApiResponse.success();
	}

	@Override
	public void clearException(BizException info, String code) {
		
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