package com.ldz.biz.mapper;

import com.ldz.biz.model.ChargeItemManagement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChargeItemManagementMapper extends Mapper<ChargeItemManagement> {
    /**
     * 获取用户费用信息
     * @param chargeCode        收费代码
     * @param jgdm              机构代码
     * @return
     */
    @Select({ "<script> " +
            " SELECT * FROM CHARGE_ITEM_MANAGEMENT C " +
            " WHERE C.STATUS='00' AND C.CHARGE_CODE = #{chargeCode} AND C.car_type like  '%${carType}%'   " +
            "      AND C.ID IN (SELECT CHARGE_ID FROM CHARGE_INSTITUTION_MANAGE WHERE JGDM  LIKE '${jgdm}%') ORDER BY ID DESC  "+
            " </script>"})
    List<ChargeItemManagement> getUserCharge(@Param("chargeCode") String chargeCode, @Param("jgdm") String jgdm,@Param("carType")String carType);

    @Select("select charge_name from charge_item_management a left join charge_institution_manage b on a.id = b.charge_id where a.charge_code like '${chargeCode}%' and b.jgdm = #{jgdm} and a.car_type like '%${carType}%'")
     String findChargeName(@Param("jgdm") String jgdm ,@Param("chargeCode") String chargeCode,@Param("carType")String carType);

}