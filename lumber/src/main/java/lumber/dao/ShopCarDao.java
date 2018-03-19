package lumber.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lumber.entity.Order;
import lumber.tool.NullJudgeTool;
import lumber.tool.StringTool;

/*
 * 用于操作购买商品的相关操作
 */
@Repository
@Transactional
public class ShopCarDao extends BaseDao {
	RowMapper<Order> rowMapper=new BeanPropertyRowMapper<Order>(Order.class);
	/*
	 * 添加商品到购物车
	 */	
	public boolean addShopCar(String userId,String productId,
			String productName,String indexImage,Integer productNum,
			Integer productPrice)
	{
		boolean foo=NullJudgeTool.nullReturnBoolean( userId, productId,
				 productName, indexImage, productNum
				 , productPrice);
		if(foo)
			return false;
		boolean exists=existsSameProduct(userId, productId);
		System.out.println("exists: "+exists);
		int flag=0;
		if(exists)
		{
			flag=addProductBuySum(userId, productId, productNum);
		}else{
			String  id=StringTool.randomString(11);
			String sql="insert into productorder values(?,?,0,?,?,?,?,?,?,0)";
			flag=jdbcTemplate.update(sql, userId,id,productId,productName,
					indexImage,productNum,productNum*productPrice,productPrice);
		}
		
		
		
		return flag==1?true:false;
	}
	
	/*
	 * 查询购物车中的所有商品
	 */
	public List<Order> getShopCarOfPros(String userId)
	{
		if(userId==null)
			return null;
		String sql="select * from productorder where userId=? and status=0";
		List<Order> orders=jdbcTemplate.query(sql, rowMapper, userId);
		return orders;
		
	}
	
	/*
	 * 查询某一产品是否已添加到购物车中
	 */
	public boolean existsSameProduct(String userId,String productId)
	{
		String sameSql="select productId from productorder where"
				+ " userId=? and productId=? and status=0";
		String id=null;
		try {
			id = jdbcTemplate.queryForObject(sameSql, String.class,
					userId,productId);
		} catch (DataAccessException e) {
			return false;
		}
		if(id!=null&&id.equals(productId))
			return true;
		else
			return false;
	}
	
	/*
	 * 添加购物车中产品的购买数量
	 */
	public int addProductBuySum(String userId,String productId,int num)
	{
		String prodNumSql="select productNum from productorder where"
				+ " userId=? and productId=? and status=0";
		int hasNum=jdbcTemplate.queryForObject(prodNumSql, int.class, userId,productId);
		int totalNum=hasNum+num;
		
		String addSql="update productorder set productNum=productNum+?, "
				+ "totalMoney=productPrice*? where "
				+ "userId=? and productId=? and status=0";
		int rows=jdbcTemplate.update(addSql,num,totalNum, userId,productId);
		
		return rows;
	}
	
	
	
	
	/*
	 * 从购物车中移除某些产品
	 */
	public boolean deleteShopCar(String userId,String[] orderIds)
	{
		if(userId==null||orderIds==null)
			return false;
		String sql="delete from productorder where userId=? and orderId=? and status=0";
		if(orderIds==null||orderIds.length==0)
			return false;		
		else
		{
			for(int i=0;i<orderIds.length;i+=1)
			{
				jdbcTemplate.update(sql,userId, orderIds[i]);
			}
			return true;
		}
	}
	
	//修改购物车中的选购数量
	public boolean updateProductNum(String userId,String productId,
			Integer productNum,Integer productPrice,Integer totalMoney)
	{
		boolean foo=NullJudgeTool.nullReturnBoolean( userId, productId,
				 productNum,productPrice, totalMoney);
		if(foo)
			return false;
		String sql="update productorder set productNum=?,totalMoney=?"
				+ " where userId=? and productId=?";
		int flag=jdbcTemplate.update(sql, productNum,productNum*productPrice,userId,productId);
		return flag==1?true:false;
	}
	
	
	
}
