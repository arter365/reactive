package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Test9Conditional {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test9Conditional t9 = new Test9Conditional();
        t9.defaultIfEmpty1();
        t9.defaultIfEmpty2();
        t9.defaultIfEmpty3();
        t9.takeUntil();
        t9.timeout();

    }

    public void defaultIfEmpty1() {
        System.out.println("defaultIfEmpty1 start -----");
        Mono.empty()
                .defaultIfEmpty(new Person(1, "DEFAULT1", 51))
                .subscribe(x -> log.info(x.toString()));
        System.out.println("defaultIfEmpty1 end -----");
    }

    public void defaultIfEmpty2() {
        System.out.println("defaultIfEmpty2 start -----");
        Flux.empty()
                .defaultIfEmpty(new Person(2, "DEFAULT2", 52))
                .subscribe(x -> log.info(x.toString()));
        System.out.println("defaultIfEmpty2 end -----");
    }

    public void defaultIfEmpty3() {
        System.out.println("defaultIfEmpty3 start -----");
        Mono.just(new Person(6, "이이", 61))
                .defaultIfEmpty(new Person(3, "DEFAULT3", 53))  // 비어있지 않기 때문에 동작하지 않는다.
                .subscribe(x -> log.info(x.toString()));
        System.out.println("defaultIfEmpty3 end -----");
    }

    public void takeUntil() {
        System.out.println("takeUntil start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .takeUntil(p -> p.getAge() > 10)        // 조건에 맞는것 까지 가져온다.
                .subscribe(x -> log.info(x.toString()));
        System.out.println("takeUntil end -----");
    }

    public void timeout() {
        System.out.println("timeout start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                // 아래와 같이 넣으면 TimeoutException이 발생한다. 이 구문의 timeout이 2초인데 각 Stream마다 3초의 딜레이를 주었기 때문.
//                .delayElements(Duration.ofSeconds(3))
//                .timeout(Duration.ofSeconds(2))
                // 아래와 같이 처리하면 처리된다.
                .delayElements(Duration.ofSeconds(1))
                .timeout(Duration.ofSeconds(2))
                .subscribe(x -> log.info(x.toString()));

        // 위의 내용이 처리될 때 까지 프로그램이 대기해야 하기 때문에 아래와 같이 대기를 주었다.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("timeout end -----");
    }
}
