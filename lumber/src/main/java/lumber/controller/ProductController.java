package lumber.controller;
/*
 * 用于处理产品的请求
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
import lumber.dao.ProductDao;
import lumber.entity.Product;
import lumber.tool.JsonTool;

@Api(description="用于执行商品相关的操作")
@RestController
public class ProductController {
	//设置精选商品的种类名字为
	private  final String KIND="elite";
	
	
	//注入操作产品相关的对象
	@Autowired
	ProductDao productDao;
	
	/*
	 * 查询所有的产品信息
	 */
	@ApiOperation(value="查询出所有的商品信息")
	@ApiImplicitParam(dataType="String",name="searchName",required=false,value="需要匹配的商品名")
	@RequestMapping(value="/getAllProduct",
					method={RequestMethod.POST})
	public String getAllProduct(@RequestParam(required=false,name="searchName")
	String productName)
	{
		List products=null;
		if(productName!=null)
		{
			products=productDao.getProductsLikeName(productName);
		}else
		{
			products=productDao.getAllProduct();
		}		
		return JsonTool.toJson(products);
	}
	
	
	/*
	 * 查询产品的所有分类
	 */
	@ApiOperation(value="查询出所有的商品分类")
	@RequestMapping(value="/productKinds",
			method={RequestMethod.POST})
	public String productKinds()
	{
		List<String> kinds=productDao.productKinds();
		return JsonTool.CollectionToJson("kind", kinds);
	}
	
	/*
	 * 通过某一产品的类名来查询该类的所有商品
	 */
	@ApiOperation(value="查询出某一分类下的所有的商品")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="productKind",
					  required=true,value="商品的分类名"),
			@ApiImplicitParam(dataType="String",name="searchName",
					  required=false,value="需要匹配的商品名")	
	})
	
	@RequestMapping(value="/getProductsByKind",method=RequestMethod.POST)
	public String getProductsByKind(String productKind,
			@RequestParam(name="searchName",required=false) String productName)
	{
		List products=null;
		if(productName!=null)
		{
			products=productDao.getProductsByKindAndProductName(productKind, productName);
		}else{
			products=productDao.getProductsByKind(productKind);
		}
		
		return  JsonTool.toJson(products);
	}
	/*
	 * 通过商品名字来模匹配
	 */
	@ApiOperation(value="通过商品名来模糊查询")
	@ApiImplicitParam(dataType="String",name="name",
					  required=true,value="需要匹配的商品名")
	@RequestMapping(value="/likeQueryProductByName",
			method={RequestMethod.POST})
	public String getProductsByLikeName(String name)
	{
		List products=productDao.getProductsLikeName(name);
		return  JsonTool.toJson(products);
	}
	
	/*
	 * 按照产品的销量来查询desc排列
	 */
	@ApiOperation(value="按价格来排序查询")
	@ApiImplicitParam(dataType="String",name="searchName",
					  required=false,value="需要匹配的商品名")
	@RequestMapping(value="/getProductsBySalesDesc",method=RequestMethod.POST)
	public String getProductsBySalesDesc(@RequestParam(required=false,name="searchName")
					String productKind)
	{
		List products=null;
		if(productKind!=null)
		{
			products=productDao.getProductsBySalesDescWithproductKind(productKind);
		}else{
			products=productDao.getProductsBySalesDesc();
		}
		
		return  JsonTool.toJson(products);
	}
	
	
	/*
	 * 根据上线时间来查询，按时间的降序
	 */
	@ApiOperation(value="根据上线时间查询所有的商品")
	@ApiImplicitParam(dataType="String",name="searchName",
					  required=false,value="需要匹配的商品名")
	@RequestMapping(value="/getProductsByOnlineTime",method=RequestMethod.POST)
	public String getProductsByOnlineTime(@RequestParam(required=false,name="searchName")
	String productKind)
	{
		List products=null;
		if(productKind!=null)
		{
			products=productDao.getProductsByOnlineTime(productKind);
		}else{
			products=productDao.getProductsByOnlineTime();
		}
		
		return  JsonTool.toJson(products);
	}
	
	/*
	 * 根据价格的升、降来排序
	 */
	@ApiOperation(value="根据价格来查询商品")
	@ApiImplicitParam(dataType="String",name="searchName",
					  required=false,value="需要匹配的商品名")
	@RequestMapping(value="/getProductsByPriceASC",method=RequestMethod.POST)
	public String getProductsByASC(@RequestParam(required=false,name="searchName")
				String productKind,boolean asc)
	{
		List products=null;
		if(productKind!=null)
		{
			products=productDao.getProductsByASC(productKind, asc);
		}else{
			products=productDao.getProductsByASC(asc);
		}	
		
		return  JsonTool.toJson(products);
	}
	
	/*
	 * 查询商品的具体信息和相关的信息
	 */
	@ApiOperation(value="查询出某件商品的详细信息")
	@ApiImplicitParam(dataType="String",name="productId",
					  required=true,value="需要查询的产品的ID值")
	@RequestMapping(value="/productLinkInfo",
			method={RequestMethod.POST})
	public String productLinkInfo(String productId)
	{
		//查询出某一产品的信息
		Product product=productDao.getProductById(productId);
		
		//产品的轮播图
		List<String> ups=productDao.imageUrls(productId, 1);
		
		//产品的详情图
		List<String> downs=productDao.imageUrls(productId, 0);
		return JsonTool.toJsonFromObjAndList(product, null,ups,downs);
	}
	
	/*
	 * 查询精选类商品
	 */
	@ApiOperation(value="查询精选下的所有商品")
	@RequestMapping(value="/queryPerfectPro",
			method={RequestMethod.POST})
	public String queryPerfectPro()
	{
		
		List products=productDao.getProductsByKind("精选商品");
		return  JsonTool.toJson(products);
	}
	
	
}
