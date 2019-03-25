package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.BizLcWxjlMapper;
import com.ldz.biz.model.BizLcWxjl;
import com.ldz.biz.service.BizLcWxjlService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizLcWxjlServiceImpl extends BaseServiceImpl<BizLcWxjl, String> implements BizLcWxjlService {

	@Autowired
	private BizLcWxjlMapper baseMapper;
	
	@Override
	protected Mapper<BizLcWxjl> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> saveEntity(BizLcWxjl entity) {
		SysYh user = getCurrentUser();
//		RuntimeCheck.ifBlank(entity.getJlJx(), "驾校名称不能为空");
		RuntimeCheck.ifBlank(entity.getJlXm(), "教练姓名不能为空");
		entity.setId(genId());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		if(StringUtils.isBlank(entity.getJlJx())){
			entity.setJlJx("无驾校");
		}
        save(entity);
		ApiResponse<String> res = new ApiResponse<>();
		res.setMessage("操作成功");
		res.setResult(entity.getId());
		return res;
    }
}