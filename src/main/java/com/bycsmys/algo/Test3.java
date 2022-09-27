package com.bycsmys.algo;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Test3 {


    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("tmmzuxt") == 5);
        System.out.println(lengthOfLongestSubstring("abcabcbb") == 3);
        System.out.println(lengthOfLongestSubstring(" ") == 1);
        System.out.println(lengthOfLongestSubstring("pwwkew") == 3);

        System.out.println(lengthOfLongestSubstring("aabaab!bb") == 3);
    }

    public static int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> indexMap = new HashMap<>();
        int maxlength = 0;
        int beginIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);

            if (indexMap.containsKey(curChar) && indexMap.get(curChar) > beginIndex) {
                beginIndex = indexMap.get(curChar);
                maxlength = Math.max(maxlength, i - beginIndex);
                indexMap.put(curChar, i);
                continue;
            }

            maxlength = Math.max(maxlength,i - beginIndex );
            indexMap.put(curChar, i);
        }

        return maxlength;
    }
}
