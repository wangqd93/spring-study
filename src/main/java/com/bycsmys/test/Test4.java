package com.bycsmys.test;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Test4 {


    public static void main(String[] args) {

        String s = "订单 123 456 789";

        Pattern pattern = Pattern.compile("订单 [0-9]* [0-9]* [0-9]*");
        Matcher matcher = pattern.matcher(s);

        System.out.println("matcher.find() = " + matcher.find());

        Stream.of(s.split(" ")).filter(StringUtils::isNoneBlank)
                .filter(i -> !Objects.equals("订单",i)).forEach(System.out::println);

    }
}
