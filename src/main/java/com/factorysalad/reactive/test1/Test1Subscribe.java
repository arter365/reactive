package com.factorysalad.reactive.test1;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test1Subscribe {
    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);
    public static void main(String[] args) {
        Test1Subscribe t1 = new Test1Subscribe();
        t1.reactor();
    }

    public void reactor() {
        Mono.just(new Person(1, "이종석", 46))		// 이 줄만 있으면 아무것도 일어나지 않는다. 파이프라인만 만들었을 뿐이다.
                .doOnNext(p -> log.info("[Reactor doOnNext] Person: " + p))	// 일반적으로 문제를 디버깅하는데 사용된다.
                .subscribe(p -> log.info("[Reactor subscribe] Person: " + p));
        System.out.println();
    }

    public void mono() {
        Mono.just(new Person(1, "이종석", 46)).subscribe(p -> log.info(p.toString()));
    }

    public void flux() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "이종석", 46));
        persons.add(new Person(2, "홍길동", 25));
        persons.add(new Person(3, "이순신", 38));

        Flux.fromIterable(persons).subscribe(p -> log.info(p.toString()));
    }

    public void fluxMono() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "이종석", 46));
        persons.add(new Person(2, "홍길동", 25));
        persons.add(new Person(3, "이순신", 38));

        Flux<Person> fx = Flux.fromIterable(persons);
        fx.collectList().subscribe(p -> log.info(p.toString()));	// collectList()에서는 Mono를 리턴한ㄷ.
    }
}
