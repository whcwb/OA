package com.ldz.biz.car.mapper;

import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.util.mapperprovider.InsertListMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizCarWarnMapper extends Mapper<BizCarWarn>,InsertListMapper<BizCarWarn> {

    @Update("<script> " +
            "<![CDATA[  UPDATE biz_car c SET  " +
            " car_type=#{carType} , " +
            " warn_ygns_date = (select w.expiry_date from biz_car_warn w where w.cl_id=c.id and warn_type='2' and warn_dispose='0'  order by  warn_date asc limit 1 ),  " +
            " warn_bx_date = (select w.expiry_date from biz_car_warn w where w.cl_id=c.id and warn_type='4' and warn_dispose='0'  order by  warn_date asc limit 1 ), " +
            "   warn_nssj_date = (select w.expiry_date from biz_car_warn w where w.cl_id=c.id and warn_type='1' and warn_dispose='0'  order by  warn_date asc limit 1 ), " +
            "   warn_gx_date = (select w.expiry_date from biz_car_warn w where w.cl_id=c.id and warn_type='3' and warn_dispose='0'  order by  warn_date asc limit 1 )    " +
            " where c.id = #{clId} ]]>" +
            "</script>")
    void updCarWarnDate( @Param("clId") String clId,@Param("carType") String carType);

//    SELECT p.* FROM (SELECT * FROM biz_car_warn WHERE warn_dispose='0' and warn_date <now() ORDER BY warn_date asc  LIMIT 10000000 )p
//    GROUP BY  p.warn_type ORDER BY warn_date asc;
    @Select({ "<script> " +
            " <![CDATA[  SELECT p.* FROM (SELECT * FROM biz_car_warn WHERE cl_id= #{clId} and  warn_dispose='0' and warn_date <now() ORDER BY warn_date asc  LIMIT 10000000 )p  "+
            " GROUP BY  p.warn_type ORDER BY warn_date asc  ]]>" +
            " </script>"})

    List<BizCarWarn> getNewestWarn(@Param("clId") String clId);
}