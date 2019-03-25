package com.ldz.biz.service;

import com.ldz.biz.model.ArchivesRecord;
import com.ldz.biz.model.RecordManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.Map;

public interface ArchivesRecordService extends BaseService<ArchivesRecord, String> {


    ApiResponse<TraineeInformation> addWarehousing(ArchivesRecord entity);

    /**
     * 获取档案明细
     * @param entity serialNum 学员流水号
     * @param entity archivesCode 档案柜编码
     * @param entity stype 档案状态 0在库 1 出库 null是查询全部
     * @return 档案状态  档案柜状态
     */
    ApiResponse<Map<String,Object>> getRecordDetail(ArchivesRecord entity);
    /**
     * 档案库 出库
     * @param entity serialNum 学员流水号
     * @param entity remark  备注
     * @return 档案状态  档案柜状态
     */
    ApiResponse<String> updateShiftout(ArchivesRecord entity);
    /**
     * 获取学员档案柜信息
     * @param entity serialNum 学员流水号
     * @return
     */
    ApiResponse<RecordManagement> getTraineeRecordDetail(ArchivesRecord entity);
}