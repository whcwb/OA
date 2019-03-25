package com.ldz.biz.mapper;

import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.ValueLabelModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReduceManagementMapper extends Mapper<ReduceManagement> {
    /**
     * 查询所有职务
     * @return
     */
    @Select(" select distinct(lx) from sys_clk_ptyh where lx != 'su' ")
    List<String> findAllZw();

    @Select(" select yhid as value,xm as label,xm as title from sys_clk_ptyh where lx = #{lx}")
    List<ValueLabelModel.ValueLabel> findIdAndXm(@Param("lx") String lx);
//AND YH_XM LIKE '%${name}%'
    @Select({ "<script> " +
            " SELECT * FROM REDUCE_MANAGEMENT C " +
            "    <![CDATA[  WHERE C.REDUCE_START_TIME <=  #{startTime} AND C.REDUCE_END_TIME >=  #{nowTime} ]]>    " +
            "      AND C.ID IN (SELECT reduce_id FROM reduce_institution WHERE JGDM LIKE '${jgdm}%') ORDER BY ID DESC  "+
            " </script>"})
    List<ReduceManagement> getJgReduce(@Param("startTime") String startTime,@Param("nowTime") String nowTime, @Param("jgdm") String jgdm);

    @Select("select zdmc from sys_zdxm where zdlmdm = #{zdlm} and zddm = #{zddm}")
    String getZdxm(@Param("zdlm") String zdlm, @Param("zddm") String zddm);
}