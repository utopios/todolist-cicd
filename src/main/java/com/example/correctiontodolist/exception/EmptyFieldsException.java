package com.example.correctiontodolist.exception;

import com.example.correctiontodolist.util.Constant;

public class EmptyFieldsException extends Exception {

    private EmptyFieldsException(String message) {
        super(message);
    }
    public static EmptyFieldsException withFields(String ...fields) {
        String messageError = Constant.EMPTY_FIELD_MESSAGE_ERROR;
        for(String field : fields) {
            messageError += field;
        }
        return new EmptyFieldsException(messageError);
    }
}
