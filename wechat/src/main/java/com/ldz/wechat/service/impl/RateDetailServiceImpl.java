package com.ldz.wechat.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.mapper.CoachManagementMapper;
import com.ldz.wechat.mapper.RateDetailMapper;
import com.ldz.wechat.model.CoachValuation;
import com.ldz.wechat.model.RateDetail;
import com.ldz.wechat.model.ReduceManagement;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.RateDetailService;
import com.ldz.wechat.service.TraineeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by wangwei on 2018/9/18.
 */
@Service
public class RateDetailServiceImpl extends BaseServiceImpl<RateDetail, String> implements RateDetailService{

    @Autowired
    private RateDetailMapper baseMapper;

    @Autowired
    private TraineeInformationService traineeInformationService;
    @Override
    protected Mapper<RateDetail> getBaseMapper() {
        return baseMapper;
    }


    @Override
    public ApiResponse<String> saveDetail(RateDetail entity) {
        RuntimeCheck.ifBlank(entity.getCoachId(), "教练id不能为空");
        RuntimeCheck.ifBlank(entity.getTraineeId(), "学员id不能为空");
        TraineeInformation traineeInformation = traineeInformationService.findById(entity.getTraineeId());
        SimpleCondition condition = new SimpleCondition(RateDetail.class);
        String time = DateUtils.getToday();
        //一个用户每天只能评价一次
        condition.like(RateDetail.InnerColumn.cjsj, time);
        condition.eq(RateDetail.InnerColumn.traineeId, entity.getTraineeId());
        List<RateDetail> list = this.findByCondition(condition);
        if(list != null && list.size() > 0){
            return ApiResponse.fail("您今日已评论，请明日再试");
        }else{
            if (traineeInformation != null)
            entity.setHeadImg(traineeInformation.getHeadImg());
            entity.setCjsj(DateUtils.getNowTime());
            entity.setId(genId());
            this.save(entity);
            return ApiResponse.saveSuccess();
        }
    }

    @Override
    public ApiResponse<List<RateDetail>> queryPager(Page<RateDetail> pager, RateDetail entity) {
        ApiResponse<List<RateDetail>> result = new ApiResponse<>();
        SimpleCondition simpleCondition = new SimpleCondition(RateDetail.class);
        //只查询评论可见的
        simpleCondition.eq(RateDetail.InnerColumn.visiable,"1");
        simpleCondition.eq(RateDetail.InnerColumn.coachId,entity.getCoachId());
        PageInfo<RateDetail> page = findPage(pager, simpleCondition);
        result.setPage(page);
        return result;
    }
}
