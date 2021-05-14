package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/*
Reactive Streams는 시간에 따라 연속으로 발생하는(0개 ~ N개) 데이터(이벤트 또는 신호)이다.

Publisher(발행자)를 사용하여 Streams를 정의한다.
Subscriber(구독자)를 사용하여 발생한 신호를 처리한다.
subscribe : Subscriber가 Publisher로 부터 신호를 받는 것을 구독이라 한다.

Mono : Publisher를 구현한 발행자이다. (0 또는 1개의 데이터를 발생한다)
Flux : Publisher를 구현한 발행자이다. (0개 이상의 데이터를 발생한다)
참고 : 실제 시퀀스를 직접 생산할 일은 많지 않다. 대부분 라이브러리에서 제공받아서 사용한다.
 */
public class Test1Subscribe {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test1Subscribe t1 = new Test1Subscribe();
        t1.reactor1();
        t1.reactor2();
    }

    // Mono는 Publisher(발행자)를 구현한 객체이다.
    public void reactor1() {
        System.out.println("reactor1 start -----");
        // 시퀀스로 사용 할 데이터가 이미 존재할 때 사용한다.
        Mono.just(new Person(1, "홍길동", 23));		        // 발행만 하면 아무일도 일어나지 않는다. (파이프라인만 만들었을 뿐이다)
        System.out.println("reactor1 end -----");
    }

    public void reactor2() {
        System.out.println("reactor2 start -----");
        Mono.just(new Person(1, "이순신", 30))                 // Reactive Streams에서는 데이터를 요청(구독)하기 전까지 아무런 데이터도 전송하지 않는다.
                .subscribe(p -> log.info("Person : " + p));   // 구독(subscribe)하면 Subscriber에 next 신호를 발생시켜서 신호를 처리한다.
        System.out.println("reactor2 end -----");
    }
}
