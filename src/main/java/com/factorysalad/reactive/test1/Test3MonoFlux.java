package com.factorysalad.reactive.test1;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test3MonoFlux {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test3MonoFlux t1 = new Test3MonoFlux();
        t1.mono();
        t1.flux();
        t1.fluxMono();
    }

    // Mono : Publisher를 구현한 발행자이다. (0 또는 1개의 데이터를 발생한다)
    public void mono() {
        System.out.println("mono start -----");
        Mono.just(new Person(1, "강감찬", 19)).subscribe(p -> log.info(p.toString()));
        System.out.println("mono end -----");
    }

    // Flux : Publisher를 구현한 발행자이다. (0개 이상의 데이터를 발생한다)
    public void flux() {
        System.out.println("flux start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        // Iterable을 통해 Publisher를 생성한다.
        Flux.fromIterable(personList).subscribe(p -> log.info(p.toString()));
        System.out.println("flux end -----");
    }

    // Flux에서 Mono로 리턴하여 처리하는 예제
    public void fluxMono() {
        System.out.println("fluxMono start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux<Person> fx = Flux.fromIterable(personList);
        fx.collectList().subscribe(p -> log.info(p.toString()));	// collectList()에서는 Mono<List>를 리턴한다.
        System.out.println("fluxMono end -----");
        System.out.println("fluxMono end -----");
    }
}
