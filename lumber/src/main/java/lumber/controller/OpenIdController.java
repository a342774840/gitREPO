package lumber.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

import lumber.common.Configure;
import lumber.common.HttpRequest;
import lumber.common.RandomStringGenerator;
import lumber.common.Signature;
import lumber.model.OrderInfo;
import lumber.model.OrderReturnInfo;
import lumber.model.SignInfo;
import lumber.tool.XStreamTool;




@Controller
public class OpenIdController {
		
	@ResponseBody
	@RequestMapping(value="/authCodeToOpenid",method={RequestMethod.GET,RequestMethod.POST})
	public String directFinish(HttpServletRequest hsq,String code,Integer totalMoney) throws Exception
	{
		XStream xStream = new XStream();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
       
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        System.out.println("result: "+result);//得到 openid  session_key uunionid
        /*
         * ==========================================================
         */
        //生成商户订单  调用unifiedorder得到 prepay_id 
        JSONObject json = new JSONObject();;
        try {
        	JSONObject json_test = JSONObject.parseObject(result);
        	String openid = json_test.getString("openid");
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("锦官商城");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setTotal_fee(1);
			order.setSpbill_create_ip("111.231.203.135");
			order.setNotify_url("http://localhost:80/payResult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);
			
			
			String result1 = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println("result1111: "+result1);
			xStream.alias("xml", OrderReturnInfo.class); 
//			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result1);
//			result1=result1.toString();
//			OrderReturnInfo returnInfo =XStreamTool.fromXML(result1,xStream);

			int slen=result1.lastIndexOf("<prepay_id><![CDATA[");
			int elen=result1.lastIndexOf("]]></prepay_id>");
			String prepay_id=result1.substring(slen+20, elen);
//			json.put("prepay_id", returnInfo.getPrepay_id());
			json.put("prepay_id", prepay_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        /*
         * ==========================================================
         */       
        //数据再次签名  返回
        try {
        	System.out.println("Json: "+json);
			String repay_id = json.getString("prepay_id");
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.getAppID());
			long time = System.currentTimeMillis()/1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setRepay_id("prepay_id="+repay_id);
			signInfo.setSignType("MD5");
			//数据签名
			String sign = Signature.getSign(signInfo);
			
			JSONObject json1 = new JSONObject();
			json1.put("timeStamp", signInfo.getTimeStamp());
			json1.put("nonceStr", signInfo.getNonceStr());
			json1.put("package", signInfo.getRepay_id());
			json1.put("signType", signInfo.getSignType());
			json1.put("appid", Configure.getAppID());
			json1.put("paySign", sign);
			return json1.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
	}
	
	
	
	
}
