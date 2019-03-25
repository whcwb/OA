package com.ldz.biz.mapper;

import com.ldz.biz.model.Zgjbxx;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ZgjbxxMapper extends Mapper<Zgjbxx>  {
    @Insert({ "<script> " +
            " <![CDATA[ " +
            "  INSERT INTO zg_aqy_qd_log(ID, XM, jgdm, jgmc, aqy_qdzt, aqy_qdsj, CJSJ, BZ, CJR,KM)  " +
            " SELECT REPLACE(UUID(),'-','') AS ID ,xm,jgdm,jgmc,'00',null,DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') AS CJSJ,'定时任务设置为未登记' as BZ,'' AS CJR, KM  " +
            " from zgjbxx where aqy_qdzt='10'     "+
            "  ]]> "+
            " </script>"})
    void bulkInsertLog();
    @Update({"<script> " +
            " UPDATE ZGJBXX SET AQY_QDZT = '00', AQY_QDSJ = null , KM = null WHERE AQY_QDZT='10'   "+
            " </script>"})
    void clearSecurityOfficerType();
}