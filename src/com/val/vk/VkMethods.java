package com.val.vk;

import com.google.gson.Gson;
import com.val.vk.exceptions.EmptyParamException;
import com.val.vk.exceptions.SendMessageErrorException;
import com.val.vk.exceptions.ServerRespondErrorException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class VkMethods {
    private String access_token;
    private String user_id;
    static private String version;
    static private String prefix;
    private HttpsGetter httpsGetter;

    public VkMethods(HttpsGetter httpsGetter, String token, String id){
        this.httpsGetter = httpsGetter;
        access_token = "access_token="+token+"&";
        user_id = "user_id="+id+"&";
        version = "v=5.52&";
        prefix = "https://api.vk.com/method/";
    }

    public UserList getUsers(int ids[]) throws EmptyParamException {

        if (ids.length == 0){
            throw new EmptyParamException("ids[]");
        }
        StringBuilder sb = new StringBuilder();
        for (int i : ids){
            sb.append(Integer.toString(i)+",");
        }
        sb.deleteCharAt(sb.length()-1);

        String url = prefix+"users.get?"+version+"user_ids="+sb;
        UserList users = null;
        try {
            users = new Gson().fromJson(httpsGetter.get(url), UserList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public Dialog[] getDialogs(int count) {

        if (count > 200){
            count = 200;
        }else if (count < 1){
            count = 1;
        }

        String uri = prefix
                + "messages.getDialogs?"
                + access_token
                + version
                + "count="+count;

        String jsonResp = null;
        try {
            jsonResp = httpsGetter.get(uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        GetDialogsResponse rsp = gson.fromJson(jsonResp, GetDialogsResponse.class);

        if (rsp.error != null){
            throw new ServerRespondErrorException(rsp.error.error_msg);
        }


        return rsp.response.items;
    }
    private class GetDialogsResponse{
        Response response;
        Error error;

        class Response{
            int count;
            Dialog[] items;
        }
    }

    public MessageList getMessageHistory(int offset, int count, int uId) throws IOException{

        if (offset < 0 ){
            offset = 0;
        }
        if (count > 200){
            count = 200;
        }else if(count < 1){
            count = 1;
        }

        String url = prefix+"messages.getHistory?"+access_token+version+"offset="+offset+"&count="+count+"&user_id="+uId;
        String get = httpsGetter.get(url);
        GetMessageHistoryResponse resp = new Gson().fromJson(get, GetMessageHistoryResponse.class);

        if(resp.error != null){
            return new MessageList();
        }

        return resp.response;
    }
    private class GetMessageHistoryResponse {
        MessageList response;
        Error error;
    }

    public void sendMessage(int toId, String message) throws SendMessageErrorException, IOException {
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        String url = prefix+"messages.send?"+access_token+version+"user_id="+toId+"&message="+message;
        String resp = httpsGetter.get(url);
        SendMessageResponse smr = new Gson().fromJson(resp, SendMessageResponse.class);

        if(smr.error != null){
            throw new SendMessageErrorException(smr.error.error_code + "\n" +smr.error.error_msg);
        }
    }
    private class SendMessageResponse{
        Error error;
        int response;

        class Error{
            int error_code;
            String error_msg;
        }
    }


}
