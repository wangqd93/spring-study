package com.bycsmys.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test8 {

    public static void main(String[] args) {
        // 优先级高 -> 低
        List<Integer> list = Arrays.asList(4, 5, 2, 3, 1);

        List<Integer> collect = Stream.of(1, 2, 3, 4, 5, 6, 7)
                .filter(i -> list.contains(i))
                .sorted(Comparator.comparing(Function.identity(), (a, b) -> list.indexOf(a) - list.indexOf(b)))
                .collect(Collectors.toList());

        collect.stream().forEach(System.out::println);
    }
}
