package com.example.correctiontodolist.exception;

import com.example.correctiontodolist.util.Constant;

public class TodoNotFoundException extends Exception {
    public TodoNotFoundException() {
        super(Constant.NOTFOUND_MESSAGE_ERROR);
    }
}
