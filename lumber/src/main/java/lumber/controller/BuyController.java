package lumber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lumber.service.BuyService;
import lumber.tool.JsonTool;
import lumber.tool.StringTool;

/*
 * 购买商品的控制器
 */
@Api(description="购买商品相关的操作")
@RestController
public class BuyController {
	@Autowired
	BuyService buyService;
	
	
	/*
	 * 从购物车中购买商品 多个商品购买时  使用循环遍历
	 * 合并商品的购买方式、直接购买  或者从购物车中购买
	 */
	@ApiOperation(value="购买一件或者多件商品（直接购买、从购物车中购买）")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的编号值"),
			@ApiImplicitParam(dataType="String",name="productId",allowMultiple=true,
			required=true,value="需要购买商品的ID值（单个或者多个）"),
			@ApiImplicitParam(dataType="String",name="orderId",allowMultiple=true,
			required=false,value="若是从购物车中买则为购物车中的订单号（单个或者多个）（可选）"),
			@ApiImplicitParam(dataType="String",name="productName",allowMultiple=true,
					required=true,value="购买商品的商品名（单个或者多个）"),
			@ApiImplicitParam(dataType="String",name="indexImage",allowMultiple=true,
					required=true,value="购买商品的首页图片的路径（单个或者多个）"),
			@ApiImplicitParam(dataType="Integer",name="productNum",allowMultiple=true,
					required=true,value="每件商品的购买数量（单个或者多个）"),
			@ApiImplicitParam(dataType="Integer",name="productPrice",allowMultiple=true,
					required=true,value="每件商品的单价（单个或者多个）"),
			@ApiImplicitParam(dataType="String",name="addressId",
					required=true,value="用户的收货地址的编号"),
			@ApiImplicitParam(dataType="Integer",name="buyMethod",
					required=true,value="用户的购买方式（1：直接购买:0：从购物车中购买）"),
	})
	@RequestMapping(value="/buyProduct",method=RequestMethod.POST)
	public String buyProduct(String userId,String productId[],@RequestParam(required=false) String orderId[],
			String productName[],String indexImage[],Integer productNum[],
			Integer productPrice[],String addressId,Integer buyMethod)
	{
		if(buyMethod!=null)
		{
			if(buyMethod==1)//直接购买
			{
				String oId=StringTool.randomString(11);
				buyService.buyProduct(userId, productId[0], 
						productName[0], indexImage[0],addressId, productNum[0],
						productPrice[0],oId);
			}else if(buyMethod==0){//从购物车里面购买
				buyService.buyProduct(userId, productId,orderId, productName,
						indexImage, productNum, productPrice, addressId);
			}
			return JsonTool.Tip(true);
		}
		
		
		return JsonTool.Tip(false);
				
				
	}
	

	
	
}
