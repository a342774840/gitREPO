package lumber.entity;

import java.util.List;

/*
 * 用户进行Json字符串格式的转化
 * 主要现在用于物流的显示
 */
public class GsonObject<T> {
	//快递单号
	private String expressId;
	//快递公司 名字
	private String expressName;
	//快递公司的代码
	private String expComCode;
	//状态
	private String status;
	//物流信息
	private List<T> data;
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpComCode() {
		return expComCode;
	}
	public void setExpComCode(String expComCode) {
		this.expComCode = expComCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	
	
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	
	public GsonObject(String expressId, String expressName, String expComCode, String status, List<T> data) {
		super();
		this.expressId = expressId;
		this.expressName = expressName;
		this.expComCode = expComCode;
		this.status = status;
		this.data = data;
	}
	public GsonObject() {
		super();
	}
	@Override
	public String toString() {
		return "GsonObject [expressId=" + expressId + ", expressName=" + expressName + ", expComCode=" + expComCode
				+ ", status=" + status + ", data=" + data + "]";
	}
	
	
	
}
