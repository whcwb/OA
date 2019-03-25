package com.ldz.wechat.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.WxBaseController;
import com.ldz.wechat.mapper.TraineeInformationMapper;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.CoachTraineeRercord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.TraineeInformationService;
import com.ldz.wechat.service.TraineeStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


@Service
public class TraineeInformationServiceImpl extends BaseServiceImpl<TraineeInformation, String> implements TraineeInformationService {

    @Autowired
    private TraineeInformationMapper baseMapper;

    @Autowired // 学员状态表
    private TraineeStatusService traineeStatusService;

    @Override
    protected Mapper<TraineeInformation> getBaseMapper() {
        return baseMapper;
    }

    /**
     * 通过手机号码查询学员信息
     * 如果
     * @param phone
     * @return
     */
    @Override
    public TraineeInformation queryPhone(String phone){
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.like(TraineeInformation.InnerColumn.phone, phone);
        condition.setOrderByClause(TraineeInformation.InnerColumn.id.desc());
        List<TraineeInformation> list = this.findByCondition(condition);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public ApiResponse<PageInfo<Map>> getTraineeList(CoachTraineeRercord coachTraineeRercord,String status ,String carType,Page page) {
        PageInfo<Map> resultPage = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                baseMapper.getCoachTrain(coachTraineeRercord.getCoachId(),status,carType,coachTraineeRercord.getAllotSub());

            }
        });
        return ApiResponse.success(resultPage) ;
    }


    /**
     * 通过学员id和科目查询教练
     * @param traineeId
     * @param sub
     * @return
     */
    @Override
    public ApiResponse<CoachManagement> queryMyCoach(String traineeId, String sub) {
        return ApiResponse.success(baseMapper.getMyCoachInfo(traineeId,sub));
    }

    /**
     * 教练查询我的学员中分配时间最长的前十人
     * @param coachId
     * @return
     */
    @Override
    public ApiResponse<List<Map>> queryTop10Trainee(String coachId) {
        return ApiResponse.success(baseMapper.getTop10(coachId));
    }

    @Override
    public ApiResponse<List<TraineeInformation>> queryPager(String startTime, String endTime, Page<TraineeInformation> pager,TraineeInformation info) {
        if(StringUtils.isNotEmpty(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNotEmpty(endTime)){
            endTime = endTime + " 23:59:59";
        }
        ApiResponse<List<TraineeInformation>> result = new ApiResponse<>();
        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.gte(TraineeInformation.InnerColumn.registrationTime,startTime);
        simpleCondition.lte(TraineeInformation.InnerColumn.registrationTime,endTime);
        simpleCondition.setOrderByClause(TraineeInformation.InnerColumn.registrationTime.desc());
        if(info != null && info.getName() != null){
            simpleCondition.like(TraineeInformation.InnerColumn.name,info.getName());
        }
        if(info.getPhone() != null){
            simpleCondition.like(TraineeInformation.InnerColumn.phone,info.getPhone());
        }
        if(info.getIdCardNo() != null){
            simpleCondition.like(TraineeInformation.InnerColumn.idCardNo,info.getIdCardNo());
        }
        if(info.getInfoCheckStatus() !=null){
            simpleCondition.eq(TraineeInformation.InnerColumn.infoCheckStatus,info.getInfoCheckStatus());
        }
        PageInfo<TraineeInformation> page = findPage(pager, simpleCondition);
//        afterPager(page);z
        result.setPage(page);
        return result;
    }

    @Override
    public ApiResponse<String> updateEntity(TraineeInformation entity) {

        RuntimeCheck.ifBlank(entity.getId(), "请选择学员");
        TraineeInformation information = findById(entity.getId());
        if(org.apache.commons.lang3.StringUtils.containsAny(information.getStatus(),"50","60")){
            return ApiResponse.fail("该学员处于结业或退学状态，不可修改");
        }
        validAndUpdate(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> userInfoCheck(TraineeInformation entity) {
        TraineeInformation newEntity = new TraineeInformation();

        if ((!org.apache.commons.lang3.StringUtils.equals(entity.getInfoCheckStatus(), "10")) && (!org.apache.commons.lang3.StringUtils.equals(entity.getInfoCheckStatus(), "20"))) {
            return ApiResponse.fail("操作失败，请填写正确的审核状态");
        }
        TraineeInformation find = findById(entity.getId());
        RuntimeCheck.ifNull(find, "未找到该学员信息，请核实");
        RuntimeCheck.ifFalse(org.apache.commons.lang3.StringUtils.equals(find.getInfoCheckStatus(), "00"), "该学员已经审核过，无需审核");
        newEntity.setId(find.getId());
        newEntity.setInfoCheckStatus("10");
        newEntity.setInfoCheckTime(DateUtils.getNowTime());
        SysYh currentUser = WxBaseController.getBossCurrentUser(true);
        newEntity.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        newEntity.setModifyTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        int i = update(newEntity);
//        baseMapper.updateByPrimaryKey(newEntity);

        if (i > 0) {
            //学员状态表新增
            String status = "00";//10：审核通过 20：审核未通过
            if (org.apache.commons.lang3.StringUtils.equals(newEntity.getInfoCheckStatus(), "20")) {
                status = "10";
            }
            String type = "学员基础资料审核";
            traineeStatusService.saveEntity(entity, type, status, "学员基础资料审核");
        }

        return i > 0 ? ApiResponse.success() : ApiResponse.fail("操作失败，請重新尝试");
    }


    @Override
    public ApiResponse<String> revokeInfo(String traineeId) {
        TraineeInformation entity = baseMapper.selectByPrimaryKey(traineeId);
        RuntimeCheck.ifNull(entity, "未找到该学员信息，请核实");
        RuntimeCheck.ifFalse(org.apache.commons.lang3.StringUtils.equals(entity.getStatus(), "99"), "该学员已报名，无法回退");
        entity.setInfoCheckStatus("00");
        entity.setInfoCheckTime(DateUtils.getNowTime());
        SysYh currentUser = WxBaseController.getBossCurrentUser(true);
        entity.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        entity.setModifyTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        int i = update(entity);

        if (i > 0) {
            //学员状态表新增
            String status = "00";//10：审核通过 20：审核未通过
//            if (StringUtils.equals(entity.getInfoCheckStatus(), "20")) {
//                status = "10";
//            }
            String type = "学员基础资料回退";
            traineeStatusService.saveEntity(entity, type, status, "学员基础资料回退");
        }

        return i > 0 ? ApiResponse.success() : ApiResponse.fail("操作失败，請重新尝试");
    }

}