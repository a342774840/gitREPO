package lumber.tool;

import com.thoughtworks.xstream.XStream;

import lumber.model.OrderReturnInfo;

public class XStreamTool {
	public static OrderReturnInfo fromXML(String str,XStream xStream)
	{
		
		OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(str);
		return returnInfo;
	}
}
