package com.ldz.biz.mapper;

import com.ldz.biz.model.ChargeManagement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChargeManagementMapper extends Mapper<ChargeManagement> {

    @Update("<script> update charge_management set pjbh = #{pjbh} where id in <foreach collection='ids'  item='item' open='(' separator=',' close=')'> #{item} </foreach>  </script>")
    void updatePjbh(@Param("ids") List<String> ids, @Param("pjbh") String pjbh);

    @Select("<script> select a.*,b.car_type carType from charge_management a" +
            " right join  trainee_information b on a.trainee_id = b.id and b.status != '60' and b.jgdm  in" +
            " <foreach collection='jgdms'  item='item' open='(' separator=',' close=')'> #{item} </foreach>" +
            " <if test=\" carType neq null and '' neq carType \"> " +
            "and b.car_type like '${carType}%' " +
            "</if> " +
            " where a.charge_time &gt;= #{startTime} and a.charge_time &lt;= #{endTime} and charge_code ='0000' order by a.charge_time desc </script>")
    List<ChargeManagement> getBranchList(@Param("jgdms") List<String> jgdms, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("carType") String carType);

    @Select("<script> select a.*,b.jgdm jgdm from charge_management a" +
            " right join  trainee_information b on a.trainee_id = b.id and b.jgdm  in" +
            " <foreach collection='jgdms'  item='item' open='(' separator=',' close=')'> #{item} </foreach>" +
            " where a.charge_time &gt;= #{startTime} and a.charge_time &lt;= #{endTime} and charge_code ='0000' order by a.charge_time desc </script>")
    List<ChargeManagement> getAllIn(@Param("jgdms") List<String> jgdms, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /*@Delete(" delete from trainee_information where trainee_id = #{trainId} and charge_code = '0000'")
    void delByCodeAndTid(@Param("trainId")String traineeId);*/
    /**
     * 统计今日分期还款总额
     */
    @Select(" select sum(charge_fee) from charge_management  where charge_code = '0002' and cjsj like '${time}%'")
    public Long countStaged(@Param("time") String time);

    @Select("<script> select a.*,b.jgdm jgdm from charge_management a" +
            " right join  trainee_information b on a.trainee_id = b.id right join sys_ptjg c on b.jgdm = c.jgdm " +
            " where a.charge_time &gt;= #{startTime} and a.charge_time &lt;= #{endTime} and  (charge_code ='0000' or charge_code = '0003') order by c.px asc </script>")
    List<ChargeManagement> getAllIn2( @Param("startTime") String startTime, @Param("endTime") String endTime);

}