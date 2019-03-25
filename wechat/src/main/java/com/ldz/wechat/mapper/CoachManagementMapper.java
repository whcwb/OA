package com.ldz.wechat.mapper;

import com.ldz.wechat.model.CoachManagement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CoachManagementMapper extends Mapper<CoachManagement> {

    @Select({ "<script> " +
            " UPDATE coach_management SET coach_rate=" +
            " (SELECT IFNULL(ROUND(AVG(coach_score),0),5) FROM coach_valuation WHERE coach_id=#{coachId} )" +
            " WHERE ID = #{coachId}  " +
            " </script>"})
    void updateCoach(@Param("coachId")String coachId);

    /**
     * 查询排名列表（同结果名次相同且不占用名次）
     * @param
     * @return
     */
    @Select({ "<script> " +
        "  SELECT obj.id,obj.coach_name,obj.coach_rate,obj.head_img,obj.phone," +
        "  CASE"+
        "  WHEN @rowtotal = obj.coach_rate THEN @rownum " +
        "  WHEN @rowtotal := obj.coach_rate THEN @rownum :=@rownum + 1  " +
        "  WHEN @rowtotal = 0 THEN @rownum :=@rownum + 1"+
        "  END AS rank"+
        "  FROM(SELECT id,coach_name,coach_rate,head_img,phone FROM `coach_management` ORDER BY coach_rate DESC) AS obj,"+
        "  (SELECT @rownum := 0 ,@rowtotal := NULL) r " +
        " </script>"})
    List<Map> getRankList();

    /**
     * 查询我的评分排名（同结果名次相同且不占用名次）
     * @param coachId
     * @return
     */
    @Select({ "<script> " +
        "  SELECT * FROM (SELECT obj.id,obj.coach_name,obj.coach_rate," +
        "  CASE"+
        "  WHEN @rowtotal = obj.coach_rate THEN @rownum " +
        "  WHEN @rowtotal := obj.coach_rate THEN @rownum :=@rownum + 1  " +
        "  WHEN @rowtotal = 0 THEN @rownum :=@rownum + 1"+
        "  END AS rank"+
        "  FROM(SELECT id,coach_name,coach_rate FROM `coach_management` ORDER BY coach_rate DESC) AS obj,"+
        " (SELECT @rownum := 0 ,@rowtotal := NULL) r ) AS b WHERE b.id = #{coachId} " +
        " </script>"})
    Map<String,Object> getMyRank(@Param("coachId") String coachId);

    /**
     * 查询教练列表
     * @param coachSub
     * @param gender
     * @param orderBy
     * @return
     */
    @Select({ "<script> " +
        " SELECT * FROM coach_management "+
        "  WHERE 1=1 " +
        " <if test=\" coachSub neq null and '' neq coachSub \">"+
        " AND coach_sub like '${coachSub}%'"+
        " </if> " +
        " <if test=\" gender neq null and '' neq gender \">"+
        " AND gender =#{gender} " +
        " </if> " +
        " <if test=\" orderBy neq null and '' neq orderBy \">"+
        " order by ${orderBy} DESC" +
        " </if> " +
        " </script>"})
    List<Map> getCoachList(@Param("coachSub") String coachSub,@Param("gender") String gender,
                            @Param("orderBy") String orderBy );

}