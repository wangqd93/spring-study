package com.bycsmys.test;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.Map;

public class Test5 {

    private static final String ERROR_MESSAGE = "@${userName} \n原始指令: ${command} \n错误原因: ${errorMsg}";


    public static void main(String[] args) {

        System.out.println(appendErrorMessage("wangqd", "订单 123", "查询失败"));

    }


    public static String appendErrorMessage(String userName, String command, String errorMsg) {
        Map<String, String> param = ImmutableMap.of("userName", userName, "command", command, "errorMsg", errorMsg);


        StrSubstitutor strSubstitutor = new StrSubstitutor(param);

        return strSubstitutor.replace(ERROR_MESSAGE);
    }

}

