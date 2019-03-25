package com.ldz.biz.mapper;

import com.ldz.biz.model.CoachManagement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface CoachManagementMapper extends Mapper<CoachManagement> {
    /**
     * 更新教练当前正在学习的学员数量
     * @param
     */
    @Update({ "<script> " +
            " UPDATE COACH_MANAGEMENT AS C "+
            " SET C.trainee_num = (SELECT COUNT(1) FROM coach_trainee_rercord D WHERE D.coach_id  = C.ID AND D.status='00' )   "+
            " WHERE C.ID  IN (SELECT DISTINCT COACH_ID FROM COACH_TRAINEE_RERCORD WHERE LEFT(MODIFY_TIME,10)=#{date})  " +
            " </script>"})
    void updateCoachTraineeCount(@Param("date") String date);
}