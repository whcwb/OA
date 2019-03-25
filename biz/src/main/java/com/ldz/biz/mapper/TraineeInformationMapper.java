package com.ldz.biz.mapper;

import com.ldz.biz.model.TraineeInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TraineeInformationMapper extends Mapper<TraineeInformation> {
    /**
     * 更新学员的教练信息
     * @param traineeList
     * @param km2Code
     * @param km2Name
     * @param km3Code
     * @param km3Name
     */
    @Update({ "<script> " +
            " UPDATE TRAINEE_INFORMATION T SET "+
            "<if test='km2Code !=null'>" +
            " T.SECOND_SUBJECT_COACH= #{km2Name} "+
            " ,T.SECOND_SUBJECT_COACH_CODE= #{km2Code} "+
            "</if>" +
            "<if test='km3Code !=null'>" +
            " T.THIRD_SUBJECT_COACH=#{km3Name}  "+
            " ,T.THIRD_SUBJECT_COACH_CODE=#{km3Code}  "+
            "</if>" +
            " WHERE T.ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " </script>"})
    void usetCoach(@Param("list") List<String> traineeList, @Param("km2Code")  String km2Code, @Param("km2Name")  String km2Name, @Param("km3Code")  String km3Code, @Param("km3Name")  String km3Name);
    @Update({ "<script> " +
            " UPDATE TRAINEE_INFORMATION T SET RECORD_LIB=null WHERE ID = #{traineeId}   "+
            " </script>"})
    void updateRecordLib(@Param("traineeId") String traineeId);

    @Select(" select count(real_pay) from trainee_information where (confirm_time is null or confirm_time = '') and info_check_status = '10'")
    long countRealPay();
    @Select(" select count(real_pay) from trainee_information where confirm_time like '%${date}%')")
    long countRealPaied(@Param("date") String date);

    /**
     * 统计所有分期款
     */
    @Select(" select sum(owe_amount) from trainee_information where arrearage ='10' and name like '%${nameLike}%' and id_card_no like '%${idCardNoLike}%'")
    Long countStag(@Param("nameLike") String nameLike,@Param("idCardNoLike") String idCardNoLike);


}