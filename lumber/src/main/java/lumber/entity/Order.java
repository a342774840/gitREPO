package lumber.entity;

import java.io.Serializable;

/*
 * 用户订单的数据类
 */
public class Order implements Serializable{
	private String userId;
	private String orderId;
	private int status;
	private String productId;
	private Integer productNum;
	private Integer totalMoney;
	private String indexImage;
	private Integer productPrice;
	private String productName;
	private Integer selected;
	
	public Integer getSelected() {
		return selected;
	}
	public void setSelected(Integer selected) {
		this.selected = selected;
	}
	public String getIndexImage() {
		return indexImage;
	}
	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}
	public Integer getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	@Override
	public String toString() {
		return "Order [userId=" + userId + ", orderId=" + orderId + ", status=" + status + ", productId=" + productId
				+ ", productNum=" + productNum + ", totalMoney=" + totalMoney + ", indexImage=" + indexImage
				+ ", productPrice=" + productPrice + ", productName=" + productName + ", selected=" + selected + "]";
	}
	
	
	
}
