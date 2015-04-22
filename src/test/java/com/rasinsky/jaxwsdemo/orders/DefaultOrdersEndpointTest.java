package com.rasinsky.jaxwsdemo.orders;

import static org.junit.Assert.assertTrue;

import javax.xml.ws.soap.SOAPFaultException;

import com.rasinsky.jaxws_demo.schema.order.ObjectFactory;
import com.rasinsky.jaxws_demo.schema.order.OrderInquiryResponseType;
import com.rasinsky.jaxws_demo.schema.order.OrderInquiryType;
import com.rasinsky.jaxws_demo.service.orders.Orders;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-beans.xml"})
public class DefaultOrdersEndpointTest {

	private Orders orderService;
	private OrderInquiryType orderInquiryType;

	@Autowired
	private JaxWsProxyFactoryBean testOrdersClient;

	@Before
	public void setUp() throws Exception {
		orderService = testOrdersClient.create(Orders.class);
		ObjectFactory factory = new ObjectFactory();
		orderInquiryType = factory.createOrderInquiryType();
		orderInquiryType.setAccountId(999);
		orderInquiryType.setEan13(1234567890123L);
		orderInquiryType.setOrderQuantity(4);
		orderInquiryType.setUniqueOrderId(12345);
	}

	@Test
	public void testProcessOrderPlacementSuccess() throws Exception {
		OrderInquiryResponseType response = orderService.processOrderPlacement(orderInquiryType);
		assertTrue(response.getAccount().getAccountId() == 999);
	}

	@Test(expected = SOAPFaultException.class)
	public void testProcessOrderPlacementFailureInvalidParameter() throws Exception {
		orderService.processOrderPlacement(null);
	}
}