package lumber.entity;

import java.util.Date;

/*
 * 产品的评价类
 */
public class ProductAccess {
	private String productId;
	private String accessId;
	private String accessContent;
	private String userId;
	private Date accessDate;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	public String getAccessContent() {
		return accessContent;
	}
	public void setAccessContent(String accessContent) {
		this.accessContent = accessContent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	@Override
	public String toString() {
		return "ProductAccess [productId=" + productId + ", accessId=" + accessId + ", accessContent=" + accessContent
				+ ", userId=" + userId + ", accessDate=" + accessDate + "]";
	}
	
}
