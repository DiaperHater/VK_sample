package com.val.vk;

import com.val.vk.exceptions.DialogNullFieldException;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class DialogTest {

    private Dialog dialog = new Dialog();

    @Test
    void allGetters() throws NoSuchFieldException, IllegalAccessException {
        assertThrows(DialogNullFieldException.class, ()->dialog.getUserId());
        assertThrows(DialogNullFieldException.class, ()->dialog.getBody());
        assertThrows(DialogNullFieldException.class, ()->dialog.getTime());

        Field messageField = dialog.getClass().getDeclaredField("message");
        messageField.setAccessible(true);
        messageField.set(dialog, new Message());

        assertEquals(0, dialog.getUserId());
        assertEquals(null, dialog.getBody());
        assertEquals(0, dialog.getTime());
    }

}