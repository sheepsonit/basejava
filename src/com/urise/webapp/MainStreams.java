package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((x, y) -> Integer.parseInt(Integer.toString(x).concat(Integer.toString(y)))).getAsInt();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().reduce(Integer::sum).get() % 2 == 0) {
            integers.removeIf(n -> n % 2 == 0);
        } else {
            integers.removeIf(n -> n % 2 != 0);
        }
        return integers;
    }
}
