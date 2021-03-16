package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int remainder = integers.stream().reduce(0, Integer::sum) % 2;
        return integers.stream().filter(n -> n % 2 != remainder).collect(Collectors.toList());
    }
}
