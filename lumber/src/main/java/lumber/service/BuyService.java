package lumber.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lumber.dao.BuyDao;
import lumber.dao.LogisticsDao;
import lumber.dao.ProductDao;
import lumber.tool.JsonTool;
import lumber.tool.NullJudgeTool;
import lumber.tool.StringTool;

/*
 * 对购买操作的原子化，保证操作的一致
 */

@Service
@Transactional
public class BuyService {
	//注入商品购买操作的对象
	@Autowired
	BuyDao buyDao;
	
	//注入商品信息更新的对象
	@Autowired
	ProductDao productDao;
	/*
	 * 注入物流操作的dao对象
	 */
	@Autowired
	private LogisticsDao logisticsDao;
	
	
	/*
	 * 从购物车中购买商品  
	 */
	public String buyProduct(String userId,String productId[],String orderId[],
			String productName[],String indexImage[],Integer productNum[],
			Integer productPrice[],String addressId)
	{
		boolean foo=NullJudgeTool.nullReturnBoolean( userId,productId, orderId,
				 productName, indexImage, productNum,
				 productPrice);
		if(foo)
			return JsonTool.Tip(false);
		else
		{
			int len=orderId.length;
			buyDao.buyProFromShopCar(userId, orderId, addressId);
			for(int i=0;i<len;i+=1)//更新产品销量
			{			
				
				boolean flag1=productDao.updateProduct(productId[i], productNum[i]);
				if(!flag1)
				{
					return JsonTool.Tip(false);
				}
					
			}
			return JsonTool.Tip(true);			
		}
		
	}
	
	
	/*
	 * 直接购买商品
	 */
	public boolean buyProduct(String userId,String productId,String productName,
			String indexImage,String addressId,Integer productNum,Integer productPrice
			,String oId)
	{
		System.out.println( userId+ productId+ productName+indexImage+ addressId
				+productNum+ productPrice+ oId);
		boolean logo=false;
		boolean flag=buyDao.buyProduct(userId, productId, productName,
				indexImage,addressId, productNum, productPrice,oId);
		if(flag)
		{
			boolean flag1=productDao.updateProduct(productId, productNum);
			if(flag1)
			{
				Date date=new Date();
				//添加订单和相关的地址信息
				logo=logisticsDao.addTransportinfo(userId, addressId,oId,date);
				//添加物流公司的信息
				String expressId=StringTool.getExpressId();
				logisticsDao.addOrderLogisticsInfo(oId, "锦官木业公司",
						expressId, "jinguangmuye", 1);
				//初始化一条物流信息				
				logisticsDao.addLogisticsInfo(expressId, "等待发货", "货物正在出库",
						date, date);
			}
				
		}
		
		
		return logo;
	}
	
}
