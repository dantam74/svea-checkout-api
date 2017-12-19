package se.sveaekonomi.webpay.checkout.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.checkout.CheckoutApiClientRF;
import se.sveaekonomi.webpay.checkout.JsonUtil;
import se.sveaekonomi.webpay.checkout.entity.Address;
import se.sveaekonomi.webpay.checkout.entity.Cart;
import se.sveaekonomi.webpay.checkout.entity.CartItem;
import se.sveaekonomi.webpay.checkout.entity.Customer;
import se.sveaekonomi.webpay.checkout.entity.MerchantSettings;
import se.sveaekonomi.webpay.checkout.entity.Order;

public class TestCreateOrder {

	// private String CONFIG_FILE = "config-test-mytestfile.xml";
	// private String CONFIG_FILE = "config-test-local-jetty.xml";
	private String CONFIG_FILE = "config-test.xml";
	
	@Before
	public void setUp() throws Exception {
			
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateOrder() {

		CheckoutApiClientRF client = new CheckoutApiClientRF();
		try {
		
			client.loadConfig(CONFIG_FILE);
			client.init();
			
			MerchantSettings ms = new MerchantSettings();
			ms.setTermsUri("http://webshop.se/terms");
			ms.setCheckoutUri("http://webshop.se/checkout");
			ms.setConfirmationUri("http://webshop.se/confirm");
			ms.setPushUri("https://ssl.notima.se/svea/checkoutpush/{checkout.order.uri}");
			
			Cart cart = new Cart();
			CartItem item = new CartItem();
			item.setArticleNumber("100");
			item.setName("Snygga skor");
			item.setQuantity(1);
			item.setUnit("par");
			item.setUnitPrice(10000);
			item.setVatPercent(2500);
			cart.addItem(item);
			
			String locale = "sv-SE";
			String currency = "SEK";
			String countryCode = "SE";
			String clientOrderNumber = "100002";
			
			Order order = new Order();
			order.setMerchantSettings(ms);
			order.setCart(cart);
			order.setLocale(locale);
			order.setCurrency(currency);
			order.setCountryCode(countryCode);
			order.setClientOrderNumber(clientOrderNumber);

			Customer customer = new Customer();
			customer.setNationalId("194608142222");
			
			order.setCustomer(customer);
			
			Address address = new Address();
			address.setFullName("Therese Persson");
			address.setFirstName("Therese");
			address.setLastName("Persson");
			address.setStreetAddress("Testgatan 1");
			address.setCoAddress("c/o Eriksson, Erik");
			address.setPostalCode("99999");
			address.setCity("Stan");
			address.setCountryCode("SE");
			
			order.setShippingAddress(address);
			order.setBillingAddress(address);
			
			String result = client.createOrder(order);

			
			System.out.println("Resultat: " + result);
			
			if (result!=null) {
			
				Order resultOrder = JsonUtil.gson.fromJson(result, Order.class);
				System.out.println(resultOrder.toString());
				
			}
			
			/*
			System.out.println("Merchant order ID: " + order.getMerchantOrderId());
			System.out.println("Order has " + order.getOrderRows().size() + " lines."); */
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
