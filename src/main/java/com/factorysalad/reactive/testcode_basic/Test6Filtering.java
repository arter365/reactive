package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
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

    public void stringDistinct() {
        System.out.println("distinct start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(1, "홍길동", 23));   // id를 1로 수정
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .distinct()
                .subscribe(p -> log.info(p.toString()));
        System.out.println("distinct end -----");
    }

}
