package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test5Transformation {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test5Transformation t5 = new Test5Transformation();
        t5.map();
        t5.flatMap();
        t5.groupBy();
    }

    // map은 synchronous하게 동작한다.
    public void map() {
        System.out.println("map start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .map(p -> {
                    p.setAge(p.getAge() + 10);
                    return p;
                })
                .subscribe(p -> log.info(p.toString()));

        System.out.println("map end -----");
    }

    /*
    Flux나 Mono로 asynchronous call을 하고 싶다.
    이럴때는 flatMap을 사용한다. flatMap은 변경하는 Function을 인자로 받아서 Publisher<U>를 리턴한다. (Publisher == Flat or Mono)
    비동기적으로 변경하는 내용을 Publisher로 감싸고 있다.
    map을 사용하면 Flux<Publisher<U>>를 리턴하여 사용이 어렵다.
    반면에 FlatMap을 사용하면
    */
    public void flatMap() {
        System.out.println("flatMap start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .flatMap(p -> {
                    p.setAge(p.getAge() + 10);
                    return Mono.just(p);
                })
                .subscribe(p -> log.info(p.toString()));
        System.out.println("flatMap end -----");
    }

    public void groupBy() {
        System.out.println("groupBy start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(1, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .groupBy(Person::getIdPerson)
                .flatMap(Flux::collectList)
                .subscribe(x -> log.info(x.toString()));
        System.out.println("groupBy end -----");
    }

}
