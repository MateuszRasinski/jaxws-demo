package com.rasinsky.jaxwsdemo.orders;

import com.rasinsky.jaxws_demo.schema.order.OrderInquiryResponseType;

public interface OrderService {
	OrderInquiryResponseType processOrder(int uniqueOrderId, int orderQuantity, int accountId, long ean13);
}
