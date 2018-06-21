package com.val.vk;

import com.val.vk.exceptions.ProtocolIsNotHTTPSException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class HttpsGetter {

    public String get (String address) throws IOException{

        URL url = new URL(address);

        if( !url.getProtocol().equals("https") ){
            throw new ProtocolIsNotHTTPSException();
        }


        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ( (line = reader.readLine()) != null ){
            sb.append(line+"\n");
        }

        return sb.toString();

    }
}
