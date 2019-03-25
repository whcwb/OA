package com.ldz.biz.mapper;

import com.ldz.biz.model.YyCwjl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface YyCwjlMapper extends Mapper<YyCwjl> {

     @Update(" update yy_cwjl set zt = '10' where trainee_id = #{trainId}")
     void updateZt(@Param("trainId") String trainId);

}
