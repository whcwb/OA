package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.wechat.service.PicRotationService;
import com.ldz.wechat.mapper.PicRotationMapper;
import com.ldz.wechat.model.PicRotation;

import java.util.List;

@Service
public class PicRotationServiceImpl extends BaseServiceImpl<PicRotation, String> implements PicRotationService {

	@Autowired
	private PicRotationMapper baseMapper;
	
	@Override
	protected Mapper<PicRotation> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<List<PicRotation>> queryPic(PicRotation picRotation) {
		SimpleCondition condition = new SimpleCondition(PicRotation.class);
		if(StringUtils.isNotBlank(picRotation.getType())){
			condition.eq(PicRotation.InnerColumn.type,picRotation.getType());
		}
		//只查询生效的
		condition.eq(PicRotation.InnerColumn.effective,"1");
		condition.setOrderByClause(PicRotation.InnerColumn.picSort.asc());
		List<PicRotation> list = this.findByCondition(condition);
		return ApiResponse.success(list);
    }
}