package se.sveaekonomi.webpay.checkout.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.checkout.CheckoutApiClientRF;

public class TestCheckoutApi {

	@Before
	public void setUp() throws Exception {
			
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetOrder() {

		CheckoutApiClientRF client = new CheckoutApiClientRF();
		try {
		
			client.loadConfig("config-test.xml");
			client.init();
			
			String result = client.getOrder(154444L);
			
			System.out.println(result);
			
			/*
			System.out.println("Merchant order ID: " + order.getMerchantOrderId());
			System.out.println("Order has " + order.getOrderRows().size() + " lines."); */
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
