package lumber.tool;

import com.google.gson.Gson;

import lumber.entity.GsonObject;
import lumber.entity.LogisticsInfo;

/*
 * 通过Gson来转化Json和类之间
 */
public class GsonTool {
	/*
	 * 将对象转化为Json格式
	 */
	private static Gson gson=new Gson();
	private static GsonObject<LogisticsInfo> gsonObject=null;
	public static String objToJson(Object obj)
	{
		if(obj==null)
		{
			gsonObject =new GsonObject<LogisticsInfo>("","","","error",null);
			return gson.toJson(gsonObject);
		}else
		{
			return gson.toJson(obj);
		}
	}
}
