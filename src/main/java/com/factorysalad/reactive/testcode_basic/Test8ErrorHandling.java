package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test8ErrorHandling {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test8ErrorHandling t8 = new Test8ErrorHandling();
        t8.retry();
        t8.errorReturn();
        t8.errorResume();
        t8.errorMap();
    }

    public void retry() {
        System.out.println("retry start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .concatWith(Flux.error(new RuntimeException("Error")))
                .retry(1)
                .doOnNext(x -> log.info(x.toString()))
                .subscribe();
        System.out.println("retry end -----");
    }

    public void errorReturn() {
        System.out.println("errorReturn start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .concatWith(Flux.error(new RuntimeException("Error")))
                .onErrorReturn(new Person(0,"XYZ", 99))
                .subscribe(x -> log.info(x.toString()));
        System.out.println("errorReturn end -----");
    }

    public void errorResume() {
        System.out.println("errorResume start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .concatWith(Flux.error(new RuntimeException("Error")))
                .onErrorResume(e -> Mono.just(new Person(0,"XYZ", 99)))
                .subscribe(x -> log.info(x.toString()));
        System.out.println("errorResume end -----");
    }

    public void errorMap() {
        System.out.println("errorMap start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .concatWith(Flux.error(new RuntimeException("Error")))
                .onErrorMap(e -> new InterruptedException(e.getMessage()))
                .subscribe(x -> log.info(x.toString()));
        System.out.println("errorMap end -----");
    }
}
