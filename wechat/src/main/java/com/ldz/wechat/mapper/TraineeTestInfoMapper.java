package com.ldz.wechat.mapper;

import com.ldz.wechat.model.TraineeTestInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface TraineeTestInfoMapper extends Mapper<TraineeTestInfo> {
    @Select({ "<script> " +
            "SELECT COUNT(*) FROM trainee_test_info where trainee_id=#{traineeId} " +
            " </script>"})
    Integer countTraineeTestInfo(@Param("traineeId") String traineeId);
}