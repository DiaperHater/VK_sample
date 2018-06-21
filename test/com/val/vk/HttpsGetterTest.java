package com.val.vk;

import com.val.vk.exceptions.ProtocolIsNotHTTPSException;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class HttpsGetterTest {
    HttpsGetter getter;

    @Before
    public void before(){
        getter = new HttpsGetter();
    }


    @Test(expected = ProtocolIsNotHTTPSException.class)
    public void get_WrongProtocolInUrl_ProtocolIsNotHTTPSException() throws IOException {
        getter.get("http://google.com");
    }

    @Test(expected = MalformedURLException.class)
    public void get_NoProtocolInUrl_MalformedURLException() throws IOException {
        getter.get("google.com");
    }

    @Test(expected = MalformedURLException.class)
    public void get_ShitInURL_MalformedURLException() throws IOException {
        getter.get("goog");
    }

}