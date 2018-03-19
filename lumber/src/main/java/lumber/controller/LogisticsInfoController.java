package lumber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lumber.dao.LogisticsDao;
import lumber.entity.GsonObject;
import lumber.entity.LogisticsInfo;
import lumber.tool.GsonTool;

/*
 * 物流信息相关的操作
 */
@Api(description="物流信息相关操作")
@RestController
public class LogisticsInfoController {
	@Autowired
	private LogisticsDao logisticsDao;
	/*
	 * 查询某一订单的物流信息
	 */
	@ApiOperation(value="查询某一订单的物流信息")
	@ApiImplicitParams(value={
			@ApiImplicitParam(dataType="String",name="userId",
					required=true,value="用户的ID值"),
			@ApiImplicitParam(dataType="String",name="orderId",
					required=true,value="订单的ID值"),
	})
	@RequestMapping(value="/queryLogByOrderId",method=RequestMethod.POST)
	public String queryLogByOrderId(String userId,String orderId)
	{
		GsonObject<LogisticsInfo> loginfo=logisticsDao.queryLogInfo(userId, orderId);
		String gson=GsonTool.objToJson(loginfo);
		return gson;
	}
	
}
