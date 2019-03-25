package com.ldz.wechat.mapper;

import com.ldz.wechat.model.TrainingRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TrainingRecordMapper extends Mapper<TrainingRecord> {

    @Select({ "<script> " +
            "SELECT COUNT(*) FROM training_record where trainee_id=#{traineeId} " +
            " </script>"})
    Integer countTrainRecord(@Param("traineeId") String traineeId);

    /**
     * 查询培训的天数
     * @return
     */
    @Select({ "<script> " +
            "SELECT DISTINCT DATE_FORMAT(train_start_time,'%Y-%m-%d') FROM training_record  where trainee_id = #{traineeId} " +
            " <if test='sub != null'>" +
            " and sub=#{sub} " +
            " </if> " +
            " </script>"})
    List<String>  countTrainRecordDays(@Param("traineeId") String traineeId,@Param("sub") String sub);


    /**
     * 查询每天培训的次数
     * @return
     */
    @Select({ "<script> " +
            "SELECT * FROM training_record " +
            " WHERE DATE_FORMAT(train_start_time,'%Y-%m-%d')= #{trainDay} ORDER BY train_start_time DESC " +
            " </script>"})
    List<TrainingRecord>  countTrainRecordByDay(@Param("trainDay") String trainDay);

    @Select({ "<script> " +
            "SELECT * FROM training_record " +
            " WHERE trainee_id = #{traineeId} and train_end_time is null and DATE_FORMAT(train_start_time,'%Y-%m-%d')=#{trainEndTime}" +
            " </script>"})
    TrainingRecord  queryByTraineeId(@Param("traineeId") String traineeId,@Param("trainEndTime") String trainEndTime);
}