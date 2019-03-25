package com.ldz.biz.mapper;

import com.ldz.biz.model.CoachTraineeRercord;
import com.ldz.util.mapperprovider.InsertListMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CoachTraineeRercordMapper extends Mapper<CoachTraineeRercord>,InsertListMapper<CoachTraineeRercord> {
    /**
     * 将所有退学的学员，做更新
     * @param trainees
     */
    @Update({ "<script> " +
            " UPDATE COACH_TRAINEE_RERCORD AS R "+
            " SET  R.STATUS='20' ,R.MODIFIER= #{modifier} ,R.MODIFY_TIME= #{modifyTime}  "+
            " WHERE R.ALLOT_SUB  = #{km}  " +
            " and R.TRAINEE_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " </script>"})
    void changeCoach(@Param("list") List<String> changeTraineeLog, @Param("km") String km
            , @Param("modifier") String modifier, @Param("modifyTime") String modifyTime);
}