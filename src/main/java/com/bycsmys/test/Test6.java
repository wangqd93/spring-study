package com.bycsmys.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test6 {

    public static void main(String[] args) {
        List<Integer> collect = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());

        int subOrderBatchSize = 18;

        int pageSize = collect.size() % subOrderBatchSize == 0 ?
                collect.size() / subOrderBatchSize : collect.size() / subOrderBatchSize + 1;

        for (int i = 0; i < pageSize; i++) {
            int startIndex = subOrderBatchSize * i;
            int endIndex = subOrderBatchSize * (i + 1) > collect.size() ? collect.size() : subOrderBatchSize * (i + 1);

            String s = collect.subList(startIndex,endIndex).stream().map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(s);
            System.out.println("---");

        }


    }
}
