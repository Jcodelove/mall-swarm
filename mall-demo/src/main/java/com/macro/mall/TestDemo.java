package com.macro.mall;

import com.alibaba.nacos.common.util.Md5Utils;

import java.nio.charset.StandardCharsets;

public class TestDemo {
    public static void main(String[] args) {
        String md5 = Md5Utils.getMD5("1234".getBytes(StandardCharsets.UTF_8));
        System.out.println(md5);
    }
}
