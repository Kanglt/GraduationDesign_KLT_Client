
package lyu.klt.frame.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import lyu.klt.frame.ab.util.AbDateUtil;

public class DateUtil {
	
	public static final String FORMAT_TYPE1 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_TYPE2 = "yyyyMMddHHmmss";
	public static final String FORMAT_TYPE3 = "yyyy-MM-dd";
	public static final String FORMAT_TYPE4 = "yyyy/MM/dd";
	public static final String FORMAT_TYPE5 = "MM/dd HH:mm";
	public static final String FORMAT_TYPE6 = "MM/dd";
	public static final String FORMAT_TYPE7 = "yyyy/MM/dd HH:mm";
	public static final String FORMAT_TYPE8 = "HH:mm:ss";	
	public static final String FORMAT_TYPE9 = "yyyy年MM月";	
	public final static String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
	
	public static String getTimestampToString(Timestamp timestamp,String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String timeStr = format.format(timestamp); 
		return timeStr;
	}
	
	public static String getDateCN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日 23:41:31
	}

	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}

	public static String getDateEN1() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 
	}
	public static String getDateEN2() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 
	}

	public static String getDateEN3() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 
	}
	public static String getDateEN4() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 
	}
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}
	public static String getDate1() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}
	public static String getDateString() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}
	
	public static String getDateYMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日
	}
	
	public static String getDateMD() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;// 2012年10月03日
	}
	
	public static String getDateString(Date date){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(date);
		return date1;// 2012-10-03 
	}
	public static String getDateString4(Date date){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		String date1 = format1.format(date);
		return date1;// 2012-10-03 
	}
	/**
	 * 获取某天的年月
	 * @return
	 */
	public static String getDateYM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String date1 = format.format(date);
		return date1;// 2012-10
	}
	/**
	 * 获取某月
	 * @return
	 */
	public static String getDateM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("MM");
		String date1 = format.format(date);
		return date1;// 10
	}
	/**
	 * 获取某天
	 * @return
	 */
	public static String getDateD(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String date1 = format.format(date);
		return date1;// 10
	}
	/**
	 * 获取某年
	 * @return
	 */
	public static String getDateY(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String date1 = format.format(date);
		return date1;// 10
	}
	/**
	 * 取某年月的第一天
	 * @param yearMonth
	 * @return
	 */
	public static Date stringToDate(String yearMonth){
		String[] yearmonth = yearMonth.split("-");
		Calendar cal_select = Calendar.getInstance();
		cal_select.set(Calendar.YEAR,
				Integer.parseInt(yearmonth[0]));
		cal_select.set(Calendar.MONTH,
				Integer.parseInt(yearmonth[1]) - 1);
		cal_select.set(Calendar.DAY_OF_MONTH, 1);
		return cal_select.getTime();
	}
    /**
     * 当月最后一天
     * 
     * @author  linxw
     * @version  [版本号, 2014-10-9]   
     */
    public static String getMonthLastDay(String yearMonth)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	String[] yearmonth = yearMonth.split("-");
		Calendar cal_select = Calendar.getInstance();
		cal_select.set(Calendar.YEAR,
				Integer.parseInt(yearmonth[0]));
		cal_select.set(Calendar.MONTH,
				Integer.parseInt(yearmonth[1]) - 1);
        cal_select.set(Calendar.DAY_OF_MONTH, cal_select.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return format.format(cal_select.getTime()); 
    }
	/**
	 * 取某日期的前一个年月
	 * @param date
	 * @return
	 */
	public static String getPreYM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		cal.add(Calendar.MONTH, -1);
		String date1=format.format(cal.getTime());
		return date1;
	}
	/**
	 * 取某日期的下一个年月
	 * @param date
	 * @return
	 */
	public static String getNextYM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		cal.add(Calendar.MONTH, 1);
		String date1=format.format(cal.getTime());
		return date1;
	}
	/**
	 * 取某日期的偏移日期
	 * @param tempDate
	 * @return
	 */
	public static String getOffDate(String strDate,String format,int offset){
		Date date=AbDateUtil.getDateByFormat(strDate, format);
		Calendar c = new GregorianCalendar();
		try {
			c.setTime(date);
			c.add(Calendar.DATE, offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDateString(c.getTime());
	}
	
	
	
	public static String getFileNameByTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_TYPE2);

		return df.format(c.getTime());
	}

	public static String getStringTime() {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_TYPE1);

		return df.format(c.getTime());
	}
	public static String getStringTime(String formatType) {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(formatType);

		return df.format(c.getTime());
	}
	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	// long类型转换为String类型
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType)
			throws ParseException {
		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// long转换为Date类型
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType)
			throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static long stringToUnixTimeStamp(String strTime, String formatType) {
		try {
			return DateUtil.stringToLong(strTime, formatType) / 1000;
		} catch (ParseException e) {
			System.err.println(e.getMessage().toString());
		}
		return 0;
	}

	public static Date UnixTimeStampToDate(long strTime, String formatType) {
		try {
			return DateUtil.longToDate(strTime * 1000, formatType);
		} catch (ParseException e) {
			System.err.println(e.getMessage().toString());
		}
		return new Date();
	}

	public static int compare_date(String DATE1, String DATE2,String fotmatType) {

		DateFormat df = new SimpleDateFormat(fotmatType);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return -1;
	}
    public static int daysBetween(Date date1,Date date2)  

    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date1);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(date2);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));         

    }  
    public static int MonthDaysNum(int year , int month){
    	year+=1900;
    	int[] monDays = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    	 if(year%4==0&&year%100!=0||year%400==0) 
    		 if(month ==1){
    			 return 29;
    		 }
    	   
    	return monDays[month];
    }
	public static void dialogDate(Context context,final TextView date) {
        Calendar calendar = Calendar.getInstance();  
		Dialog dialog1 = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						int MonthOfYear;
						MonthOfYear = monthOfYear + 1;

						String Month = "";
						String DayOfMonth = "";
						if (MonthOfYear <= 9) {
							Month = "0" + MonthOfYear;
						} else {
							Month = "" + MonthOfYear;
						}
						if (dayOfMonth <= 9) {
							DayOfMonth = "0" + dayOfMonth;
						} else {
							DayOfMonth = "" + dayOfMonth;
						}
						date.setText("" + year + "-" + Month + "-"
								+ DayOfMonth);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));// 默认为系统时间
		dialog1.show();
	}

	public static void dialogtime(Context context,final TextView time) {
        Calendar calendar = Calendar.getInstance();  
		Dialog times = new TimePickerDialog(context, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String HourOfDay = "";
				String Minute = "";
				if (hourOfDay <= 9) {
					HourOfDay = "0" + hourOfDay;
				} else {
					HourOfDay = "" + hourOfDay;
				}
				if (minute <= 9) {
					Minute = "0" + minute;
				} else {
					Minute = "" + minute;
				}
				time.setText(HourOfDay + ":" + Minute);
			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				true);
		times.show();
	}
    /**
     * get Calendar of given year
     * @param year
     * @return
     */
    private static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
    
    /**
     * get start date of given week no of a year
     * @param year
     * @param weekNo
     * @return
     */
    public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
        
    }
    
    /**
     * get the end day of given week no of a year.
     * @param year
     * @param weekNo
     * @return
     */
    public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
    }
    
    /**
	 * 获取给定日期所在周的开始日期
	 * 
	 * @author John 2016-03-29
	 * @param Date
	 *            date
	 * @return String
	 */
	public static String getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * 获取给定日期所在周的结束日期
	 * 
	 * @author John 2016-03-29
	 * @param Date
	 *            date
	 * @return String
	 */
	public static String getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	/**
	 * 
	 * @Title: getWeekName   
	 * @Description: 获得周几   
	 * @param: @param data
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getWeekName(Date data){
	      Calendar cal = Calendar.getInstance();
	       cal.setTime(data);
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)
	            w = 0;

        return dayNames[w];
	}
	
	
	
	
	

	 private static Calendar calS=Calendar.getInstance();   
	 private static Pattern   p   =   Pattern.compile("\\d{4}-\\d{2}-\\d{2}");//定义整则表达式   
	   
	    /**  
	     * 计算日期相差的年月日
	     * @param startDateStr  
	     * @param endDateStr  
	     * @return  
	     */   
	    public static String remainDateToString(String startDateStr, String endDateStr){   
	        java.util.Date startDate = null;   
	        java.util.Date endDate= null;   
	        try {   
	            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);   
	            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            return "";   
	        }   
	        calS.setTime(startDate);   
	        int startY = calS.get(Calendar.YEAR);   
	        int startM = calS.get(Calendar.MONTH);   
	        int startD = calS.get(Calendar.DATE);   
	        int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);   
	        
	        calS.setTime(endDate);   
	        int endY = calS.get(Calendar.YEAR);   
	        int endM = calS.get(Calendar.MONTH); 
	        //处理2011-01-10到2011-01-10，认为服务为一天   
	        int endD = calS.get(Calendar.DATE)+1;   
	        int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);   
	           
	        StringBuilder sBuilder = new StringBuilder();   
	        if (endDate.compareTo(startDate)<0) {   
	            return sBuilder.append("过期").toString();   
	        }   
	        int lday = endD-startD;  
	        if (lday<0) {   
	            endM = endM -1;   
	            lday = startDayOfMonth+ lday;   
	        }   
	        //处理天数问题，如：2011-01-01 到 2013-12-31  2年11个月31天     实际上就是3年   
	        if (lday == endDayOfMonth) {   
	            endM = endM+1;   
	            lday =0;   
	        }   
	        int mos = (endY - startY)*12 + (endM- startM);   
	        int lyear = mos/12;   
	        int lmonth = mos%12;   
	        if (lyear >0) {   
	            sBuilder.append(lyear+"年");   
	        }   
	        if (lmonth > 0) {   
	            sBuilder.append(lmonth+"个月");   
	        } 
	       
	        //假如是小于一岁的时候，
	        if(lyear==0){
	         //sBuilder.append(lday+"天");   
		        if (lday >0 ) {   
		            sBuilder.append(lday-1+"天");   
		        }  
	        }else{
	           if(lday>0){
	            sBuilder.append(lday+"天"); 
	        }
	        }
	        return sBuilder.toString();   
	    }   
	       
	    /*  
	     * 转换 dataAndTime 2013-12-31 23:59:59 到  
	     * date 2013-12-31  
	     */   
	    public static String getDate(String dateAndTime){   
	        if (dateAndTime != null && !"".equals(dateAndTime.trim())) {   
	            Matcher   m   =  p.matcher(dateAndTime);    
	            if (m.find()) {   
	                  return dateAndTime.subSequence(m.start(), m.end()).toString();   
	            }   
	        }   
	        return "data error";   
	    }   
	    
	    public static String remainDateToStringYear(String startDateStr, String endDateStr){   
	        java.util.Date startDate = null;   
	        java.util.Date endDate= null;   
	        try {   
	            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);   
	            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            return "";   
	        }   
	        calS.setTime(startDate);   
	        int startY = calS.get(Calendar.YEAR);   
	        int startM = calS.get(Calendar.MONTH);   
	        int startD = calS.get(Calendar.DATE);   
	        int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);   
	        
	        calS.setTime(endDate);   
	        int endY = calS.get(Calendar.YEAR);   
	        int endM = calS.get(Calendar.MONTH); 
	        //处理2011-01-10到2011-01-10，认为服务为一天   
	        int endD = calS.get(Calendar.DATE)+1;   
	        int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);   
	           
	        StringBuilder sBuilder = new StringBuilder();   
	        if (endDate.compareTo(startDate)<0) {   
	            return sBuilder.append("过期").toString();   
	        }   
	        int lday = endD-startD;  
	        if (lday<0) {   
	            endM = endM -1;   
	            lday = startDayOfMonth+ lday;   
	        }   
	        //处理天数问题，如：2011-01-01 到 2013-12-31  2年11个月31天     实际上就是3年   
	        if (lday == endDayOfMonth) {   
	            endM = endM+1;   
	            lday =0;   
	        }   
	        int mos = (endY - startY)*12 + (endM- startM);   
	        int lyear = mos/12;   
	        int lmonth = mos%12;   
	        if (lyear >0) {   
	            sBuilder.append(lyear); 
	            return sBuilder.toString();
	        }   
	        sBuilder.append(0);
	        return sBuilder.toString();   
	    }   
	         
}
