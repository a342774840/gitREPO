package lumber.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value="xml")
public class OrderReturnInfo {
	@XStreamAlias("return_code")
    private String return_code;
	@XStreamAlias("return_msg")
    private String return_msg;
	@XStreamAlias("appid")
    private String appid;
	@XStreamAlias("mch_id")
    private String mch_id;
	@XStreamAlias("nonce_str")
    private String nonce_str;
	@XStreamAlias("sign")
    private String sign;
	@XStreamAlias("result_code")
    private String result_code;
	@XStreamAlias("prepay_id")
    private String prepay_id;
	@XStreamAlias("trade_type")
    private String trade_type;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
    
}
