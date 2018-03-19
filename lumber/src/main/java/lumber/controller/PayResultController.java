package lumber.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lumber.common.StreamUtil;


/*
 * 支付回调的接口  用于接受微信支付返回的结果
 */
@Controller
public class PayResultController {
	@RequestMapping(value="/payresult")
	public String payResult(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String reqParams = StreamUtil.read(request.getInputStream());
		StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		return sb.toString();
	}
}
