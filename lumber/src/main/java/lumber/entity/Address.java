package lumber.entity;

import java.io.Serializable;

/*
 * 用户地址所对应的字段类
 */
public class Address implements Serializable{
	private String userId;
	private String addressId;
	private String Name;
	private String phone;
	private String detailAddress;
	private int defaultAddress;
	private String province;
	private String city;
	private String area;
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public int getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(int defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	@Override
	public String toString() {
		return "Address [userId=" + userId + ", addressId=" + addressId + ", Name=" + Name + ", phone=" + phone
				+ ", detailAddress=" + detailAddress + ", defaultAddress=" + defaultAddress + ", province=" + province
				+ ", city=" + city + ", area=" + area + "]";
	}
	
	
}
