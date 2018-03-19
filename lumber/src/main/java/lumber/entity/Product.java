package lumber.entity;

import java.io.Serializable;
import java.util.Date;

/*
 * 对应数据库中的product 数据表
 */
public class Product implements Serializable{
	private String productId;
	private String productName;
	private Integer productPrice;
	private Integer productSaledSum;
	private String productKind;
	private String productDesc;
	private Integer sendMoney;
	private String sendAddress;
	private String indexImage;
	private Date onlineTime;
	
	public String getIndexImage() {
		return indexImage;
	}
	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Integer getSendMoney() {
		return sendMoney;
	}
	public void setSendMoney(Integer sendMoney) {
		this.sendMoney = sendMoney;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getProductSaledSum() {
		return productSaledSum;
	}
	public void setProductSaledSum(Integer productSaledSum) {
		this.productSaledSum = productSaledSum;
	}
	public String getProductKind() {
		return productKind;
	}
	public void setProductKind(String productKind) {
		this.productKind = productKind;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productSaledSum=" + productSaledSum + ", productKind=" + productKind + ", productDesc="
				+ productDesc + ", sendMoney=" + sendMoney + ", sendAddress=" + sendAddress + "]";
	}
	
	
	
}
