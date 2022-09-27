package com.bycsmys.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test118 {


    public static void main(String[] args) {
        System.out.println(generate(1));
        System.out.println(generate(2));
        System.out.println(generate(3));
        System.out.println(generate(4));

    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();

        if (numRows >= 1) {
            list.add(Arrays.asList(1));
        }
        if (numRows >= 2) {
            list.add(Arrays.asList(1, 1));
        }

        if (numRows >= 3) {
            for (int i = 3; i <= numRows; i++) {
                List<Integer> subList = new ArrayList<>();
                List<Integer> lastList = list.get(list.size() -1);
                for (int j = 0; j < i; j++) {
                    int start = (j - 1) < 0 ? 0 : lastList.get(j-1);
                    int end = j  >= lastList.size() ? 0 : lastList.get(j);
                    subList.add(start + end);
                }
                list.add(subList);
            }

        }
        return list;
    }
}
