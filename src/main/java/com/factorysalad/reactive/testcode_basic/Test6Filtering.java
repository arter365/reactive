package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import com.factorysalad.reactive.model.Person2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Test6Filtering {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test6Filtering t6 = new Test6Filtering();
        t6.filter();
        t6.numberDistinct();
        t6.stringDistinct1();
        t6.stringDistinct2();
        t6.take();
        t6.takeLast();
        t6.skip();
        t6.skipLast();
    }

    public void filter() {
        System.out.println("filter start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .filter(p -> p.getAge() > 20)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("filter end -----");
    }

    public void numberDistinct() {
        System.out.println("numberFilter start -----");
        Flux.fromIterable(List.of(1,1,2,2))
                .distinct()
                .subscribe(p -> log.info(p.toString()));
        System.out.println("numberFilter end -----");
    }

    public void stringDistinct1() {
        System.out.println("distinct1 start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(1, "강감찬", 19));   // 모든 내용을 동일하게 해도 중복이 제거되지 않는다.
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .distinct()
                .subscribe(p -> log.info(p.toString()));
        System.out.println("distinct1 end -----");
    }

    public void stringDistinct2() {
        System.out.println("distinct2 start -----");
        List<Person2> personList = new ArrayList<>();
        personList.add(new Person2(1, "강감찬", 19));
        personList.add(new Person2(1, "강감찬", 19));   // Person2 class의 equals를 원하는 대로 재정의 하면 된다.
        personList.add(new Person2(3, "이순신", 30));

        Flux.fromIterable(personList)
                .distinct()
                .subscribe(p -> log.info(p.toString()));
        System.out.println("distinct2 end -----");
    }

    public void take() {
        System.out.println("take start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));
        Flux.fromIterable(personList)
                .take(1)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("take end -----");
    }

    public void takeLast() {
        System.out.println("takeLast start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));
        Flux.fromIterable(personList)
                .takeLast(2)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("takeLast end -----");
    }

    public void skip() {
        System.out.println("skip start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));
        Flux.fromIterable(personList)
                .skip(1)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("skip end -----");
    }

    public void skipLast() {
        System.out.println("skipLast start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));
        Flux.fromIterable(personList)
                .skipLast(1)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("skipLast end -----");
    }

}
