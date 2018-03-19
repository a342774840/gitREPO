package lumber.tool;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 主要处理自字符串的操作
 */
public class StringTool {
	//将字符串数组转化为SQL语句中的in  字段后的参数样式
	public static String strArrayToStr(String[] values)
	{
		StringBuffer sb=new StringBuffer("(");
		if(values==null)
			return null;
		else
		{
			
			int len=values.length;
			for(int i=0;i<len;i+=1)
			{
				if(i!=len-1)
					sb.append("'"+values[i]+"' ,");
				else
					sb.append("'"+values[i]+"'");
			}
			sb.append(")");
		}
		return sb.toString();
	}
	
	//得到随机字符串序列
	public static String randomString(int len)
	{
		UUID uuid = UUID.randomUUID(); 
		String s = UUID.randomUUID().toString();
		s=replaceChar(s);
		return s.substring(0,24);
	}
	/*
	 * 验证用户的账号是否为电话号码
	 */
	public static boolean validateAccount(String account)
	{
		if(account==null||account.length()<11)
			return false;
		else
		{
			String regExp = "^((13[0-9])|(15[0-3,5-9])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(account);  
	        return m.matches();  
		}
	}
	
	
	/*
	 * 生成快递单号
	 */
	public static String getExpressId()
	{
		Date date=new Date();
		String ymd=DateTool.getYMD(date);
		ymd=replaceChar(ymd);
		String time=""+date.getTime();
		return ymd+time;
	}
	
	//将-替换为""
	public static String replaceChar(String str)
	{
		str=str.replace("-", "");
		return str;
	}
	
	
}
