package com.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: dengKai
 * @Date: 2021-08-30-11-51
 * @Description: java stream流的练习
 */

public class StreamTest {
    public static void main(String[] args) {
        testSort();
    }


    /**
     * 流的过滤
     * filter
     */
    private static void testFilter() {
        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        Stream<Integer> stream = list.stream();
        stream.filter(x -> x > 7).forEach(System.out::println);
    }

    /**
     * 流的遍历
     * foreach/find/match
     */
    private static void testForeach() {
        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        list.stream()
                .filter(number -> number > 6)
                .forEach(number -> System.out.println(number));
    }

    /**
     * 流的计算
     * 计数：count
     * 平均值：averagingInt、averagingLong、averagingDouble
     * 最值：maxBy、minBy
     * 求和：summingInt、summingLong、summingDouble
     * 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
     */
    private static void testCalculate() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    /**
     * 流的映射
     * map/flatMap
     */
    private static void testMap() {
        String[] list = {"abcd", "bcdd", "defde", "fTr"};
        List<String> stringList = Arrays
                .stream(list)
                .sequential()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(stringList.toString());
    }

    /**
     * 流的规约
     * 把一个流缩减为一个值
     */
    private static void testReduce() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        Integer answer = list.stream()
                .reduce((x, y) -> {
                    return x + y;
                }).get();
        System.out.println(answer);
    }

    /**
     * 流的收集
     * toList/toSet/toMap
     */
    private static void testList() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        List<Integer> integerList = list.stream()
                .map(x -> x * 5 + 1)
                .collect(Collectors.toList());
        System.out.println(integerList);
    }

    /**
     * 流的分组
     */
    private static void testGroup() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);

        // 获取小于8的分组
        Map<Boolean, List<Integer>> collect = list.stream()
                .collect(Collectors.groupingBy(number -> number < 8));

        // 获取大于2小于8的分组
        Map<Boolean, Map<Boolean, List<Integer>>> collect1 = list.stream()
                .collect(Collectors.groupingBy(number -> number < 8,
                        Collectors.groupingBy(number -> number > 2)));

        List<Integer> integers = collect1.get(true).get(true);
    }

    /**
     * 流的接合
     */
    private static void testJoin() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        String collect = list.stream().collect(Collectors.joining("-"));
        System.out.println(collect);
    }

    /**
     * 流的排序
     */
    private static void testSort() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
        personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 8800, 26, "male", "New York"));
        personList.add(new Person("Alisa", 9000, 26, "female", "New York"));

        // 按工资升序排序（自然排序）
        List<String> newList = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary)).map(Person::getName)
                .collect(Collectors.toList());
        // 按工资倒序排序
        List<String> newList2 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());
        // 先按工资再按年龄升序排序
        List<String> newList3 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary().equals(p2.getSalary())) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());

        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资升序再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄降序排序：" + newList4);
    }

    /**
     * 流的组合
     */

    public static void testConcat() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);

        // concat:合并两个流 distinct：去重
        List<String> newList = Stream
                .concat(stream1, stream2)
                .distinct()
                .collect(Collectors.toList());

        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream
                .iterate(1, x -> x + 2)
                .limit(10)
                .collect(Collectors.toList());

        // skip：跳过前n个数据
        List<Integer> collect2 = Stream
                .iterate(1, x -> x + 2)
                .skip(1).limit(5)
                .collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);
    }
}
