package com.techie.springconnect.exceptions;

import org.springframework.mail.MailException;

public class SpringConnectException extends RuntimeException {
    public SpringConnectException(String s, Exception e) {
        super(s,e);
    }

    public SpringConnectException(String s) {
        super(s);
    }
}
