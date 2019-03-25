package com.ldz.util.commonUtil;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils {
	/**
	 * 返回格式：2007-08-14
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	/**
	 *
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}

	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date getDate(String date,String format) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(date);
	}

	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static Date getDateToday(String format) throws ParseException{
		String str = getDateStr(Calendar.getInstance().getTime(),format);
		return getDate(str,format);
	}

	public static long getTimes(){
		return new Date().getTime();
	}


	/**
	 * 根据所有传入的参数进行时间计算
	 * @param time 时间
	 * @param operator 运算符
	 * @param second 计算参数    秒
	 */
	public static String calculateTime(LocalDateTime time,String operator,int second){

		if(StringUtils.equals(operator,"+")){
			time = time.plusSeconds(second);
		}else if(StringUtils.equals(operator, "-")){
			time = time.minusSeconds(second);
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return time.format(df);

	}






//	/**
//	 * 考试年份编码
//	 * @return
//	 */
//	public static String getYearCode(){
//		Calendar cal = Calendar.getInstance();
//    	int year = cal.get(Calendar.YEAR);
//    	String yearStr = new Integer(year).toString();
//    	return yearStr.substring(2,4);//当前年份后两位
//	}
	/**
	 * 以天为单位，计算日期
	 * @param date 时间
	 * @param day 移动的天数
	 * @return
	 */
	public static Date moveTime(Date date,int day){
		Calendar   calendar   =   new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
		return date=calendar.getTime();   //这个时间就是日期往后推一天的结果
	}
	public static void main(String[] args){
		String aa="aa,aa,";
		System.out.printf(aa.substring(0,aa.length()-1));
//		System.out.println(getDateStr(new Date(), "yyyy-MM-dd")>"2017-10-10"?1,2);
//		Date startDate= null;
//		try {
//			startDate = getDate("2018-11-25 23:00:00","yyyy-MM-dd HH:mm:ss");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.printf(""+daysBetween(new Date(),startDate));
//		for(int i=1;i<20*2+1;i++){
//			System.out.println("");
//			System.out.print(i+"     ");
//			Calendar calendar = new GregorianCalendar();
//			calendar.setTime(startDate);
//			int amount=i*6;
//			//前5年一年一审
//			if(i<5*2+1&&i%2==1){
//				continue;
//			}
//			calendar.add(Calendar.MONTH, amount);// 把日期往后增加一年.整数往后推,负数往前移动
//			System.out.print(getDateStr(calendar.getTime(), "yyyy-MM-dd")+"       ");
//
//			if( calendar.getTime().compareTo(new Date())>0){
//				System.out.print("   "+calendar.getTime().compareTo(new Date())+"成立    ");
//			}else{
//				System.out.print("   "+calendar.getTime().compareTo(new Date())+"不成立    ");
//			}
//
//			calendar.add(Calendar.DATE, -90);
//			System.out.print(getDateStr(calendar.getTime(), "yyyy-MM-dd"));
//			System.out.print("    "+(i%2==0?2:1));
//		}
//		String dateStr = DateUtils.getDateStr(parseMills(1350994695000l), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
//		System.out.println(calculateTime(LocalDateTime.now(), "-", 60 * Integer.parseInt("1")));
//		System.out.println(getDateStr(moveTime(new Date(),1), "yyyy-MM-dd HH:mm:ss"));//2018-10-25 00:04:10  2018-10-26 00:04:10
//		try {
//			DateUtils.getDate("2018-04-10","yyyy-MM-dd");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
	public static String getNowTime() {
		return getToday("yyyy-MM-dd HH:mm:ss");
	}
	public static boolean checkTxnTime(String startTime, String endTime) {
		String nowDate = getDateStr(new Date(), "HH:mm:ss");
		if(nowDate.compareTo(startTime) >= 0 && nowDate.compareTo(endTime) <= 0){
			return true;
		}
		return false;
	}

	/**
	 * 	计算两个时间相差的天数
	 * @param date1  开始时间
	 * @param date2	结束时间
	 * @return
	 */
	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Long daysBetween(Date smdate,Date bdate)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		return  (time2 - time1) / (1000 * 3600 * 24);
	}

	public static String getNextTime(){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
		String format = localDateTime.format(dateTimeFormatter);
		return format;

	}

	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static  String getPreMonth(){
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime dateTime = localDateTime.minusMonths(1L);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return  dateTime.format(dateTimeFormatter);
	}

}
