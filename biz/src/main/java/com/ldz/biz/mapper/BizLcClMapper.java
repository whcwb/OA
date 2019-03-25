package com.ldz.biz.mapper;

import com.ldz.biz.model.BizLcCl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BizLcClMapper extends Mapper<BizLcCl> {
    @Update(" update biz_lc_cl set cl_zt = '00' where  cl_zt = '01' ")
    void updateClZt();
    @Update(" update biz_lc_cl set card_no = null where id = #{id}")
    void updateCardNoToNull(@Param("id") String id);

}