package lumber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lumber.dao.AddressDao;
/*
 * 将对地址信息表的操作放置在事务的管理下，保证操作的原子化
 */
@Service
@Transactional
public class AddressService {
	//注入AddressDao 对象
	@Autowired
	AddressDao addressDao;
	
	
	/*
	 * 插入一条地址信息
	 */
	public boolean addAddress(String userId,String name,String phone,
			String province,String city,String area,String detailAddress)
	{
		boolean flag=addressDao.addAddress(userId, name, phone,
				province, city, area, detailAddress);
		
		return flag;
				
	}
	
	/*
	 * 修改地址的某些信息
	 */
	public boolean updateAddress(String  userId,String  addressId, String  name,
			String  phone,String   province,String   city,
			String  area,String   detailAddress)
	{
		boolean flag=addressDao.updateAddress(userId, addressId, name, phone,
				province, city, area, detailAddress);
		return flag;
	}
	
	
	
	
	
	
	/*
	 * 更新默认的收货地址
	 */
	public boolean updateDefaultAddress(String userId,String addressId)
	{
		boolean flag=addressDao.updateDefaultAddress(userId, addressId);
		return flag;
	}
	
	/*
	 * 删除某一地址
	 */
	public boolean deleteAddress(String userId,String addressId)
	{
		boolean flag=addressDao.deleteAddress(userId, addressId);
		return flag;
	}
	
	
}
