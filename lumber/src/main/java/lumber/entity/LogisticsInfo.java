package lumber.entity;

import java.util.Date;

/*
 * 每个节点的物流信息类
 */
public class LogisticsInfo {
	//物流信息具体描述
	private String context;
	//物流所在地
	private String location;
	//到达时间
	private Date ftime;
	//签收时间 一般和到达时间相等
	private Date time;
	
	
	
	
	public String getContext() {
		return context;
	}




	public void setContext(String context) {
		this.context = context;
	}




	public String getLocation() {
		return location;
	}




	public void setLocation(String location) {
		this.location = location;
	}




	public Date getFtime() {
		return ftime;
	}




	public void setFtime(Date ftime) {
		this.ftime = ftime;
	}




	public Date getTime() {
		return time;
	}




	public void setTime(Date time) {
		this.time = time;
	}




	@Override
	public String toString() {
		return "logisticsInfo [context=" + context + ", location=" + location + ", ftime=" + ftime + ", time=" + time
				+ "]";
	}
	
}
