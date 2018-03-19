package lumber.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lumber.entity.Order;
import lumber.tool.NullJudgeTool;
import lumber.tool.StringTool;

/*
 * 主要用于查询 代发货 待收货 待评价的订单处理
 */
@Repository
@Transactional
public class OrderDao extends BaseDao{
	RowMapper<Order> rowMapper=new BeanPropertyRowMapper<Order>(Order.class);
	//status:1待发货  2:待收货 3:待评价  0:购物车的商品  -1已提交订单但为付款
	
	
	
	//查询某一类别的订单
	public List<Order> queryOrderByStatus(String userId,int status)
	{
		if(NullJudgeTool.nullReturnBoolean(userId))
			return null;
		String sql="select * from productOrder where userId=? and status=?";
		List<Order> orders=jdbcTemplate.query(sql, rowMapper, userId,status);
		return orders;
	}
	
	//查询出所有的订单信息
	public List<Order> queryAllOrders(String userId)
	{
		if(userId==null)
			return null;
		String sql="select * from productorder where userId=? and status!=0";
		List<Order> orders=jdbcTemplate.query(sql, rowMapper, userId);
		return orders;
	}
	
	
	
	
	
	//更改订单的选中状态  选中或者取消选中
	public boolean updateOrderSelect(String userId,String[] orderId,boolean selected)
	{
		if(NullJudgeTool.nullReturnBoolean(userId))
			return false;
		
		int select=0;
		if(selected)
			select=1;
		String sql="update productorder set selected=? where userId=? and orderId=?";
		if(userId!=null&&orderId!=null&&orderId.length!=0)
		{
			int len=orderId.length;
			for(int i=0;i<len;i+=1)
			{
				int flag=jdbcTemplate.update(sql,select, userId,orderId[i]);
				if(flag==0)
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	
	//更改订单的选中状态  选中或者取消选中  所有
	public boolean updateOrderAllSelect(String userId,boolean selected)
	{
		if(NullJudgeTool.nullReturnBoolean(userId))
			return false;		
		int select=0;
		if(selected)
			select=1;
		String sql="update productorder set selected=? where userId=?";
		int flag=jdbcTemplate.update(sql,select, userId);//返回值为修改的行数值
		if(flag!=0)
			return true;		
		return false;
	}

	
	//查询用户选择的购物车商品信息
	public List<Order> querySelectedOrder(String userId)
	{
		if(NullJudgeTool.nullReturnBoolean(userId))
			return null;
		if(userId!=null)
		{
			String sql="select * from productorder where userId=? and "
					+ " selected=1 and status=0";
			List<Order> productOrders=jdbcTemplate.query(sql, rowMapper, userId);
			return productOrders;
		}else{
			return null;
		}
		
	}
	
	
	//选中购物车商品后确认订单
	public List<Order> confirmOrders(String userId,String[] orderId)
	{
		if(userId!=null&&orderId!=null&&orderId.length!=0)
		{
			String params=StringTool.strArrayToStr(orderId);
			String sql="select * from productorder where userId=? and "
					+ "orderId in "+params+" and selected=1 and status=0";
			List<Order> productOrders=jdbcTemplate.query(sql, rowMapper, userId);
			return productOrders;
		}else{
			return null;
		}
		
	}
	
}
