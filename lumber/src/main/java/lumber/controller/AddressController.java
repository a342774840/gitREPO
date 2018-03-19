package lumber.controller;

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
import lumber.dao.AddressDao;
import lumber.service.AddressService;
import lumber.tool.JsonTool;

/*
 * 用于处理地址操作的控制器
 */
@Api(description="地址操作")
@RestController
public class AddressController {
	//注入次对象，主要用户查询的操作,不需要事务处理
	@Autowired
	AddressDao addressDao;
	
	//注入次对象主要用户保证修改操作的原子化
	@Autowired
	AddressService addressService;
	
	//查询出该用户的所有地址信息
	@ApiOperation(value="查询用户的所有收货地址")
	@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值")
	@RequestMapping(value="/getAllAddressByUserId",method=RequestMethod.POST)
	public String getAddrs(@RequestParam("userId") String userId)
	{
		List addrs=addressDao.getAllAddressByUserId(userId);
		return JsonTool.toJson(addrs);
	}
	
	/*
	 * 添加一条地址信息
	 */
	@ApiOperation(value="添加收货地址")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值"),
			@ApiImplicitParam(dataType="String",name="name",
					required=true,value="收货人名字"),
			@ApiImplicitParam(dataType="String",name="phone",
					required=true,value="收货人电话"),
			@ApiImplicitParam(dataType="String",name="province",
			required=true,value="收货人所在省"),
			@ApiImplicitParam(dataType="String",name="city",
			required=true,value="收货人所在城市"),
			@ApiImplicitParam(dataType="String",name="area",
			required=true,value="收货人所在区"),
			@ApiImplicitParam(dataType="String",name="detailAddress",
			required=true,value="收货地址的详细信息")
	})
	@RequestMapping(value="/addAddress",method={RequestMethod.POST})
	public String addAddress(String userId,String name,String phone,String province,
			String city,String area,String detailAddress)
	{
		boolean flag=addressService.addAddress( userId, name, phone, province,
				 city, area, detailAddress);
		if(flag)
		{
			List addrs=addressDao.getAllAddressByUserId(userId);
			return JsonTool.toJson(addrs);
		}
		else
			return JsonTool.Tip(false);
				
	}
	
	
	/*
	 * 删除一条收货地址信息
	 */
	@ApiOperation(value="删除某一收货地址")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值"),
			@ApiImplicitParam(dataType="String",name="addressId",
					required=true,value="地址的编号")
	})
	@RequestMapping(value="/deleteAddress",method={RequestMethod.POST})
	public String deleteAddress(String userId,String addressId)
	{
		boolean flag=addressService.deleteAddress(userId,addressId);
		if(flag)
		{
			List addrs=addressDao.getAllAddressByUserId(userId);
			return JsonTool.toJson(addrs);
		}
		else
			return JsonTool.Tip(false);
	}
	/*
	 * 修改默认收货地址
	 */
	@ApiOperation(value="修改默认地址")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值"),
			@ApiImplicitParam(dataType="String",name="addressId",
					required=true,value="地址的编号")
	})
	@RequestMapping(value="/updateDefaultAddress",method={RequestMethod.POST})
	public String updateDefaultAddress(String userId,String addressId)
	{
		boolean flag=addressService.updateDefaultAddress(userId, addressId);
		if(flag)
		{
			List addrs=addressDao.getAllAddressByUserId(userId);
			return JsonTool.toJson(addrs);
		}
		else
			return JsonTool.Tip(false);
	}
	
	/*
	 * 更新某一地址的某些信息
	 */
	@ApiOperation(value="修改某一地址的内容信息")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值"),
			@ApiImplicitParam(dataType="String",name="addressId",
			required=true,value="地址信息的编号"),
			@ApiImplicitParam(dataType="String",name="name",
					required=true,value="收货人名字"),
			@ApiImplicitParam(dataType="String",name="phone",
					required=true,value="收货人电话"),
			@ApiImplicitParam(dataType="String",name="province",
			required=true,value="收货人所在省"),
			@ApiImplicitParam(dataType="String",name="city",
			required=true,value="收货人所在城市"),
			@ApiImplicitParam(dataType="String",name="area",
			required=true,value="收货人所在区"),
			@ApiImplicitParam(dataType="String",name="detailAddress",
			required=true,value="收货地址的详细信息")
	})
	@RequestMapping(value="/updateAddress",method={RequestMethod.POST})
	public String updateAddress(String userId,String addressId,String name,
			String phone,String province,String city,String area,
			String detailAddress)
	{
		boolean flag=addressService.updateAddress(userId, addressId, name,
				phone, province, city, area,
				detailAddress);
		if(flag)
		{
			List addrs=addressDao.getAllAddressByUserId(userId);
			return JsonTool.toJson(addrs);
		}
		else
			return JsonTool.Tip(false);
	}
	
	
}
