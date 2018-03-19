package lumber.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lumber.tool.NullJudgeTool;
import lumber.tool.StringTool;

/*
 * 用户处理商品购买的操作
 * 
 */
@Repository
@Transactional
public class BuyDao extends BaseDao {
	/*
	 * 注入物流操作的dao对象
	 */
	@Autowired
	private LogisticsDao logisticsDao;
	
	//直接购买商品
	public boolean buyProduct(String userId, String productId,
			 String productName, String indexImage,String addressId, Integer productNum,
			 Integer productPrice,String oId)
	{
		//为空判断
		boolean foo=NullJudgeTool.nullReturnBoolean(userId,productId,
				  productName,indexImage, productNum,productPrice);
		if(foo)
			return false;
		
		
		String sql="insert into productOrder values(?,?,1,?,?,?,?,?,?,0)";
		int flag=jdbcTemplate.update(sql, userId,oId,productId,productName,  indexImage, 
				productNum, productNum*productPrice, productPrice);
		return flag!=0?true:false;
	}
	
	
	//从购物车中购买商品
	public boolean buyProFromShopCar(String userId,String[] orderIds,String addressId)
	{
		String buySql="update productorder set status=1,selected=0 where userId=? and orderId=?";
		if(userId==null||orderIds==null||addressId==null)
			return false;
		else{
			int len=orderIds.length;
			for(int i=0;i<len;i+=1)
			{
				Date date=new Date();
				jdbcTemplate.update(buySql, userId,orderIds[i]);
				logisticsDao.addTransportinfo(userId,addressId,orderIds[i],date);
				String expressId=StringTool.getExpressId();
				boolean ok=logisticsDao.addOrderLogisticsInfo(orderIds[i], "锦官木业公司",
						expressId, "jinguangmuye", 1);
				if(ok)
				//初始化一条物流信息				
				logisticsDao.addLogisticsInfo(expressId, "等待发货", "货物正在出库",
						date, date);
			}
		}
		
		return false;
	}
	
	
	//提交订单但未付款 
	public boolean commitOrderNoPay(String userId, String productId,
			 String productName, String indexImage, Integer productNum,
			 Integer totalMoney,Integer productPrice)
	{
		//为空判断
		boolean foo=NullJudgeTool.nullReturnBoolean( userId,  productId,
				  productName,  indexImage,  productNum,
				  totalMoney, productPrice);
		if(foo)
			return false;
		
		Date date=new Date();
		String sql="insert into productOrder values(?,?,?,-1,?,?,?)";
		int flag=jdbcTemplate.update(sql, userId,date.hashCode(),productId,productName,  indexImage, 
				productNum, totalMoney, productPrice);
		return flag==1?true:false;
	}
	
	
	
	
	
}
