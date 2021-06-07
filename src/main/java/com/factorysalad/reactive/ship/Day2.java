package com.factorysalad.reactive.ship;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/*
https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Flux 사이트 학습
Flux는 Publisher를 구현했다. 생성, 변환, 오케스트레이션하는 연산자를 가지고 있다.
Flux는 0~N개의 <T>요소 방출(onNext 이벤트) 후 terminal event 방출(완료(onComplete) 또는 오류(onError)) 이게 없으면 무한이다.
- Static Methods : Flux 생성.
- Instance Methods : 비동기 시퀀스를 생성하는 비동기 파이프 라인 구축.
- subscribe(), publish, publishNext : 이것을 사용하면 데이터가 흐른다.
Flux 예)
    Flux.fromIterable(getSomeLongList())                // List로 Flux를 생성.
        .delayElements(Duration.ofMillis(100))          // Item을 하나씩 보낸 때 100msec씩 딜레이 시킨다.
        .doOnNext(serviceA::someObserver)               // Item을 보낼 때 트리거되는 Callback(someSbserver) 호출
        .map(d -> d * 2)
        .take(3)                                        // 3개만 받는다.
        .onErrorResume(errorHandler::fallback)          // 지정한 에러가 발생하면 대체 publisher를 구독한다.
        .doAfterTerminate(serviceM::incrementTerminate) // 종료(에러포함) 된 후 할일 추가.
        .subscribe(System.out::println)                 // 구독 (비동기, 논블러킹으로 처리된다)

Mono는 0 또는 1개의 요소 방출
 */
public class Day2 {
    public static void main(String[] args) throws InterruptedException {
        Day2 d2 = new Day2();
        d2.emptyFlux();
        d2.fooBarFluxFromValues();
        d2.fooBarFluxFromList();
        d2.errorFlux();
        d2.counter();
    }

    // TODO 빈 Flux 리턴.
    Flux<String> emptyFlux() {
        return Flux.empty();
    }

    // TODO 배열이나 컬렉션을 사용하지 않고 "foo"와 "bar"두 값을 포함하는 Flux를 반환.
    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

    // TODO 리스트를 사용해서 "foo"및 "bar"를 포함하는 Flux 생성.
    Flux<String> fooBarFluxFromList() {
        return Flux.fromIterable(List.of("foo","bar"));
    }

    // TODO IllegalStateException을 발생시키는 Flux 생성 (asynchronous 적인 에러처리) , (error : 에러를 만든다)
    void errorFlux() {
        Flux.error(new IllegalStateException())
                .doOnError(System.out::println)
                .subscribe();
    }

    // TODO 100ms마다 0에서 9까지 증가하는 값을 방출하는 Flux를 만든다.
    public void counter() throws InterruptedException {
        Flux.interval(Duration.ofMillis(100))
                .take(10)
                .subscribe(System.out::println);

        Thread.sleep(5000);         // 위의 내용을 처리하기 전에 프로그램이 끝나기 때문에 대기시켜준다. 비동기처리 된다는 증거이다.
    }

    // TODO 비어있는 Mono 리턴.
    Mono<String> emptyMono() {
        return Mono.empty();
    }

    // TODO 어떤 시그널도 방출하지 않는 Mono 리턴.
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

    // TODO foo를 리턴하는 Mono.
    Mono<String> fooMono() {
        return Mono.just("foo");
    }

    // TODO IllegalStateException을 방출하는 Mono.
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }
}
