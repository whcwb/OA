package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.EChart;
import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.ZgTjjl;
import com.ldz.biz.service.ReduceManagementService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.ZgTjjlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.bean.TreeNode;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.ExcelReader;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生基本信息
 */
@RestController
@RequestMapping("/api/traineeinformation")
public class TraineeInformationController extends BaseController<TraineeInformation,String> {
    @Autowired
    private TraineeInformationService service;

    @Autowired
    private ReduceManagementService reduceManagementService;

    @Autowired
    private JgService jgService;

    @Autowired
    private ZgTjjlService tjjlService;


    @Override
    protected BaseService<TraineeInformation, String> getBaseService() {
        return service;
    }


    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return service.removeEntity(id);
    }

    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(TraineeInformation entity){
        return service.updateEntity(entity);
    }


    /**
     * 基础资料审核
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/baseAuditingPager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<TraineeInformation>> baseAuditingPager(TraineeInformation entity, Page<TraineeInformation> pager){
        return service.baseAuditingPager(pager);
    }

    /**
     * 待受理列表接口
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/traineeAcceptancePager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<TraineeInformation>> traineeAcceptancePager(TraineeInformation entity, Page<TraineeInformation> pager){
        return service.traineeAcceptancePager(pager);
    }

    /**
     * 受理确认接口
     * @param entity  id 主键
     * @param entity  serialNum 受理流水号
     * @return
     */
    @RequestMapping(value="/traineeAcceptanceAuditing", method={RequestMethod.POST})
    public ApiResponse<String> traineeAcceptanceAuditing(TraineeInformation entity){
        // 测试
        return service.updateTraineeAcceptanceAuditing(entity);

    }

    /**
     * 受理确认导入
     * @return
     */
    @RequestMapping(value="/impAcceptanceExcel", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> impAcceptanceExcel(MultipartHttpServletRequest request, HttpServletResponse response){
        Map<String,Object> ret=new HashMap<>();
        MultipartFile file = request.getFile("file");
        RuntimeCheck.ifNull(file,"请选择需要xls文件上传");
        // 获取上传的文件的名称
        String filename = file.getOriginalFilename();
        if(filename.endsWith(".xls") ){
            ExcelReader er = new ExcelReader();

            try {
                List<Map<Integer,String>> list = er.readExcelContent(file.getInputStream(),0); //读取Excel数据内容
                RuntimeCheck.ifFalse(list!=null&&list.size()>0&&list.get(0).size()>1,"请选择正确的受理确认模板");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(0).get(0),"学员身份证"),"请上传正确的导入信息");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(0).get(1),"学员流水号"),"导入文件中未找到学员流水号字段，请核实");
                List<Map<Integer, String>> collect = list.stream().filter(m -> StringUtils.isNotBlank(m.get(0))).collect(Collectors.toList());
                ret=service.impAcceptanceExcel(collect,filename.substring(0,filename.lastIndexOf(".")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            RuntimeCheck.ifNull(file,"请选择xls文件上传");
        }

        return ApiResponse.success(ret);
    }


    /**
     * 学员报名接口
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(TraineeInformation entity){
        return service.validAndSave(entity);
    }

    /**
     * 用户基础资料审核
     * @param entity  id  PKID
     * @param entity  infoCheckStatus  基本信息审核状态  00: 未审核 10：审核通过 20：审核未通过
     * @return
     */
    @RequestMapping(value="/usersh", method={RequestMethod.POST})
    public ApiResponse<String> userInfoCheck(TraineeInformation entity){
        return service.userInfoCheck(entity);
    }

    /**
     * 学员报名缴费确认接口
     * @param traineeId 学员id
     * @param chargeType 收款方式
     */
    @PostMapping("/confirmSignUp")
    public ApiResponse<String> confirmSignUp(String traineeId,String chargeType,String remark){
        return service.confirmSignUp(traineeId , chargeType,remark);
    }

    /**
     * 学员报名批量缴费确认接口
     */
    @PostMapping("/confirmSignUpList")
    public ApiResponse<List<Map<String,String>>> confirmSignUpList(String ids, String bz){
        return service.confirmSignUpList(ids, bz);
    }

    /**
     * 缴费确认撤回接口
     */
    @PostMapping("/revokeSignUp")
    public ApiResponse<String> revokeSignUp(String traineeId){
        return service.revokeSignUp(traineeId);
    }

    /**
     *考试费用缴费确认接口
     */
    @PostMapping("/confirmTest")
    public ApiResponse<String> confirmTest(String traineeId, String remark, String km){
        return service.confirmTestTo(traineeId,remark,km);
    }

    /**
     *  科目培训状态修改
     * @param id    主鍵ID
     * @param type  培训状态  00 成功 10 失败
     * @param subjects  科目 1、科目一 2、科目二 3、科目三
     * @return
     */
    @RequestMapping(value="/updateTrainType", method={RequestMethod.POST})
    public ApiResponse<String> updateTrainType(@RequestParam(name="id", required = false) String id,@RequestParam(name="type", required = false) String type,@RequestParam(name="subjects", required = false) String subjects){

        return service.updateTrainType(id,type,subjects);
    }
    /**
     * 考试缴费撤回接口
     */
    @PostMapping("/revokeTest")
    public ApiResponse<String> revokeTest(String traineeId,String chargeId){
        return service.revokeTest(traineeId,chargeId);
    }

    /**
     * 获取系统报名点(机构)接口 以目录树形式展现
     * @return
     */
    @RequestMapping("/getCurrentOrgTree")
    public ApiResponse<List<TreeNode>> getCurrentOrgTree(){
        SysYh currentUser = getCurrentUser();
        String jgdms = currentUser.getJgdms();
        List<String> list = new ArrayList<>();
        list.add(currentUser.getJgdm());
        if(StringUtils.isNotBlank(jgdms)){
            list.addAll( Arrays.asList(jgdms.split(",")));
        }
        List<SysJg> orgList =  jgService.findAllSubOrg(list,null);
        List<TreeNode> orgNode = jgService.convertToTreeNodeList(orgList);
        List<TreeNode> orgTree = TreeNode.buildTree(orgNode);
        return ApiResponse.success(orgTree);
    }

    /**
     * 获取机构下所有的优惠列表
     * @param jgdm  机构代码
     * @return
     */
    @PostMapping("/getjgreduce")
    public ApiResponse<List<ReduceManagement>> getJgReduce(@RequestParam(name="jgdm", required = false) String jgdm){
        return reduceManagementService.getJgReduce(jgdm);
    }

    /**
     * 统计今日报名学员人数
     */
    @GetMapping("/countReg")
    public ApiResponse<Integer> countReg(@RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime){
        return service.countReg(startTime, endTime);
    }
    /**
     * 统计科目考试
     */
    @PostMapping("/countTest")
    public ApiResponse<List<EChart>> countTest(@RequestParam(required = false) String startTime,@RequestParam(required = false)String endTime){
        return service.countTest(startTime,endTime);
    }

    /**
     * 学员信息统计
     */
    @PostMapping("/countStu")
    public ApiResponse<Map<String, Integer>> countStu(@RequestParam(required = false)String startTime, @RequestParam(required = false)String endTime){
        return service.countStu(startTime, endTime);
    }

    /**
     * 根据学员姓名 ， 学员证件号码 ， 学员报名点 查询该学员是否已经缴纳体检费
     */
    @PostMapping("/checkInspect")
    public ApiResponse<TraineeInformation> checkInspect(@RequestParam(required = false) String name , @RequestParam(required = false) String idCardNo,@RequestParam(required = false) String jgmc){
        return service.checkInspect(name, idCardNo, jgmc);
    }

    /**
     * 学员招生统计
     */
    @PostMapping("/countNewStu")
    public ApiResponse<Map<String,Integer>> countNewStu(@RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime){
        return service.countNewStu(startTime, endTime);
    }

    /**
     *报名审核回退
     */
    @PostMapping("/revokeInfo")
    public ApiResponse<String> revokeInfo(String traineeId){
        return service.revokeInfo(traineeId);
    }

    /**
     * 退学
     * @param id
     * @param fee
     * @return
     */
    @PostMapping("/dropOut")
    public ApiResponse<String> dropOut(String id, int fee){
        return service.dropOut(id,fee);
    }

    /**
     * 学员退学撤回接口
     */
    @PostMapping("/dropOutRevert")
    public ApiResponse<String> dropOutRevert(String id){
        return service.dropOutRevert(id);
    }



    /**
     * 今日代缴报名费接口
     */
    @PostMapping("/jrdj")
    public ApiResponse<Long> getJrDjBmf(Page<TraineeInformation> page){
        return service.getJrDjBmfZ(page);
    }


    /**
     * 今日已交报名费接口
     */
    @PostMapping("/jryj")
    public ApiResponse<Long> getYjBmf(Page<TraineeInformation> page, @RequestParam(required = false) String pj){
        return service.getYjBmf(page,pj);
    }

    /**
     * 修改报名费用的优惠项及金额，以及实际收费
     * @param traineeId  学员id
     * @param reduceId  优惠项id
     * @param price    优惠项金额
     * @param chargeId  收费记录id
     * @return
     */
    @PostMapping("/editReduceAndRealPay")
    public ApiResponse<String> editReduceAndRealPay(String traineeId, String reduceId,@RequestParam(required = false) int price,@RequestParam(required = false) String chargeId){
        return service.editReduceAndRealPay(traineeId,reduceId,price,chargeId);
    }

    /**
     * 学员车型修改
     */
    @PostMapping("/editCarType")
    public ApiResponse<String> editCarType( String id, String carType, String inOutType, Integer fee, String chargeType, String remark){
        return service.editCarType(id,carType,inOutType,fee,chargeType,remark);
    }

    /**
     * 学员班型修改
     */
    @PostMapping("/editClassType")
    public ApiResponse<String> editClassType(String id, String classType, String inOutType, Integer fee, String chargeType,String remark){
        return service.editClassType(id, classType, inOutType, fee, chargeType, remark);
    }
    /**
     * 获取分期还款学员
     */
    @PostMapping("/stag")
    public ApiResponse<Long> getStag(Page<TraineeInformation> page){
        return service.getStag(page);
    }

    /**
     * 获取某日已还款学员
     */
    @PostMapping("/staged")
    public ApiResponse<Long> getStaged(Page<TraineeInformation> page,String timeGte,String timeLte){
        return service.getStaged(page,timeGte,timeLte);
    }

    /**
     * 查询已经预约还没考试的学员
     */
    @PostMapping("/getAllAppointed")
    public ApiResponse<String> getAllAppointed(Page<TraineeInformation> page){
        return service.getAllAppointed(page);
    }

    /**
     * 修改学员为分期
     */
    @PostMapping("/updateToIns")
    public ApiResponse<String> updateToIns(TraineeInformation entity){
        return service.udpateToIns(entity);
    }

    /**
     * 修改应收金额
     */
    @PostMapping("/editRegistrationFee")
    public ApiResponse<String> editRegistrationFee(String id, Integer registrationFee){
        return  service.editRegistrationFee(id,registrationFee);
    }

    /**
     *  C1 降C2
     */
    @PostMapping("/degradeCarType")
    public ApiResponse<String> degradeCarType(String id){
        return service.degradeCarType(id);
    }

    /**
     * 查询处于约考状态但是还未上传成绩的学员
     */
    @PostMapping("/getAppointed")
    public ApiResponse<String> getAppointed(Page<TraineeInformation> entity){
        return service.getAppointed(entity);
    }
    /**
     * 查询
     */
    @PostMapping("/getAppointing")
    public ApiResponse<String> getAppointing(Page<TraineeInformation> entity){
        return service.getAppointing(entity);
    }

    /**
     * 删除某个学员的优惠
     */
    @PostMapping("/deleteReduce")
    public ApiResponse<String> deleteReduce(String id){
        return service.deleteReduce(id);
    }

    /**
     * 学员信息大接口
     */
    @PostMapping("/getAllInfo")
    public ApiResponse<TraineeInformation> getAllInfo(String id){
        return service.getAllInfo(id);
    }

    /**
     * 推荐人记录
     */
    @PostMapping("/getReferrerRecord")
    public ApiResponse<String> getReferrerRecord(Page<ZgTjjl> page){
        return  tjjlService.getReferrerRecord(page);
    }

    /**
     * 查询已经约考 , 要练车的学员
     */
    @PostMapping("/getAppoint")
    public ApiResponse<List<TraineeInformation>> getAppoint(String carType){
        return service.getAppoint(carType);
    }

    /**
     * 修改学员车型 (不计财务 ， 随意修改)
     */
    @PostMapping("/editCarTypeNoCharge")
    public ApiResponse<String> editCarTypeNoCharge(String id, String carType){
        return service.editCarTypeNoCharge(id, carType);
    }

    /**
     * 导出结果
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportResultNew", method = {RequestMethod.GET})
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.exportResult(request, response);
    }

    /**
     * 获取约考记录
     */
    @PostMapping("/getTestStudents")
    public ApiResponse<String> getTestStudents(@RequestParam(defaultValue = "8") int pageSize,@RequestParam(defaultValue = "1") int pageNum,   String kskm){
        return service.getTestStudents(pageSize, pageNum,kskm);
    }

    /**
     * 获取异常约考记录
     */
    @PostMapping("/getTestStudentsError")
    public ApiResponse<String> getTestStudentsError(@RequestParam(defaultValue = "8") int pageSize,@RequestParam(defaultValue = "1") int pageNum,   String kskm){
        return service.getTestStudentsError(pageSize, pageNum,kskm);
    }

    /**
     * 修改学员的考试状态
     */
    @PostMapping("/updateTestResult")
    public ApiResponse<String> updateTestResult(String id , String kskm, String result, String time){
        return service.updateTestResult(id, kskm, result, time);
    }


    /**
     *  考试结果撤回
     */
    @PostMapping("/revokeAppoint")
    public ApiResponse<String> revokeAppoint(String id, String kskm, String time){
        return service.revokeTestAppoint(id, kskm, time);
    }

    @Override
    @GetMapping("/{pkid}")
    public ApiResponse<TraineeInformation> get(@PathVariable("pkid")String id){
        return service.getById(id);
    }




}