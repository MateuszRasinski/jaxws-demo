package com.rasinsky.jaxwsdemo.orders;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import com.rasinsky.jaxws_demo.schema.order.AccountType;
import com.rasinsky.jaxws_demo.schema.order.BookType;
import com.rasinsky.jaxws_demo.schema.order.ObjectFactory;
import com.rasinsky.jaxws_demo.schema.order.OrderInquiryResponseType;
import com.rasinsky.jaxws_demo.schema.order.OrderItemType;
import com.rasinsky.jaxws_demo.schema.order.OrderStatusType;
import com.rasinsky.jaxws_demo.schema.order.OrderType;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

	private ObjectFactory factory = new ObjectFactory();

	@Override
	public OrderInquiryResponseType processOrder(int uniqueOrderId, int orderQuantity, int accountId, long ean13) {
		OrderInquiryResponseType response = factory.createOrderInquiryResponseType();

		AccountType account = createAccount(accountId);
		response.setAccount(account);

		OrderType order = processOrder(orderQuantity, ean13);
		response.setOrder(order);

		return response;
	}

	private OrderType processOrder(int orderQuantity, long ean13) {
		BookType book = findBook(ean13);
		OrderItemType orderItem = findOrderItem(orderQuantity, book);
		OrderType order = factory.createOrderType();
		order.setOrderStatus(OrderStatusType.READY);
		order.getOrderItems().add(orderItem);
		return order;
	}

	private OrderItemType findOrderItem(int orderQuantity, BookType book) {
		OrderItemType orderItem = factory.createOrderItemType();
		orderItem.setBook(book);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		try {
			XMLGregorianCalendar estimatedShippingDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			orderItem.setExpectedShippingDate(estimatedShippingDate);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		orderItem.setLineNumber(1);
		orderItem.setPrice(new BigDecimal(5));
		orderItem.setQuantityShipped(orderQuantity);
		return orderItem;
	}

	private BookType findBook(long ean13) {
		BookType book = factory.createBookType();
		book.setEan13(ean13);
		book.setTitle("A CXF Book");
		return book;
	}

	private AccountType createAccount(int accountId) {
		AccountType account = factory.createAccountType();
		account.setAccountId(accountId);
		return account;
	}
}
