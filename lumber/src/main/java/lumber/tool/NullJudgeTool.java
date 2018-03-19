package lumber.tool;
/*
 * 为空判断
 */
public class NullJudgeTool {
	//返回值为boolean
	public static boolean nullReturnBoolean(Object...objects)
	{
		if(objects==null)
			return true;
		else
		{
			int len=objects.length;
			for(int i=0;i<len;i+=1)
			{
				if(objects[i]==null)
					return true;
				if(String.valueOf(objects[i]).trim().equals("undefined"))
				{
					System.out.println("UserId未定义");
					return true;
				}
					
			}
		}
		return false;
	}
	
	
	
}
