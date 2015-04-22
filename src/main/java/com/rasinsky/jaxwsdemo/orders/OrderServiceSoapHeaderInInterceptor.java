package com.rasinsky.jaxwsdemo.orders;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

@Component
public class OrderServiceSoapHeaderInInterceptor extends AbstractSoapInterceptor {

	public OrderServiceSoapHeaderInInterceptor() {
		super(Phase.USER_PROTOCOL);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		QName qname = new QName("http://www.rasinsky.com/jaxws-demo/service/Orders/", "apikey");
		SoapHeader header = (SoapHeader) message.getHeader(qname);
		Element element = (Element) header.getObject();
		String apikey = element.getTextContent();
		System.err.println("API Key = " + apikey);
	}
}
