package com.ldz.biz.car.mapper;

import com.ldz.biz.car.model.BizCarAnnualExam;
import com.ldz.util.mapperprovider.InsertListMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizCarAnnualExamMapper extends Mapper<BizCarAnnualExam>, InsertListMapper<BizCarAnnualExam> {
    @Delete({
            "<script> " +
            " delete from biz_car_annual_exam where zt!='1' " +
            " and  warn_id  in " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> "
            +" </script>"
    })
    void insertDeleteList(@Param("list") List<String> warnIdList);
//            " UPDATE biz_car_annual_exam SET  jsycjsj=null, cj_type = '2' WHERE ZT !='1' AND nslx='001' and TIMESTAMPDIFF(MONTH,jsycjsj,now()) >3 " +
    @Update({ "<script> " +
            " <![CDATA[ " +
            " UPDATE biz_car_annual_exam SET  jsycjsj=null, cj_type = '2' WHERE ZT !='1' AND nslx='001' and datediff(now(),jsycjsj) >=30 " +
            "  ]]> "+
            " </script>"})
    void updateCollectionMessage();

//            " UPDATE biz_car SET  jsycjsj=null, cj_type = '2' WHERE TIMESTAMPDIFF(MONTH,jsycjsj,now()) >3 " +
    @Update({ "<script> " +
            " <![CDATA[ " +
            " UPDATE biz_car SET  jsycjsj=null, cj_type = '2' WHERE datediff(now(),jsycjsj) >=30  " +
            "  ]]> "+
            " </script>"})
    void updateCarCollectionMessage();
}