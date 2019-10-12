package com.ldz.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.TraineeInformation;

import tk.mybatis.mapper.common.Mapper;

public interface BizExceptionMapper extends Mapper<BizException> {
	
	@Select({"<script>",
        "SELECT * FROM trainee_information",
        "WHERE ${column} ${value} and STATUS NOT IN ('50','60')",
        "<choose>",
        	"<when test=\"column == 'fir_sub' and tcolumn == 'fir_sub_payment_time'\">",
		        "AND DATE_FORMAT(DATE_ADD(fir_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and fir_sub_payment_time is null",
	        "</when>",
	        "<when test=\"column == 'sec_sub' and tcolumn == 'sec_sub_payment_time'\">",
			    "AND DATE_FORMAT(DATE_ADD(sec_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and sec_sub_payment_time is null",
		    "</when>",
		    "<when test=\"column == 'third_sub' and tcolumn == 'third_sub_payment_time'\">",
		        "AND DATE_FORMAT(DATE_ADD(third_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and third_sub_payment_time is null",
		    "</when>",
		    "<otherwise>",
		    	"AND ${tcolumn} >= #{tvalue}",
		    "</otherwise>",
        "</choose>",
        "</script>"})
    public List<TraineeInformation> countInfoByColumn(@Param("column") String column, @Param("value") String value, @Param("tcolumn") String tcolumn, @Param("tvalue") String tvalue);
}