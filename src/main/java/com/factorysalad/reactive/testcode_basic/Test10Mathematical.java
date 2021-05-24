package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test10Mathematical {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test10Mathematical t10 = new Test10Mathematical();
        t10.average();
        t10.count();
        t10.min();
        t10.sum();
        t10.summarizing();
    }

    public void average() {
        System.out.println("average start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .collect(Collectors.averagingInt(Person::getAge))
                .subscribe(p -> log.info(p.toString()));
        System.out.println("average end -----");
    }

    public void count() {
        System.out.println("count start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .count()
                .subscribe(x -> log.info("수량 : " + x));
        System.out.println("count end -----");
    }

    public void min() {
        System.out.println("min start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .collect(Collectors.minBy(Comparator.comparing(Person::getAge)))
                .subscribe(p -> log.info("최소값을 가진 객체 : " + p.orElseThrow().toString()));  // Optional을 리턴한다.
        System.out.println("min end -----");
    }

    public void sum() {
        System.out.println("sum start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .collect(Collectors.summingInt(Person::getAge))
                .subscribe(x -> log.info("합계 : " + x));
        System.out.println("sum end -----");
    }

    public void summarizing() {
        System.out.println("summarizing start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .collect(Collectors.summarizingInt(Person::getAge))
                .subscribe(x -> log.info("Summarizing : " + x));    // {count=3, sum=72, min=19, average=24.000000, max=30}
        System.out.println("summarizing end -----");
    }

}
