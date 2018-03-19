package lumber.tool;

import java.util.Date;
import java.util.GregorianCalendar;

/*
 * ���ڹ�����
 */
public class DateTool {
	/*
	 * ͨ��Date�õ�yyyy-MM-dd���ַ���ֵ
	 * ��:2018-02-21
	 */
	public static String getYMD(Date date)
	{
		String month="";
		String day="";
		int year=1900+date.getYear();
		int m=date.getMonth()+1;
		int d=date.getDate();
		if(m<10)
			month="0"+m;
		else
			month=""+m;
		if(d<10)
			day="0"+d;
		else
			day=""+d;
		return year+"-"+month+"-"+day;
	}
	
	
	/*ͨ��Date�õ�yyyy-MM���ַ���ֵ
	 *  �� :2018-02
	 */
	public static String getYM(Date date)
	{
		String month="";
		String day="";
		int year=1900+date.getYear();
		int m=date.getMonth()+1;
		if(m<10)
			month="0"+m;
		else
			month=""+m;
		return year+"-"+month;
	}
		
		
	/*
	 * ���ݴ����µ�ĳһ�ܵ�ֵ �����õ���һ�ܵ�ʱ�䷶Χֵ
	 * Ϊ����ͼ���ṩX���ṩlabel
	 */
	public static Date[] theMonthWeekOfRange(int year,int month,int weekth)
	{
		int start=(weekth-1)*7+1;
		int end=0;
		
		if(weekth==5)
		{
			end=31;
		}
		else
		{
			end=weekth*7;
		}
		Date[] dates=new Date[2];
		dates[0]=new GregorianCalendar(year, month,start).getTime();
		dates[1]=new GregorianCalendar(year, month,end ).getTime();
		return dates;
	}
	
}
