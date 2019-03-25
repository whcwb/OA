package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ComplainRecordMapper;
import com.ldz.biz.model.ComplainRecord;
import com.ldz.biz.service.ComplainRecordService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ComplainRecordServiceImpl extends BaseServiceImpl<ComplainRecord, String> implements ComplainRecordService {

	@Autowired
	private ComplainRecordMapper baseMapper;

	@Autowired
	private WxOperateLogService wxLog;
	
	@Override
	protected Mapper<ComplainRecord> getBaseMapper() {
		return baseMapper;
	}
	/**
	 * 按指定字段进行排序
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		condition.setOrderByClause(" id desc");
		return true;
	}


	@Override
	public ApiResponse<String> updateCallBack(ComplainRecord obj){
		SysYh currentUser = getCurrentUser();
		obj.setOperateTime(DateUtils.getNowTime());
		obj.setOperater(currentUser.getYhid());
		obj.setOperaterName(currentUser.getXm());

		wxLog.saveEntity(currentUser,obj.getId(),obj.getComplainStatus(),obj.getFeedback(),"1");
		return this.update(obj)>0?ApiResponse.success():ApiResponse.fail("数据库操作失败");
	}

}