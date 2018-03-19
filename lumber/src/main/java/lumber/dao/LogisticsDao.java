package lumber.dao;
/*
 * 用户管理物流信息的dao对象
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lumber.entity.GsonObject;
import lumber.entity.LogisticsInfo;

@Repository
public class LogisticsDao extends BaseDao {
	/*
	 * 
	 * 向订单 地址信息表中插入一条数据
	 */
	public boolean addTransportinfo(String userId,String addressId,String orderId,Date date)
	{
		String sql="insert into transportinfo values(?,?,?,?)";
		int flag=jdbcTemplate.update(sql, userId,addressId,orderId,date);
		return flag!=0?true:false;
	}
	
	/*
	 * 向订单 物流表中插入一条数据
	 */
	public boolean addOrderLogisticsInfo(String orderId,String companyName,
					String expressId,String logComCode,int selfDelivery)
	{
		String insertSql="insert into orderlogistics values(?,?,?,?,?,0)";
		int row=jdbcTemplate.update(insertSql, orderId,companyName,expressId,
				logComCode,selfDelivery);
		return row==1?true:false;
	}
	
	/*
	 * 初始化一条物流信息
	 */
	public boolean addLogisticsInfo(String expressId,String location,
			String context,Date ftime,Date time)
	{
		String insertSql="insert into logisticsinfo values(?,?,?,?,?)";
		int row=jdbcTemplate.update(insertSql, expressId,
				location,context,ftime,time);
		return row==1?true:false;
	}
	
	
	public GsonObject<LogisticsInfo> queryLogInfo(String userId,String orderId)
	{
		String sql="SELECT ol.expressId,companyName as expressName ,logComCode as expComCode,"
				+ "location,context,ftime,time FROM "
				+ "logisticsinfo li,orderlogistics ol,transportinfo ti "
				+"WHERE li.expressId=ol.expressId AND ti.orderId=ol.orderId AND"
				+ " ti.userId=? and ti.orderId=?";
		RowMapper<GsonObject<LogisticsInfo>> rowMapper=new LogisticsMapper();
		GsonObject<LogisticsInfo> gsonObject=null;
		try {
			gsonObject = jdbcTemplate.queryForObject(sql, rowMapper, userId,orderId);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return gsonObject;
		
	}
	
	
}

class LogisticsMapper implements RowMapper<GsonObject<LogisticsInfo>>
{

	@Override
	public GsonObject<LogisticsInfo> mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		if(rs==null)
			return null;
		else if(rs!=null&&rs.getRow()>0)
		{
			GsonObject<LogisticsInfo> gsonObject=new GsonObject<LogisticsInfo>();
			List<LogisticsInfo> lis=new ArrayList<LogisticsInfo>();
			rs.absolute(0);
			while(rs.next())
			{
				if(rs.getRow()==1)
				{
					gsonObject.setExpressName(rs.getString(2));
					gsonObject.setExpressId(rs.getString(1));
					gsonObject.setExpComCode(rs.getString(3));
					gsonObject.setStatus("success");
				}
				LogisticsInfo li=new LogisticsInfo();
				li.setLocation(rs.getString(4));
				li.setContext(rs.getString(5));
				li.setFtime(rs.getDate(6));
				li.setTime(rs.getDate(7));
				lis.add(li);
			}
			gsonObject.setData(lis);
			
			return gsonObject;
		}
		return null;
	}
	
}

