package com.ldz.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.TraineeInformation;

import tk.mybatis.mapper.common.Mapper;

public interface BizExceptionMapper extends Mapper<BizException> {
	
	/**
	 * 动态组合字段查询统计各个节点未处理的学员数据
	 * @param column
	 * @param value
	 * @param tcolumn
	 * @param tvalue
	 * @return
	 */
	@Select({"<script>",
        "SELECT * FROM trainee_information",
        "WHERE ${column} ${value} and STATUS NOT IN ('50','60')",
        "<choose>",
        	"<when test=\"column == 'fir_sub' and tcolumn == 'fir_sub_payment_time'\">",
		        "AND DATE_FORMAT(DATE_ADD(fir_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and fir_sub_payment_time is null",
	        "</when>",
	        "<when test=\"column == 'fir_sub' and tcolumn == 'fir_sub_test_time'\">",
		        "AND fir_sub_test_time &lt; CURRENT_DATE AND DATE_FORMAT(DATE_ADD(fir_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE",
	        "</when>",
	        "<when test=\"column == 'sec_sub' and tcolumn == 'sec_sub_payment_time'\">",
			    "AND DATE_FORMAT(DATE_ADD(sec_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and sec_sub_payment_time is null",
		    "</when>",
	        "<when test=\"column == 'sec_sub' and tcolumn == 'sec_sub_test_time'\">",
			    "AND sec_sub_test_time &lt; CURRENT_DATE AND DATE_FORMAT(DATE_ADD(sec_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE",
		    "</when>",
		    "<when test=\"column == 'third_sub' and tcolumn == 'third_sub_payment_time'\">",
		        "AND DATE_FORMAT(DATE_ADD(third_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE and third_sub_payment_time is null",
		    "</when>",
		    "<when test=\"column == 'third_sub' and tcolumn == 'third_sub_test_time'\">",
		        "AND third_sub_test_time &lt; CURRENT_DATE AND DATE_FORMAT(DATE_ADD(third_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE",
		    "</when>",
		    "<when test=\"column == 'forth_sub' and tcolumn == 'forth_sub_test_time'\">",
		        "AND forth_sub_test_time &lt; CURRENT_DATE AND DATE_FORMAT(DATE_ADD(forth_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE",
		    "</when>"  ,
					"<when test=\" tcolumn == 'fir_sub_test_time' and column=='fir_sub_payment_time'\"> " ,
					"  AND class_type != '60' AND   DATE_FORMAT(DATE_ADD(fir_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE " ,
					" </when>",
			"<when test=\" tcolumn == 'sec_sub_test_time' and column=='sec_sub_payment_time'\"> " ,
			" AND CLASS_TYPE != '60'  AND  DATE_FORMAT(DATE_ADD(sec_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE " ,
			" </when>",
			"<when test=\" tcolumn == 'third_sub_test_time' and column=='third_sub_payment_time'\"> " ,
			" AND CLASS_TYPE != '60'  AND   DATE_FORMAT(DATE_ADD(third_sub_test_time, INTERVAL ${tvalue} DAY), '%Y-%m-%d') &lt;= CURRENT_DATE " ,
			" </when>",
		    "<otherwise>",
		    	"AND ${tcolumn} &lt;= #{tvalue}",
		    "</otherwise>",
        "</choose>",
        "</script>"})
    public List<TraineeInformation> getTraineeInfoByColumn(@Param("column") String column, @Param("value") String value, @Param("tcolumn") String tcolumn, @Param("tvalue") String tvalue);
	
	@Select("SELECT SUM( CASE WHEN info_check_status = '00' THEN 1 ELSE 0 END ) 'BMDSH', "
			+ "(SELECT COUNT(1) FROM biz_exception WHERE CODE = '001' AND zt = '00') 'BMSHYC', "
			+ "SUM( CASE WHEN charge_status = '00' AND info_check_status = '10' THEN 1 ELSE 0 END ) 'SFDQR', "
			+ "(select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE (CODE = '002' ) AND zt = '00' GROUP BY sfzmhm) t) 'SFQRYC', "
			+ "SUM( CASE WHEN accept_status <> '20' and info_check_status='10' THEN 1 ELSE 0 END ) 'SLDQR', "
			+ "( select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '003'  AND zt = '00' GROUP BY sfzmhm) t ) 'SLQRYC', "
			+ " SUM( CASE WHEN STATUS='10' THEN 1 ELSE 0 END )   'KM1YY', "
			+ "( (select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE (CODE = '991' ) AND KSKM='1' AND zt = '00' GROUP BY sfzmhm) t) ) 'KM1YYYC', "
			+ "SUM( CASE WHEN  (fir_sub_test_time != ''  AND  (fir_sub_payment_time is null or fir_sub_payment_time = '' ) AND class_type !='60') THEN 1 ELSE 0 END ) 'KM1JF', "
			+ "(SELECT COUNT(1) FROM biz_exception WHERE CODE = '101' AND kskm = '1' AND zt = '00') 'KM1JFYC', "
			+ " SUM( CASE WHEN STATUS='20' THEN 1 ELSE 0 END)  'KM2YY', "
			+ "((select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE (CODE = '991' ) AND KSKM='2' AND zt = '00' GROUP BY sfzmhm) t) ) 'KM2YYYC', "
			+ "SUM( CASE WHEN (sec_sub_test_time != ''  AND  (sec_sub_payment_time is null or sec_sub_payment_time = '' AND class_type !='60' )) THEN 1 ELSE 0 END ) 'KM2JF', "
			+ "(SELECT COUNT(1) FROM biz_exception WHERE CODE = '201' AND kskm = '2' AND zt = '00') 'KM2JFYC', "
			+ " SUM( CASE WHEN STATUS='30' THEN 1 ELSE 0 END ) 'KM3YY', "
			+ "((SELECT COUNT(1) FROM ( SELECT COUNT(1) FROM BIZ_EXCEPTION WHERE (CODE = '991' ) AND KSKM='3' AND zt = '00' GROUP BY sfzmhm) t) ) 'KM3YYYC', "
			+ "SUM( CASE WHEN (third_sub_test_time != ''  AND  (third_sub_payment_time is null or third_sub_payment_time = '' AND class_type !='60' )) THEN 1 ELSE 0 END ) 'KM3JF', "
			+ "(SELECT COUNT(1) FROM biz_exception WHERE CODE = '301' AND kskm = '3' AND zt = '00') 'KM3JFYC', "
			+ " SUM(CASE WHEN (SELECT COUNT(1) FROM trainee_test_info where subject ='科目一' and  (test_result is null or test_result = '') AND trainee_id = m.id AND m.`fir_sub` NOT IN ('30','40') and m.fir_sub_test_time = test_time)=1 THEN 1 ELSE 0 END)  'KM1DQR', "
			+ "(select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '102' AND kskm = '1' AND zt = '00' GROUP BY sfzmhm) t) 'KM1QRYC', "
			+ "SUM(CASE WHEN ( SELECT COUNT(1) FROM trainee_test_info where subject ='科目二' and  (test_result is null or test_result = '') AND trainee_id = m.id AND m.`sec_sub` NOT IN ('30','40') and m.sec_sub_test_time = test_time)=1 THEN 1 ELSE 0 END) 'KM2DQR', "
			+ "(select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '202' AND kskm = '2' AND zt = '00' GROUP BY sfzmhm) t) 'KM2QRYC', "
			+ "SUM(CASE WHEN (  SELECT COUNT(1) FROM trainee_test_info where subject ='科目三' and  (test_result is null or test_result = '') AND trainee_id = m.id AND m.`third_sub` NOT IN ('30','40') and m.third_sub_test_time = test_time)=1 THEN 1 ELSE 0 END) 'KM3DQR', "
			+ "(select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '302' AND kskm = '3' AND zt = '00' GROUP BY sfzmhm) t) 'KM3QRYC', "
			+ " SUM( CASE WHEN STATUS='40' THEN 1 ELSE 0 END ) 'KM4YY', "
			+ "((select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE (CODE = '991' ) AND KSKM='4' AND zt = '00' GROUP BY sfzmhm) t)) 'KM4YYYC', "
			+ "SUM(CASE WHEN ( SELECT COUNT(1) FROM trainee_test_info where subject ='科目四' and  (test_result is null or test_result = '') AND trainee_id = m.id AND m.`forth_sub` NOT IN ('10','20') and m.forth_sub_test_time = test_time)=1 THEN 1 ELSE 0 END ) 'KM4DQR'  , "
			+ "(select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '402' AND kskm = '4' AND zt = '00' GROUP BY sfzmhm) t) 'KM4QRYC' ,"
			+"( SELECT COUNT(1) FROM TRAINEE_INFORMATION WHERE ARREARAGE ='10' and status  not in ( '60','99')  ) 'FQDQR' ,"
			+" (select count(1) from ( SELECT COUNT(1) FROM biz_exception WHERE CODE = '903'  AND zt = '00' GROUP BY sfzmhm) t) 'FQQRYC'  "
			+ "FROM trainee_information m WHERE STATUS NOT IN ('50', '60')")
    public Map<String, Integer> dashboard();
}