package com.val.vk;

public class UserList {

    public User[] response = null;
    Error error;

    public String getUsersFullName(int i) {

        if(i < 0){
            throw new IllegalArgumentException();
        }

        return response[i].first_name
                + " "
                + response[i].last_name;
    }

}
