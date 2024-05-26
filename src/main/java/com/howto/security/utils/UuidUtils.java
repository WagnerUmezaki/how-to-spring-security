package com.howto.security.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidUtils {

    public String randomUUUID() {
        return UUID.randomUUID().toString();
    }
}
