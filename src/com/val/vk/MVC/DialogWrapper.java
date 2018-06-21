package com.val.vk.MVC;

import com.val.vk.*;
import com.val.vk.exceptions.EmptyParamException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class DialogWrapper {
    public final String user;
    public final int userId;
    public final LocalDateTime time;
    public final String body;

    private DialogWrapper(int id, String first_name,
                         String last_name, Dialog dialog) {

        user = first_name + " " + last_name;
        this.userId = id;
        time = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(dialog.getTime())
                , TimeZone.getDefault().toZoneId());
        body = dialog.getBody();
    }


    public static List<DialogWrapper> list
            (Dialog[] allDialogs, VkMethods vk) throws EmptyParamException {

        List<DialogWrapper> wrapperList = new ArrayList<>();
        List<Integer> listIds = new ArrayList<>();
        Map<Integer, Dialog> userDialogsMap = new LinkedHashMap<>();

        for (Dialog d : allDialogs){
            if (d.getUserId() < 0){
                continue; // group ids is negative. ignore them
            }
            listIds.add(d.getUserId());
            userDialogsMap.put(d.getUserId(), d);
        }

        int[] arrIds = new int[listIds.size()];
        for (int i = 0; i < listIds.size(); i++) {
            arrIds[i] = listIds.get(i);
        }
        UserList userList = vk.getUsers(arrIds);

       try{
           for (User u : userList.response){

               DialogWrapper wrapper =
                       new DialogWrapper(u.id, u.first_name,
                               u.last_name, userDialogsMap.get(u.id));
               wrapperList.add(wrapper);
           }

       }catch (NullPointerException e){
           e.printStackTrace();
       }
        return wrapperList;
    }
}
