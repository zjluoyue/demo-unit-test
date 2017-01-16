package com.zjluoyue.arrays;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Jia on 2017/1/14.
 */
public class StreamArrays {
    public static void main(String[] args) {
        //流的构造方法
        // Individual values
        Stream st = Stream.of("a", "b", "c");
        //Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        st = Stream.of(strArray);
        st = Arrays.stream(strArray);
        //Collections
        List<String> strList = Arrays.asList(strArray);
        st = strList.stream();

        //数值流的构造
        /*IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 5).forEach(System.out::println);
        IntStream.rangeClosed(1, 6).forEach(System.out::println);*/

        //流的转换
/*        //Array
        String[] strArray1 = (String[]) st.toArray(String[]::new);
        //Collection
        List<String> strList1 = (List<String>) st.collect(Collectors.toList());
        List<String> strList2 = (List<String>) st.collect(Collectors.toCollection(ArrayList::new));
        Set strSet = (Set) st.collect(Collectors.toSet());
        Stack strStack = (Stack) st.collect(Collectors.toCollection(Stack::new));
        //String
        String str = st.collect(Collectors.joining()).toString();*/

        //流的操作
        // Intermediate: map, filter, distinct, sorted, peek, limit, skip, parallel, sequential,
        // unordered
        // Terminal: forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator
        // Short-circuiting：anyMatch、allMatch、noneMatch, findFirst、findAny、limit
        // 大小写转换
        List<String> out = strList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // 平方数
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        // 一对多
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outStream = inputStream.flatMap((childList) -> childList.stream());

        // peek: 对每个元素操作返回新的Stream
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        /**
         *  reduce
         *  这个方法的主要作用是把 Stream 元素组合起来。
         *  它提供一个起始值（种子），然后依照运算规则（BinaryOperator），
         *  和前面 Stream的第一个、第二个、第 n 个元素组合
         */
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);


        // 创造流 Stream.generate()
        Random seed = new Random(57);
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);

        // Stream.iterate()
        Stream.iterate(0, n -> n + 3).limit(10)
                .forEach(x -> System.out.print(x + " "));
    }

}
