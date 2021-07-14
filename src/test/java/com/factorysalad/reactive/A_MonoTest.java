package com.factorysalad.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Locale;

@Slf4j
public class A_MonoTest {

    @Test
    public void monoSubscriber() {
        String name = "William Suane";
        /*
        // 아래와 같이 작성하면 에러가 발생한다.
        Mono<String> mono = Mono.just(name)
                .log()
                .subscribe();
        */
        Mono<String> mono = Mono.just(name)
                .log(); // Mono를 구독하면서의 동작을 log로 보여준다.
        mono.subscribe();
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumer() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log();
        mono.subscribe(s -> log.info("Value {}", s));
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerError() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                // map은 동기식이다. 1:1로 다른 것으로 변환한다.
                .map(s -> {
                    throw new RuntimeException("Testing mono with error");
                });
        mono.subscribe(s -> log.info("Name {}", s), s -> log.error("Something bad happend"));
        mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoSubscriberConsumerComplete() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoSubscriberConsumerSubscription() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        // subscribe에 인자로 올 수 있는 내용 consumer, errorConsumer, completeConsumer, subscriptionConsumer
        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"),
                Subscription::cancel);
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnMethods1() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                // 구독이 시작 될 트리거 된다. 가장 먼저 실행 된다. 파라미터로는 Subscription이 넘어온다.
                .doOnSubscribe(subscription -> log.info("--- doOnSubscribe ---"))
                // 요청 받을 때 트리거 된다. 기본적으로 파라미터는 Long의 Long.MAX_VALUE 값이 넘어온다.
                .doOnRequest(longNumber -> log.info("--- doOnRequest ---"))
                // 성공적으로 데이터가 방출 될 때 트리거 된다. 파라미터로는 해당 T 타입이 넘어온다.
                .doOnNext(s -> log.info("--- doOnNext --- {}", s))
                // 완료 되면 트리거 된다. 파라미터는 해당 T 타입이 넘어온다.
                .doOnSuccess(s -> log.info("--- doOnSuccess --- {}", s));

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    public void monoDoOnMethods2() {
        String name = "William Suane";
        Mono<Object> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("--- doOnSubscribe ---"))
                .doOnRequest(longNumber -> log.info("--- doOnRequest ---"))
                .doOnNext(s -> log.info("--- doOnNext --- {}", s))
                // 추가 시작
                .flatMap(s -> Mono.empty())
                .doOnNext(s -> log.info("--- doOnNext --- {}", s))  // 방출될게 없기 때문에 실행되지 않는다.
                // 추가 끝
                .doOnSuccess(s -> log.info("--- doOnSuccess --- {}", s));

        mono.subscribe(s -> log.info("Value {}", s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));
        log.info("-------------------------");
    }

    @Test
    public void monoDoOnError() {
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                // 에러가 발생하면 아래는 실행되지 않는다.
                .doOnNext(s -> log.info("Executing this doOnNext")) // 실행되지 않는다.
                .log(); // 실행되지 않는다.

        StepVerifier.create(error)
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    public void monoDoOnErrorResume() {
        String name = "William Suane";
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                // 에러가 발행했을 때 여기서부터 다시 시작한다.
                .onErrorResume(s -> {
                    log.info("Inside On Error Resume");
                    return Mono.just(name); // Mono를 리턴한다는 것은 Mono를 리턴하게 만들라는 뜻이다.
                })
                .log();

        StepVerifier.create(error)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    public void monoDoOnErrorReturn() {
        String name = "William Suane";
        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal argument exception error"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                // 에러가 발행했을 때 리턴한다. (오류 발생 시 데이터베이스에 저장한 후 다시 실행시키는 용도로 활용가능)
                .onErrorReturn("EMPTY")
                // 아래는 실행되지 않는다.
                .onErrorResume(s -> {
                    log.info("Inside On Error Resume");
                    return Mono.just(name); // Mono를 리턴한다는 것은 Mono를 리턴하게 만들라는 뜻이다.
                })
                .log();

        StepVerifier.create(error)
                .expectNext("EMPTY")
                .verifyComplete();
    }

}