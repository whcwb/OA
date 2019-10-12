package com.ldz.biz.service;

import java.util.List;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizExceptionService extends BaseService<BizException, java.lang.String> {
	
	/**
	 * 查询所有异常配置信息
	 * @return
	 */
	public ApiResponse<List<BizExceptionConfig>> getAllConfig();
	
	/**
	 * 发生异常时调用该方法进行异常保存
	 * 必填项：
	 * xm-姓名
	 * sfzmhm-身份证号
	 * code-异常代码。根据具体异常来填写，异常代码必须是系统存在的代码
	 * 选填项
	 * kskm-考试科目。主要是约考和考试结果导入时需要填写
	 * lsh-当学员有流水号的时候填写
	 * @param exception
	 * @return
	 */
	public ApiResponse<String> saveException(BizException exception);
	
	/**
	 * 学员的异常已处置，将异常数据修改为已关闭
	 */
	public void clearException(TraineeInformation info, String code);
	
	/**
	 * 根据异常类型将异常学员统计并记录到异常表中
	 * @param config
	 */
	public void expJobSave(BizExceptionConfig config);
}