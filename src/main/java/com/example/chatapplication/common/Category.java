package com.example.chatapplication.common;

public enum Category {
    ;

    public enum SocketService{
        chat("/chat");

        public final String name;
        SocketService(String name){
            this.name=name;
        }
    };

    public enum Role{
        STUDENT,
        ADMIN,
        SUBJECT_TEACHER
    };

    public enum ErrorCodeEnum {
        INTERNAL_SERVER_ERROR,
        URI_NOT_FOUND,
        INVALID_PARAMETER,
        INVALID_FORMAT;

        private ErrorCodeEnum() {
        }
    };

    public enum RegisterStatus{
        REGISTER_SUCCESS,
        REGISTER_FAIL
    };
    public enum Status{
        SUCCESS,
        FAIL
    }


}
