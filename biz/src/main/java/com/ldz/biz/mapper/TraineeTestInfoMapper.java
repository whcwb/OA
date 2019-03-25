package com.ldz.biz.mapper;

import com.ldz.biz.model.TraineeTestInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TraineeTestInfoMapper extends Mapper<TraineeTestInfo> {

    @Select("<script> select testInfo.*,info.jgdm from trainee_test_info testInfo left join trainee_information info on testInfo.trainee_Id = info.id " +
            "<if test='jgdm != null'> and info.jgdm = #{jgdm} </if>   " +
            "where testInfo.test_time &gt;= #{startTime} and testInfo.test_time &lt;= #{endTime}  " +
            "<if test='km != null'> testInfo.subject = #{km} </if>  " +
            "</script>")
    List<TraineeTestInfo> getInfo(@Param("jgdm") String jgdm,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("km") String km);

}