
package com.rasinsky.jaxwsdemo;

import javax.jws.WebService;

@WebService(endpointInterface = "com.rasinsky.jaxwsdemo.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

