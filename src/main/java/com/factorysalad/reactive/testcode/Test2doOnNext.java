package com.factorysalad.reactive.testcode;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Test2doOnNext {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test2doOnNext t1 = new Test2doOnNext();
        t1.reactor1();
        t1.reactor2();
    }

    public void reactor1() {
        System.out.println("reactor1 start -----");
        Mono.just(new Person(1, "홍길동", 23))
                .doOnNext(p -> log.info("[doOnNext()] Person: " + p))	// doOnNext()메서드는 Mono가 Subscriber에 next 신호를 발생할 때 호출된다. (일반적으로 문제를 디버깅하는데 사용)
                .subscribe(p -> log.info("Person : " + p));   // 구독(subscribe)하면 Subscriber에 next 신호를 발생시켜서 신호를 처리한다.
        System.out.println("reactor1 end -----");
    }

    public void reactor2() {
        System.out.println("reactor2 start -----");
        Mono.just(new Person(1, "이순신", 30))
                .doOnNext(p -> log.info("[doOnNext()] Person: " + p));	// 구독하지 않으면 아무일도 일어나지 않는다.
        System.out.println("reactor2 end -----");
    }
}
