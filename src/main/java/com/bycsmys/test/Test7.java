package com.bycsmys.test;

public class Test7 {


    public static void main(String[] args) {
        String disfUrl = "disf!strategic-manhattan-oppenheimer-product_center/spu/queryOne?spuId=";

        String disfName = disfUrl.split("/", 2)[0];
        String host = getHost(disfName);

        String path = disfUrl.split("/", 2)[1];

        System.out.println(disfName);

        System.out.println(host + "/" + path);

    }

    private static String getHost(String disfName) {
        return "http://10.96.93.138:8191";
    }
}
