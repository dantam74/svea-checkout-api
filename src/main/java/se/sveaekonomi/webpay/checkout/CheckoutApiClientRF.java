package se.sveaekonomi.webpay.checkout;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import okhttp3.ResponseBody;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.sveaekonomi.webpay.checkout.entity.Order;

public class CheckoutApiClientRF {

	public static Logger clientLog = org.slf4j.LoggerFactory.getLogger("CheckoutApiClientRF");

	public static DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
	
	private String merchantId;
	private String secretWord;
	private String serverName;
	
	
	private Configurations configs = new Configurations();
	
	private	Retrofit	retroFit = null;
	private CheckoutApiService service = null;

	
	public void loadConfig(String configfile) throws Exception {

		URL url = null;
		
		// Try absolute path first
		File cf = new File(configfile);
		if (!cf.exists()) {
			// Try read as resource
			url = ClassLoader.getSystemResource(configfile);
		} else {
			url = new URL(cf.getAbsolutePath());
		}

		if (url==null) {
			System.out.println("Can't find configfile: " + configfile);
			System.exit(-1);
		}
		
		XMLConfiguration fc = configs.xml(url);
		
		serverName = fc.getString("server");
		merchantId = fc.getString("merchantId");
		secretWord = fc.getString("secretWord");
		
	}	
	
	/**
	 * Initializes client with current values. loadConfig must have been called before.
	 * 
	 */
	public void init() {
		init(serverName, merchantId, secretWord);
	}
	
	/**
	 * Initializes client
	 */
	public void init(String serverName, String merchantId, String secretWord) {

		this.serverName = serverName;
		this.merchantId = merchantId;
		this.secretWord = secretWord;
		
		// Disable SNI to prevent SSL-name problem
		// System.setProperty("jsse.enableSNIExtension", "false");
		
		ScalarsConverterFactory converter = ScalarsConverterFactory.create();
		
		retroFit = new Retrofit.Builder().baseUrl(this.serverName)
				.addConverterFactory(converter)
				.build();

		service = retroFit.create(CheckoutApiService.class);		
		
	}
	
	/**
	 * Creates an order 
	 * @param ms
	 * @param cart
	 * @param locale
	 * @param currency
	 * @param countryCode
	 * @param clientOrderNumber
	 * @return
	 * @throws Exception
	 */
	public String createOrder(
			Order order
			) throws Exception {
		
		StringBuffer body = new StringBuffer();
		body.append(order.toString());

		String ts = CheckoutUtil.getTimestampStr();
		String auth = CheckoutUtil.calculateAuthHeader(merchantId, body.toString(), secretWord, ts);
		
		Call<ResponseBody> call = service.createOrder(auth, ts, 
				body.toString());

		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null) {
			clientLog.debug(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			resultMsg = response.body().string();
			clientLog.debug(response.message());
			clientLog.debug(resultMsg);
			clientLog.debug(response.raw().toString());
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return resultMsg;
		} else {
			return null;
		}
		
	}
	
	
	public String getOrder(Long orderId) throws Exception {

		String ts = CheckoutUtil.getTimestampStr();
		String auth = CheckoutUtil.calculateAuthHeader(merchantId, "", secretWord, ts);
		
		Call<ResponseBody> call = service.getOrder(auth, ts, orderId.toString());
		
		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null) {
			clientLog.debug(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			resultMsg = response.body().string();
			clientLog.debug(response.message());
			clientLog.debug(resultMsg);
			clientLog.debug(response.raw().toString());
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return resultMsg;
		} else {
			return null;
		}
	}
	
	public Order getOrderFromString(String orderStr) {
		Order order = JsonUtil.gson.fromJson(orderStr, Order.class);
		return order;
	}
	
	
}
