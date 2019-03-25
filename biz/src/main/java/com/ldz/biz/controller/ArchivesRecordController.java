package com.ldz.biz.controller;





import com.ldz.biz.model.ArchivesRecord;
import com.ldz.biz.model.RecordManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.ArchivesRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 档案记录1
 */
@RestController
@RequestMapping("/api/archivesrecord")
public class ArchivesRecordController extends BaseController<ArchivesRecord,String> {
    @Autowired
    private ArchivesRecordService service;

    @Override
    protected BaseService<ArchivesRecord, String> getBaseService() {
        return service;
    }

    /**
     * 档案入库操作
     * @param entity serialNum 学员流水号
     * @param entity archivesCode 档案柜编码
     * @param entity remark      备注
     * @return
     */
    @RequestMapping(value="/addition", method={RequestMethod.POST})
    public ApiResponse<TraineeInformation> addWarehousing(ArchivesRecord entity){
        return service.addWarehousing(entity);
    }

    /**
     * 获取档案明细
     * @param entity serialNum 学员流水号
     * @param entity archivesCode 档案柜编码
     * @param entity stype 档案状态 0在库 1 出库 null是查询全部
     * @return
     */
    @RequestMapping(value="/getdymx", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> getRecordDetail(ArchivesRecord entity){
        return service.getRecordDetail(entity);
    }
    /**
     * 获取学员档案柜信息
     * @param entity serialNum 学员流水号
     * @return
     */
    @RequestMapping(value="/getxyda", method={RequestMethod.POST})
    public ApiResponse<RecordManagement> getTraineeRecordDetail(ArchivesRecord entity){
        return service.getTraineeRecordDetail(entity);
    }


    /**
     * 档案库 出库
     * @param entity serialNum 学员流水号
     * @param entity remark  备注
     * @return 档案状态  档案柜状态
     */
    @RequestMapping(value="/ck", method={RequestMethod.POST})
    public ApiResponse<String> updateShiftout(ArchivesRecord entity){
        return service.updateShiftout(entity);
    }
}