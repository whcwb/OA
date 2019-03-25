package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ZgTjjlMapper;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.ZgTjjl;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.ZgTjjlService;
import com.ldz.biz.service.ZgjbxxService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ZgTjjlServiceImpl  extends BaseServiceImpl<ZgTjjl,String> implements ZgTjjlService  {
    @Autowired
    private ZgTjjlMapper baseMapper;
    @Autowired
    private ZgjbxxService zgjbxxService;
    @Autowired
    private TraineeInformationService informationService;

    @Override
    protected Mapper<ZgTjjl> getBaseMapper() {
        return baseMapper;
    }
    @Override
    public int countByTime(String startTime,String endTime,String zgId){
        return baseMapper.countByTime(startTime,endTime,zgId);
    }

    @Override
    public ApiResponse<String> getReferrerRecord(Page<ZgTjjl> page) {
        ApiResponse<String> result = new ApiResponse<>();
        String status = getRequestParamterAsString("status");
        LimitedCondition condition = getQueryCondition();
        PageInfo<ZgTjjl> pageInfo = findPage(page, condition);
        for (ZgTjjl zgTjjl : pageInfo.getList()) {

            SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
            simpleCondition.eq(TraineeInformation.InnerColumn.id,zgTjjl.getTraineeId());
            if(StringUtils.isNotBlank(status)){
                simpleCondition.eq(TraineeInformation.InnerColumn.status,status);
            }
            List<TraineeInformation> byCondition = informationService.findByCondition(simpleCondition);
            if(CollectionUtils.isNotEmpty(byCondition)){
                zgTjjl.setInformation(byCondition.get(0));
            }
            zgTjjl.setZgjbxx(zgjbxxService.findById(zgTjjl.getZgId()));
        }
        result.setPage(pageInfo);

        return result;
    }


}
