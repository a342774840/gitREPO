package lumber.common;

public class Configure {
	private static String key = "10e0cc2e07a41d5c535579360f996fb4";

	//小程序ID	
	private static String appID = "wx85907fdbec00a2f7";
	//商户号
	private static String mch_id = "1498192602";
	//
	private static String secret = "10e0cc2e07a41d5c535579360f996fb4";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
