package lumber.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

import lumber.common.Configure;
import lumber.common.HttpRequest;
import lumber.common.RandomStringGenerator;
import lumber.common.Signature;
import lumber.model.OrderInfo;
import lumber.model.OrderReturnInfo;


//得到prepay_id的值
@Controller
public class PlaceOrderController {
	@RequestMapping(value="/getPrepayid")
	public String  prepay_id(HttpServletRequest request, HttpServletResponse response)
	{
		try {
			String openid = request.getParameter("openid");
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("锦官商城");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setTotal_fee(10);
			order.setSpbill_create_ip("123.57.218.54");
			order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//签名
			String sign = Signature.getSign(order);
			order.setSign(sign);
			
			
			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class); 

			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			JSONObject json = new JSONObject();
			json.put("prepay_id", returnInfo.getPrepay_id());
//			response.getWriter().append(json.toJSONString());
			return json.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
