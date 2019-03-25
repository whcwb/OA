package com.ldz.biz.mapper;

import com.ldz.biz.model.ZgTjjl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ZgTjjlMapper extends Mapper<ZgTjjl> {


    @Select(" select count(1) from zg_tjjl a left join trainee_information b on a.trainee_id = b.id where a.cjsj >= #{startTime} and a.cjsj <= #{endTime} and b.status !='60' and zg_id = #{zgId}")
    int countByTime(@Param("startTime") String startTime,@Param("endTime") String endTime, @Param("zgId") String zgId);

}
