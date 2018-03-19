package lumber.tool;


import java.lang.reflect.Field;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*
 * 将对应的Java对对象转化为JSon格式的数据
 */
public class JsonTool {
	//将单个对象转化为Json格式的数据
	public static  String toJsonFromObj(Object obj)
	{
		JSONObject sum=new JSONObject();
		String status="error";
		int len=0;
		if(obj==null)
		{
			return Tip(false);
		}
		else
		{
			len=1;
			status="success";
			Field[] fields=obj.getClass().getDeclaredFields();
			
			JSONObject temp=new JSONObject();
			for(int j=0;j<fields.length;j+=1)
			{					
				fields[j].setAccessible(true);
				String fieldName=fields[j].getName();
				try {
					Object fieldValue=fields[j].get(obj);
					temp.put(fieldName,fieldValue==null?"null":fieldValue );
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			try {
				sum.put("data", temp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		try {
			sum.put("status", status);
			sum.put("length", len);
		} catch (JSONException e) {
		}
		
		return sum.toString();
	}
	
	
	
	
	
	
	//
	public static  String toJson(List<Object> lists)
	{
		if(lists==null)
		{
			JSONObject JsonObject=new  JSONObject();
			try {
				JsonObject.put("data", "null");
				JsonObject.put("status", "error");
				JsonObject.put("length", 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return JsonObject.toString();			
			
		}
		else if(lists.size()==0){
			JSONObject JsonObject=new  JSONObject();
			try {
				JsonObject.put("data", "null");
				JsonObject.put("status", "success");
				JsonObject.put("length", 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return JsonObject.toString();	
		}
		
		String arrayName=lists.get(0).getClass().getSimpleName()+"s";
		JSONObject JsonObject=new  JSONObject();
		JSONArray JsonArray=new JSONArray();
		
		try {
			int size=lists.size();
			for(int i=0;i<size;i+=1)
			{
				Object obj=lists.get(i);
				Field[] fields=obj.getClass().getDeclaredFields();
				
				JSONObject temp=new JSONObject();
				for(int j=0;j<fields.length;j+=1)
				{					
					fields[j].setAccessible(true);
					String fieldName=fields[j].getName();
					Object fieldValue=fields[j].get(obj);
					temp.put(fieldName,fieldValue==null?"null":fieldValue );
				}
				JsonArray.put(temp);
			}
			JsonObject.put(arrayName, JsonArray);
			JsonObject.put("status", "success");
			JsonObject.put("length", size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonObject.toString();
	}
	
	
	
	
	public static String CollectionToJson(String arrayName,List<String> list)
	{
		JSONObject JsonObject=new JSONObject();
		JSONArray JsonArray=new JSONArray();
		int len=list.size();
		
		if(list==null||list.size()==0)
		{
			return Tip(false);
		}		
		
		for(int i=0;i<len;i+=1)
		{
				JsonArray.put(list.get(i));				
		}
		try {
				JsonObject.put(arrayName, JsonArray);
				JsonObject.put("status", 1);
				JsonObject.put("length", len);
			} catch (JSONException e){
				try {
					JsonObject.put("status", 0);
				} catch (JSONException e1) {
				}
			}
		
		return JsonObject.toString();
	}
	
	
	
	public  static String Tip(boolean status)
	{
		JSONObject JsonObject=new JSONObject();
		try {
			JsonObject.put("data", "null");
			JsonObject.put("status", status?"success":"error");
			JsonObject.put("length", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return JsonObject.toString();
	}
	
	
	public static String toJsonFromObjAndList(Object obj,List objs,
			List<String> ups,List<String> downs)
	{
		JSONObject sum=new JSONObject();
		if(obj==null)
		{
			String  res=Tip(false);
			return res;
		}else
		{
		try {
			
//			处理轮播路径
			if(ups!=null&&ups.size()>0)
			{
				JSONArray JsonArray1=new JSONArray();
				int len1=ups.size();
				for(int i=0;i<len1;i+=1)
				{
					JsonArray1.put(ups.get(i));				
				}				
				sum.put("ups", JsonArray1);
			}else{
				sum.put("ups", "null");
			}
			
			
			//处理详情图片路径
			if(downs!=null&&downs.size()>0)
			{
				JSONArray JsonArray2=new JSONArray();
				int len2=downs.size();
				for(int i=0;i<len2;i+=1)
				{
					JsonArray2.put(downs.get(i));				
				}				
				sum.put("downs", JsonArray2);
				
			}else{
				sum.put("downs", "null");
			}
			
			
			
			Field[] objfields=obj.getClass().getDeclaredFields();
			String name1=obj.getClass().getSimpleName();
			System.out.println("Name1:"+name1);
			JSONObject temp1=new JSONObject();
			for(int j=0;j<objfields.length;j+=1)
			{					
				objfields[j].setAccessible(true);
				String fieldName1=objfields[j].getName();			
				Object fieldValue1=objfields[j].get(obj);
				temp1.put(fieldName1,fieldValue1);				
			}			
			sum.put(name1, temp1);
			if(objs==null||objs.size()==0)
			{
				
			}else
			{			
				int len=objs.size();
				JSONArray jsons=new JSONArray();
				String name2=objs.get(0).getClass().getSimpleName();
				System.out.println("2:"+name2);
				for(int i=0;i<len;i+=1)
				{
					Object temp2=objs.get(i);
					Field[] fields2=temp2.getClass().getDeclaredFields();
					JSONObject jsonObj=new JSONObject();
					for(int j=0;j<fields2.length;j+=1)
					{					
						fields2[j].setAccessible(true);
						String fieldName2=fields2[j].getName();			
						Object fieldValue2=fields2[j].get(temp2);
						jsonObj.put(fieldName2,fieldValue2);				
					}			
					jsons.put(jsonObj);					
				}
				sum.put(name2, jsons);
			}
			sum.put("status", "success");
			sum.put("length", 1);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return sum.toString();
	}
	
	
	
}
