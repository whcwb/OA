package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.service.ChargeManagementService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收费记录管理
 */
@RestController
@RequestMapping("/api/chargemanagement")
public class ChargeManagementController extends BaseController<ChargeManagement,String> {

    @Autowired
    private ChargeManagementService service;

    @Override
    protected BaseService<ChargeManagement, String> getBaseService() {
        return service;
    }

    /**
     * 体检收费记录新增
     */
    @PostMapping("/saveInspect")
    public ApiResponse<String> saveInspect(ChargeManagement entity){
        return service.saveInspect(entity);
    }

    /**
     * 体检费驳回
     * @param id
     * @return
     */
    @PostMapping("/rejectInspect")
    public ApiResponse<String> rejectInspect(String id){
        return service.rejectInspect(id);
    }

    /**
     * 体检退费确认
     * @param id
     * @return
     */
    @PostMapping("/returnInspect")
    public ApiResponse<String> returnInspect(String id){
        return service.returnInspect(id);
    }

    /**
     * 体检异常操作
     * @param id
     * @param reason
     * @return
     */
    @PostMapping("/warnInspect")
    public ApiResponse<String> warnInspect(String id, String reason){
        return service.warnInspect(id, reason);
    }

    @PostMapping("/remove/{pkid}")
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return service.removeEntity(id);
    }

    /**
     * 其他收支录入
     */
    @PostMapping("/otherCharge")
    public ApiResponse<String> otherCharge(ChargeManagement entity){
        return service.saveOtherCharge(entity);
    }

    /**
     * 收费审核确认
     */
    @PostMapping("/confirmCharge")
    public ApiResponse<String> confirmCharge( String chargeManagements){
        List<ChargeManagement> objects = JsonUtil.toList(chargeManagements, ChargeManagement.class);
        if(CollectionUtils.isEmpty(objects)){
            return ApiResponse.fail("请选择记录");
        }
        Map<String, String> collect = objects.stream().collect(Collectors.toMap(ChargeManagement::getId, ChargeManagement::getRemark));
        return service.confirmCharge(collect);
    }

    @PostMapping("/revokeCharge")
    public ApiResponse<String> revokeCharge(String chargeIds){

        List<String> objects = JsonUtil.toList(chargeIds, String.class);
        if(CollectionUtils.isEmpty(objects)){
            return ApiResponse.fail("请选择需要确认的记录");
        }
        //List<ChargeManagement> chargeManagements = JsonUtil.toList(chargeIds,ChargeManagement.class);
        /*Map<String, String> collect = chargeManagements.stream().collect(Collectors.toMap(ChargeManagement::getId, ChargeManagement::getRemark));*/
        return service.revokeCharge(objects);
    }

    /**
     * 统计今日缴费类型人数
     */
    @GetMapping("/countType")
    public ApiResponse<Map<String,Long>> countType(@RequestParam(required = false)String chargeCode , @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime){
        return service.countType(chargeCode, startTime, endTime);
    }

    /**
     * 每月收支统计
     */
    @PostMapping("/countIn")
    public ApiResponse<Map<String, Long>> countInOut(){
        return service.countInOut();
    }

    /**
     * 每月支统计
     */
    @PostMapping("/countOut")
    public ApiResponse<Map<String,Long>> countInType(){
        return service.countInType();
    }

    /**
     * 生成收据编号
     */
    @PostMapping("/receiptNo")
    public ApiResponse<String> getReceiptNo(@RequestParam(value = "ids",required = false)String ids,@RequestParam("num") String num){
        String [] split = {} ;
        if(StringUtils.isNotBlank(ids)){
            split = ids.split(",");
        }
        return service.getReceiptNo(split,num);
    }

   /* @PostMapping("/pjnum")
    public ApiResponse<String> getPjnum(String[] ids){
        return service.getPjnum(ids);
    }*/


//    chargeManagement
    /**
     * 保存票据编号
     */
    @PostMapping("/savePjbh")
    public ApiResponse<String> getPjbh(String  ids, String num, String pjbh){
        RuntimeCheck.ifBlank(ids , "请选择需要打印的记录");
        String[] split = ids.split(",");
        return service.getPjbh(Arrays.asList(split),num,pjbh);
    }

    /**
     * 费用查询及统计
     */
    @PostMapping("/findTodayCharge")
    public ApiResponse<List<Map<String, Long>>> findTodayCharge(Page<ChargeManagement> page){
        return service.findTodayCharge(page);
    }

    /**
     * 打印票据作废
     */
    @GetMapping("/removePjbh")
    public ApiResponse<String> removePjbh(String pjbh){
        return service.removePjbh(pjbh);
    }

    /**
     * 票据记录查询
     */
    @GetMapping("/getPrintLog")
    public ApiResponse<String> getPrintLog(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize){
        return service.getPrintLog(pageNum, pageSize);
    }

}