package com.ldz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.FileUploadRecordMapper;
import com.ldz.biz.model.FileUploadRecord;
import com.ldz.biz.service.FileUploadRecordService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


@Service
public class FileUploadRecordServiceImpl extends BaseServiceImpl<FileUploadRecord, String> implements FileUploadRecordService {

	@Autowired
	private FileUploadRecordMapper baseMapper;
	
	@Override
	protected Mapper<FileUploadRecord> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> getFileId(String lx, int pageNum, int pageSize) {

		ApiResponse<String> result = new ApiResponse<>();

		String today = DateUtils.getToday("yyyy-MM-dd");
		String preMonth = DateUtils.getPreMonth();
		SimpleCondition condition = new SimpleCondition(FileUploadRecord.class);
		condition.eq(FileUploadRecord.InnerColumn.lx,lx);
		condition.gte(FileUploadRecord.InnerColumn.cjsj,preMonth+" 00:00:00");
		condition.lte(FileUploadRecord.InnerColumn.cjsj, today+ " 23:59:59");
		condition.setOrderByClause(" cjsj desc ");
		PageInfo<FileUploadRecord> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
			findByCondition(condition);
		});
		result.setPage(pageInfo);

		return result;
	}
}