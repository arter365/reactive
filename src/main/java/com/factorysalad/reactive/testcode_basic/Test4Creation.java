package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test4Creation {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test4Creation t4 = new Test4Creation();
        //t4.empty();
        t4.range();
        t4.fluxRepeat();
        t4.monoRepeat();
    }

    // 데이터를 발생하지 않는 Mono, Flux 생성
    public void empty() {
        System.out.println("empty start -----");
        // Mono.empty();
        // Flux.empty();
        System.out.println("empty end -----");
    }

    // start 값 부터 count개의 Int 객체를 발행한다.
    public void range() {
        System.out.println("range start -----");
        Flux.range(0,3)
                .subscribe(i -> log.info("i : " + i));
        System.out.println("range end -----");
    }

    // 반복발행한다.
    public void fluxRepeat() {
        System.out.println("fluxRepeat start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .repeat(1)      // 한번 반복한다는 말이기 때문에 결과는 2번 찍힌다.
                .subscribe(p -> log.info(p.toString()));
        System.out.println("fluxRepeat end -----");
    }

    // 반복발행한다.
    public void monoRepeat() {
        System.out.println("monoRepeat start -----");
        Mono.just(new Person(1, "강감찬", 19))
                .repeat(1)
                .subscribe(x -> log.info(x.toString()));
        System.out.println("monoRepeat end -----");
    }
}
