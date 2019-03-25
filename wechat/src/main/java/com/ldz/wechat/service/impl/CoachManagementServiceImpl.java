package com.ldz.wechat.service.impl;


import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.mapper.CoachManagementMapper;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.CoachManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CoachManagementServiceImpl extends BaseServiceImpl<CoachManagement, String> implements CoachManagementService {

	@Autowired
	private CoachManagementMapper baseMapper;

	
	@Override
	protected Mapper<CoachManagement> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 通过手机号码查询教练员信息
	 * 如果
	 * @param phone
	 * @return
	 */
	@Override
	public CoachManagement queryPhone(String phone){
		SimpleCondition condition = new SimpleCondition(CoachManagement.class);
		condition.eq(CoachManagement.InnerColumn.phone, phone);
		condition.setOrderByClause(CoachManagement.InnerColumn.id.desc());
		List<CoachManagement> list = this.findByCondition(condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

    @Override
    public CoachManagement queryBySenceUrl(String url) {
		SimpleCondition condition = new SimpleCondition(CoachManagement.class);
		condition.eq(CoachManagement.InnerColumn.senceUrl, url);
		List<CoachManagement> list = this.findByCondition(condition);
		if(list != null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
    }

    @Override
    public ApiResponse<PageInfo<Map>> queryPage(Page<Map> page) {
		PageInfo<Map> resultPage = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				baseMapper.getRankList();
			}
		});
		return ApiResponse.success(resultPage);
	}

	@Override
	public ApiResponse<PageInfo<CoachManagement>> getCoachPage(int pageNum,int pageSize,CoachManagement coachManagement,String orderBy) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		PageInfo<CoachManagement> resultPage = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				baseMapper.getCoachList(coachManagement.getCoachSub(),coachManagement.getGender(),orderBy);

			}
		});
		return ApiResponse.success(resultPage);
	}
}