package se.sveaekonomi.webpay.checkout.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.checkout.CheckoutApiClientRF;
import se.sveaekonomi.webpay.checkout.entity.Order;

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
		
			client.loadConfig("config-test-activeapparel.xml");
			client.init();
			
			String result = client.getOrder(348001L);
			
			if (result!=null) {
				Order o = client.getOrderFromString(result);
				System.out.println("Checkout order nr: " + o.getOrderId());
				System.out.println("Order klient: " + o.getClientOrderNumber());
			}
			
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
