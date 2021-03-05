package com.urise.webapp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        AtomicInteger i = new AtomicInteger(1);
        return Arrays.stream(values).
                distinct().
                boxed().
                sorted(Comparator.reverseOrder()).
                reduce(0, (x, y) -> {
                    x += y * i.get();
                    i.updateAndGet(v -> v * 10);
                    return x;
                });
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
//        boolean isEven = integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0;
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(n -> isEven(sum) && !isEven(n) || !isEven(sum) && isEven(n)).collect(Collectors.toList());
//        return integers.stream().filter(isEven ? n -> n % 2 != 0 : n -> n % 2 == 0).collect(Collectors.toList());
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }
}
