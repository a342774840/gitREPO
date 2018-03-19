package lumber.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lumber.entity.Address;
import lumber.tool.NullJudgeTool;
import lumber.tool.StringTool;

/*
 * 对地址进行相关的操作
 */
@Repository
@Transactional
public class AddressDao extends BaseDao {
	RowMapper<Address> rowMapper=new BeanPropertyRowMapper<Address>(Address.class);
	
	//查询出某一用户的所有收货地址
	public List<Address> getAllAddressByUserId(String userId)
	{
		if(userId==null)
			return null;
		String sql="select * from address where userId=?";
		List<Address> addrs=null;
		try {
			addrs = jdbcTemplate.query(sql, rowMapper, userId);
		} catch (DataAccessException e) {
			return  null;
		}
		return addrs;
	}
	
	
	//新增加某一收货地址
	public boolean addAddress(String userId,String name,String phone,String province,
			String city,String area,String detailAddress)
	{
		boolean foo=NullJudgeTool.nullReturnBoolean( userId, name, phone, province,
				 city, area, detailAddress);
		if(foo)
			return false;
		boolean validate=StringTool.validateAccount(phone);
		if(!validate)
			return false;
		List<Address> addrs=getAllAddressByUserId(userId);
		int defaultAddress=0;
		/*
		 * 若这条地址信息为第一条，默认为用户的默认收货地址
		 * 以后添加的地址都不是默认的地址，但可修改默认地址
		 */
		if(addrs==null||addrs.size()==0)
		{
			defaultAddress=1;
		}
		
		
		Date date=new Date();
		String sql="insert into address values(?,?,?,?,?,?,?,?,?)";
		int flag=jdbcTemplate.update(sql, userId,date.hashCode(),name,phone,province,
				city,area,detailAddress,defaultAddress);
		return flag==1? true:false;
		
	}
	
	/*
	 * 删除一条收货地址
	 */
	public boolean deleteAddress(String userId,String addressId)
	{
		if(userId==null||addressId==null)
			return false;
		String sql="delete from address where addressId=? and userId=?";
		int flag=jdbcTemplate.update(sql, addressId,userId);
		return flag==1?true:false;
	}
	
	/*
	 * 修改用户的地址信息
	 */
	public boolean updateAddress(String userId,String addressId,String name,
			String phone,String province,String city,String area,
			String detailAddress)
	{
		boolean foo=NullJudgeTool.nullReturnBoolean( userId, name, phone, province,
				 city, area, detailAddress);
		if(foo)
			return false;
		boolean validate=StringTool.validateAccount(phone);
		if(!validate)
			return false;
		
		
		String update ="update address set name=?,phone=?,"
				+ "province=?,city=?,area=?,detailAddress=?"
				+ "  where userId=? and addressId=?";
		
			int f2=jdbcTemplate.update(update, name,phone,province,city,area,
					detailAddress,userId,addressId);
			if(f2!=0)
				return true;		
		return false;
	}
	
	//修改默认收货地址
	public boolean updateDefaultAddress(String userId,String addressId)
	{
		if(userId==null||addressId==null)
			return false;
		//先将所有的地址置为非默认
		String resetDefaultSql="update address set defaultAddress=0 where userId=?";		
		int flag1=jdbcTemplate.update(resetDefaultSql, userId);
		
		//设置指定的地址为默认地址
		String setDefaultSql="update address set defaultAddress=1 where"
				+ " userId=? and addressId=?";
		int flag2=jdbcTemplate.update(setDefaultSql, userId,addressId);
		if(flag1==0||flag2==0)
			return false;
		return true;
	}
	
}
