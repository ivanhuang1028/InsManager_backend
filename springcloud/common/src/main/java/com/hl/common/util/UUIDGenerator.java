package com.hl.common.util;

import java.util.UUID;

/**
 * Created by ivan.huang on 2016/7/29.
 */
public class UUIDGenerator {
    private UUIDGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
    
    public static void main(String[] args){
    	System.out.println(UUIDGenerator.generate());
    }
}
