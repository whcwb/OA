package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.EChart;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface TraineeInformationService extends BaseService<TraineeInformation, String> {

    ApiResponse<String> userInfoCheck(TraineeInformation entity);


    ApiResponse<String> confirmSignUp(String entity, String chargeType,String remark);

    TraineeInformation queryUserStudyType( String idCardNo, String carType);


    ApiResponse<List<TraineeInformation>> baseAuditingPager(Page<TraineeInformation> pager);

    ApiResponse<String> confirmTest(String traineeId,String remark);

    ApiResponse<String> confirmTestTo(String traineeId,String remark,String km);

    ApiResponse<String> revokeSignUp(String traineeId);

    ApiResponse<String> revokeTest(String traineeId,String chargeId);

    ApiResponse<List<TraineeInformation>> traineeAcceptancePager(Page<TraineeInformation> pager);

    ApiResponse<List<TraineeInformation>> traineeAcceptanceByIdCard(Page<TraineeInformation> pager);

    /**
     * 受理确认接口
     * @param entity  id 主键
     * @param entity  serialNum 受理流水号
     * @return
     */
    ApiResponse<String> updateTraineeAcceptanceAuditing(TraineeInformation entity);
    /**
     *  科目培训状态修改
     * @param id    主鍵ID
     * @param type  培训状态  00 成功 10 失败
     * @param subjects  科目 1、科目一 2、科目二 3、科目三 4、科目四
     * @return
     */
    ApiResponse<String> updateTrainType(String id, String type, String subjects);

    ApiResponse<Integer> countReg(String startTime, String endTime);

    ApiResponse<Integer> countPay(String sub, String testTime);

    ApiResponse<Map<String, Integer>> countStu(String startTime, String endTime);

    ApiResponse<List<EChart>> countTest(String startTime, String endTime);

    ApiResponse<TraineeInformation> checkInspect(String name, String idCardNo, String checkTerm);

    ApiResponse<Map<String, Integer>> countNewStu(String startTime, String endTime);

    Map<String,Object> impAcceptanceExcel(List<Map<Integer, String>> list, String substring);

    /**
     * 报名信息回退
     * @param traineeId
     * @return
     */
    ApiResponse<String> revokeInfo(String traineeId);

    ApiResponse<String> updateEntity(TraineeInformation entity);

    ApiResponse<String> dropOut(String id, int fee);

    ApiResponse<Long> getJrDjBmfZ(Page<TraineeInformation> page);

    ApiResponse<Long> getYjBmf(Page<TraineeInformation> page,String pj);

    ApiResponse<String> editReduceAndRealPay(String traineeId, String reduceId, int price, String chargeId);



    ApiResponse<String> editCarType(String id, String carType, String inOutType, Integer fee, String chargeType, String remark);

    /**
     * 获取分期还款学员
     * @param page
     * @return
     */
    ApiResponse<Long> getStag(Page<TraineeInformation> page);

    ApiResponse<Long> getStaged(Page<TraineeInformation> page,String timeGte,String timeLte);

    ApiResponse<String> getAllAppointed(Page<TraineeInformation> page);

    /**
     * 修改 学员的分期状态
     * @param entity
     * @return
     */
    ApiResponse<String> udpateToIns(TraineeInformation entity);


    ApiResponse<String> removeEntity(String id);

    ApiResponse<String> editClassType(String id, String classType, String inOutType, Integer fee, String chargeType, String remark);

    ApiResponse<String> editRegistrationFee(String id, Integer registrationFee);

    ApiResponse<String> degradeCarType(String id);

    ApiResponse<String> getAppointed(Page<TraineeInformation> entity);

    ApiResponse<String> getAppointing(Page<TraineeInformation> entity);

    ApiResponse<String> deleteReduce(String id);

    ApiResponse<TraineeInformation> getAllInfo(String id);

    ApiResponse<String> dropOutRevert(String id);

    ApiResponse<List<TraineeInformation>> getAppoint(String carType);

    ApiResponse<String> editCarTypeNoCharge(String id, String carType);

    ApiResponse<List<Map<String, String>>> confirmSignUpList(String ids , String bz);

    /**
     * 根据身份证号 , 查询正在业务中的学员
     */
    TraineeInformation findByIdCardNo(String idCardNo);

    void exportResult(HttpServletRequest request, HttpServletResponse response) throws IOException;

    ApiResponse<String> getTestStudents(int pageSize, int pageNum, String kskm);

    ApiResponse<String> updateTestResult(String id, String kskm, String result, String time);

    ApiResponse<String> revokeTestAppoint(String id, String kskm, String time);

    ApiResponse<TraineeInformation> getById(String id);

    ApiResponse<String> getTestStudentsError(int pageSize, int pageNum, String kskm);

    ApiResponse<List<TraineeInformation>> getByIdCard(String idcard);

    ApiResponse<String> saveChargeK3(String id, String amount,String remark);

    ApiResponse<String> updateTraineeInfo(TraineeInformation information);

    void exportStag(Page<TraineeInformation> page, HttpServletRequest request, HttpServletResponse response) throws IOException;
}