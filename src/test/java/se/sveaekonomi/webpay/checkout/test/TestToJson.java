package se.sveaekonomi.webpay.checkout.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.checkout.CheckoutUtil;
import se.sveaekonomi.webpay.checkout.entity.Address;
import se.sveaekonomi.webpay.checkout.entity.Cart;
import se.sveaekonomi.webpay.checkout.entity.CartItem;
import se.sveaekonomi.webpay.checkout.entity.Customer;
import se.sveaekonomi.webpay.checkout.entity.MerchantSettings;
import se.sveaekonomi.webpay.checkout.entity.Order;

public class TestToJson {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Order order = new Order();
		MerchantSettings settings = new MerchantSettings();
		settings.setCheckoutUri("https://test");
		order.setMerchantSettings(settings);
		Cart cart = new Cart();
		order.setCart(cart);
		CartItem item = new CartItem();
		
		item.setArticleNumber("1000000");
		item.setName("Desktop Computer");
		item.setQuantity(100);
		item.setDiscountPercent(0);
		item.setVatPercent(2500);
		item.setUnitPrice(1000);
		item.setUnit("st");
		
		cart.addItem(item);
		
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
		
		order.setLocale("sv");
		order.setCurrency("SEK");
		order.setCountryCode("SE");
		order.setClientOrderNumber("56944050");
		
		
		String json = CheckoutUtil.gson.toJson(order);
		System.out.println(json);
	}

}
