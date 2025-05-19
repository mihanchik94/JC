package ru.itk.stream.generator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        //1. Создайте список заказов с разными продуктами и их стоимостями
        List<Order> firstTask = Stream.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0)
                )
                .toList();

        //2. Группируйте заказы по продуктам.
        Map<String, List<Order>> secondTask = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));


        //3. Для каждого продукта найдите общую стоимость всех заказов.
        Map<String, Double> thirdTask = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProduct,
                        Collectors.summingDouble(Order::getCost)
                ));


        //4. Отсортируйте продукты по убыванию общей стоимости.
        List<Map.Entry<String, Double>> fourthTask = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProduct,
                        Collectors.summingDouble(Order::getCost)
                )).entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .toList();


        //5. Выберите три самых дорогих продукта (не заказа).
        List<String> fifthTask = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .limit(3)
                .map(order -> order.getProduct() + "=" + order.getCost())
                .toList();

        //6. Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
        System.out.println("3 самых дорогих продукта: " + fifthTask); //это список трех самых дорогих продуктов
        double sixthTask = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .limit(3)
                .mapToDouble(Order::getCost)
                .sum();
        System.out.println("Общая стоимость трех самых дорогих продуктов: " + sixthTask);




    }
}
