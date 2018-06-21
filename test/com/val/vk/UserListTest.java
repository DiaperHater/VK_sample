package com.val.vk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserListTest {

    UserList userList;

    @BeforeEach
    void beforeEach(){
        userList = new UserList();

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.first_name = "Don";
        user1.last_name = "Peregnon";
        user2.first_name = "Roberto";
        user2.last_name = "Karlos";
        user3.first_name = "Hulio";
        user3.last_name = "Reviera";

        userList.response = new User[]{user1, user2, user3};
    }

    @Test
    void getUsersFullName_negativeIndex_IllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                ()->userList.getUsersFullName(-1));
    }

    @Test
    void getUsersFullName_indexBiggerThanArraySize_ArrayOutOfBoundException(){
        assertThrows(IndexOutOfBoundsException.class,
                ()->userList.getUsersFullName(1000));
    }

    @Test
    void getUserFullName_index_userNameInRightFormat(){
        assertEquals("Roberto Karlos", userList.getUsersFullName(1));
    }

    @Test
    void getUserFullName_index_userNameInRightFormat_2(){
        assertEquals("Hulio Reviera", userList.getUsersFullName(2));
    }

}