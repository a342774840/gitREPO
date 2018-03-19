package lumber.controller;
/*
 * 用于操作订单请求的控制器
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lumber.dao.OrderDao;
import lumber.dao.ShopCarDao;
import lumber.tool.JsonTool;

@Api(description="对订单进行操作")
@RestController
public class OrderController {
	//注入购物车操作的对象
	@Autowired
	ShopCarDao shopCarDao;
	//注入订单操作的对象
	@Autowired
	OrderDao orderDao;
	
	
	/*
	 * 添加一条产品信息到该用户的购物车中
	 * 先判断购物车中是否有同样的订单，若有则直接修改数量，
	 * 没有则直接插入一条新数据
	 */
	@ApiOperation(value="添加商品到购物车")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="productId",
			required=true,value="需要购买商品的ID值"),
			@ApiImplicitParam(dataType="String",name="productName",
					required=true,value="购买商品的商品名"),
			@ApiImplicitParam(dataType="String",name="indexImage",
					required=true,value="购买商品的首页图片的路径"),
			@ApiImplicitParam(dataType="Integer",name="productNum",
					required=true,value="每件商品的购买数量"),
			@ApiImplicitParam(dataType="Integer",name="productPrice",
					required=true,value="每件商品的单价")
	})
	@RequestMapping( value="/addToShopCar",method=RequestMethod.POST)
	public String addOrder(String userId,String productId,
			String productName,String indexImage,Integer productNum,
			Integer productPrice)
	{
		
			boolean flag=shopCarDao.addShopCar( userId, productId,
					 productName, indexImage, productNum,
					  productPrice);
		
		
		return JsonTool.Tip(flag);
				
	}
	
	/*
	 * 查询购物车的所有订单数据
	 */
	@ApiOperation(value="查询个人的购物车信息")
	@ApiImplicitParam(dataType="String",name="userId",
	required=true,value="用户的ID值")
	@RequestMapping(value="/queryShopCar",method=RequestMethod.POST)
	public String getOrdersByUserId(String userId)
	{
		List orders=shopCarDao.getShopCarOfPros(userId);
		return JsonTool.toJson(orders);
	}
	
	/*
	 * 删除购物车中的数据
	 */
	@ApiOperation(value="删除购物车中的某些商品")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="orderIds",allowMultiple=true,
			required=true,value="需要购买订单的编号(一个或多个)")
	})
	@RequestMapping(value="/deleteShopCar",method=RequestMethod.POST)
	public String deleteOrders(String userId,String[] orderIds)
	{
		boolean flag=shopCarDao.deleteShopCar(userId, orderIds);
		if(flag)
		{
			List orders=shopCarDao.getShopCarOfPros(userId);
			return JsonTool.toJson(orders);
		}
		return JsonTool.Tip(false);
	}
	
	/*
	 *  在已购买的订单中查询某一类别的订单信息
	 *  待发货、待收货、待评价等类别
	 */
	@ApiOperation(value="查询个人的某一状态下的订单信息")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="Integer",name="status",
			required=true,value="状态值")
	})
	@RequestMapping(value="/queryOrderByStatus",method={RequestMethod.POST})
	public String querycommitOrder(String userId,Integer status)
	{
		List orders=orderDao.queryOrderByStatus(userId, status);
		return JsonTool.toJson(orders);
	}
	
	/*
	 * 查询出所有的 “订单信息”
	 */
	@ApiOperation(value="查询出所有状态下的订单信息")
	@ApiImplicitParam(dataType="String",name="userId",
	required=true,value="用户的ID值")
	@RequestMapping(value="/queryAllOrder",method={RequestMethod.POST})
	public String queryAllOrders(String userId)
	{
		List order=orderDao.queryAllOrders(userId);
		return JsonTool.toJson(order);
		
	}
	
	//更新购物车的商品的购买数量
	@ApiOperation(value="更新一件商品的购买数量")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="productId",
			required=true,value="需要购买商品的ID值"),
			@ApiImplicitParam(dataType="Integer",name="productNum",
					required=true,value="购买商品的数量"),
			@ApiImplicitParam(dataType="Integer",name="productPrice",
					required=true,value="购买商品的单价"),
			@ApiImplicitParam(dataType="Integer",name="totalMoney",
					required=false,value="总金额")
	})
	@RequestMapping(value="/updateShopCarProductNum",method={RequestMethod.POST})
	public String updateProductNum(String userId,String productId,
			Integer productNum,Integer productPrice,@RequestParam(required=false) Integer totalMoney)
	{
		boolean flag=shopCarDao.updateProductNum(userId, productId,
				productNum,productPrice,totalMoney);
		return JsonTool.Tip(flag);
	}
	
	
	/*
	 * 修改购物车中订单的选中状态
	 */
	@ApiOperation(value="修改购物车中订单的选中状态")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="orderId",allowMultiple=true,
			required=true,value="订单编号值"),
			@ApiImplicitParam(dataType="boolean",name="selected",
			required=true,value="是否为选中")
	})
	@RequestMapping(value="/updateShopCarSelect",method={RequestMethod.POST})
	public String updateOrderSelect(String userId,String[] orderId,
			boolean selected)
	{
		boolean flag=orderDao.updateOrderSelect(userId, orderId, selected);
		if(flag)
		{
			List orders=shopCarDao.getShopCarOfPros(userId);
			return JsonTool.toJson(orders);
		}
		else
			return JsonTool.Tip(false);
	}
	
	/*
	 * 更改购物车中所有的订单选中状态
	 */
	@ApiOperation(value="选中购物车中所有的订单")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="boolean",name="selected",
			required=true,value="是否为选中")
	})
	@RequestMapping(value="/updateOrderAllSelect",method={RequestMethod.POST})
	public String updateOrderAllSelect(String userId,boolean selected)
	{
		boolean flag=orderDao.updateOrderAllSelect(userId, selected);
		if(flag)
		{
			List orders=shopCarDao.getShopCarOfPros(userId);
			return JsonTool.toJson(orders);
		}
		else
			return JsonTool.Tip(false);
	}
	
	
	//查询出用户选中的商品信息
	@ApiOperation(value="查询购物车中选中商品的信息")
	@ApiImplicitParam(dataType="String",name="userId",
	required=true,value="用户的ID值")
	@RequestMapping(value="/querySelectedOrder",method={RequestMethod.POST})
	public String confirmOrders(String userId)
	{
		List orders=orderDao.querySelectedOrder(userId );
		return JsonTool.toJson(orders);
	}
	
	
	//确认订单
	@ApiOperation(value="确认订单")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="orderIds",allowMultiple=true,
			required=true,value="订单编号值")
	})
	@RequestMapping(value="/confirmOrders",method={RequestMethod.POST})
	public String confirmOrders(String userId,String[] orderIds)
	{
		List orders=orderDao.confirmOrders(userId, orderIds);
		return JsonTool.toJson(orders);
	}
	
}
