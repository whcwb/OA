package com.ldz.biz.mapper;

import com.ldz.biz.model.ChargeInstitutionManage;
import com.ldz.util.mapperprovider.InsertListMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ChargeInstitutionManageMapper extends Mapper<ChargeInstitutionManage> ,InsertListMapper<ChargeInstitutionManage> {

    @Delete(" delete from Charge_Institution_Manage where charge_id = #{chargeCode}")
    void deleteByChargeCode(@Param("chargeCode") String chargeCode);
}