package com.ldz.wechat.mapper;

import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.TraineeInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TraineeInformationMapper extends Mapper<TraineeInformation> {
    /**
     * 查询学生列表
     * @param id
     * @param status
     * @param carType
     * @param allotSub
     * @return
     */
    @Select({ "<script> " +
            " SELECT temp.*,SUM(d.train_times) AS trainTimes FROM training_record AS d left join"+
            " (SELECT a.id,a.head_img,a.car_type,a.phone,a.open_id,b.*," +
            " DATEDIFF(NOW(),b.allot_time) AS allotDays  FROM trainee_information a " +
            " LEFT JOIN coach_trainee_rercord b ON a.id = b.trainee_id" +
            " LEFT JOIN coach_management c ON b.coach_id = c.id " +
            "  WHERE  b.coach_id=#{id} " +
            " <if test='status != null'>" +
            " AND a.status=#{status} " +
            " </if> " +
            " <if test='carType != null '>" +
            " AND a.car_type =#{carType}" +
            " </if> " +
            " <if test= 'allotSub != null'>" +
            " AND b.allot_sub =#{allotSub}" +
            " </if>"+
            " group by a.id) as temp on temp.id = d.trainee_id "+
            " </script>"})
    List<Map> getCoachTrain(@Param("id") String id,@Param("status") String status,
                            @Param("carType") String carType,@Param("allotSub") String allotSub);

    /**
     * 只查询学习中的 00
     *  学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     * @param traineeId
     * @param allotSub
     * @return
     */
    @Select({ "<script> " +
            "SELECT * FROM coach_management a  " +
            " LEFT JOIN coach_trainee_rercord b ON a.id=b.coach_id  " +
            " WHERE b.trainee_id =#{traineeId} AND b.allot_sub=#{allotSub} " +
            " AND b.status= '00' order by b.cjsj desc limit 1 " +
            " </script>"})
    CoachManagement getMyCoachInfo(@Param("traineeId") String traineeId ,@Param("allotSub")String allotSub);

    /**
     * 教练查询我的学员中分配时间最长的前十人(状态为科目二或者科目三，用来提醒学员练车)
     * @param traineeId
     * @return
     */
    @Select({ "<script> " +
            " SELECT  b.id ,b.head_img,DATEDIFF(NOW(),a.allot_time) AS allotDay,a.trainee_name, b.phone,b.car_type,b.status " +
            " FROM coach_trainee_rercord a " +
            " LEFT JOIN trainee_information b ON " +
            " a.trainee_id = b.id " +
            " LEFT JOIN training_record c ON a.trainee_id  = c.trainee_id WHERE a.coach_id =#{coachId} and (b.status = 20 or b.status=30)" +
            " GROUP BY b.id ORDER BY allot_time DESC LIMIT 0,10 " +
            " </script>"})
    List<Map> getTop10(@Param("coachId") String coachId );

    @Select({"<script>"+
            "select * from trainee_information where open_id =  #{openId}"+
            " </script>"})

    TraineeInformation queryByOpenId(@Param("openId") String openId);
}