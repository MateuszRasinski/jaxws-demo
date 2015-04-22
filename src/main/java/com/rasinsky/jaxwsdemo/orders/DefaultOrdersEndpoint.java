package com.rasinsky.jaxwsdemo.orders;

import javax.jws.WebService;

import com.rasinsky.jaxws_demo.schema.order.OrderInquiryResponseType;
import com.rasinsky.jaxws_demo.schema.order.OrderInquiryType;
import com.rasinsky.jaxws_demo.service.orders.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@WebService(portName = "OrdersSOAP", serviceName = "Orders",
		endpointInterface = "com.rasinsky.jaxws_demo.service.orders.Orders",
		targetNamespace = "http://www.rasinsky.com/jax-ws/service/Orders/")
@Component
public class DefaultOrdersEndpoint implements Orders {

	@Autowired
	private OrderService orderService;

	@Override
	public OrderInquiryResponseType processOrderPlacement(OrderInquiryType orderInquiry) {
		return orderService.processOrder(orderInquiry.getUniqueOrderId(), orderInquiry.getOrderQuantity(),
				orderInquiry.getAccountId(), orderInquiry.getEan13());
	}
}
