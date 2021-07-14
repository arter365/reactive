package com.factorysalad.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@Slf4j
public class B_FluxTest {

    @Test
    public void fluxSubscriber() {
        Flux<String> fluxString = Flux.just("William", "Suane", "DevDojo", "Academy")
                .log();

        StepVerifier.create(fluxString)
                .expectNext("William", "Suane", "DevDojo", "Academy")
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberNumbers() {
        // 숫자범위 평가
        Flux<Integer> flux = Flux.range(1, 5)
                .log();

        flux.subscribe(i -> log.info("Number {}", i));

        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberFromList1() {
        // 아래와 같이 만들면 List<Integer> 하나가 들어있는 Flux를 생성한다.
        Flux<List<Integer>> flux = Flux.just(List.of(1, 2, 3, 4, 5))
                .log();

        flux.subscribe(i -> log.info("Number {}", i));
    }

    @Test
    public void fluxSubscriberFromList2() {
        // 숫자범위 평가
        Flux<Integer> flux = Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .log();

        flux.subscribe(i -> log.info("Number {}", i));

        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberNumbersError() {
        // 숫자범위 평가
        Flux<Integer> flux = Flux.range(1, 5)
                .log()
                // 4를 받으면 에러를 발생하게 만들었다.
                .map(i -> {
                    if (i == 4) {
                        throw new IndexOutOfBoundsException("index error");
                    }
                    return i;
                });

        flux.subscribe(i -> log.info("Number {}", i), Throwable::printStackTrace,
                () -> log.info("DONE!"));

        StepVerifier.create(flux)
                .expectNext(1, 2, 3)
                .expectError(IndexOutOfBoundsException.class)
                .verify();
    }

    //09번 볼차례
    @Test
    public void fluxSubscriberNumbersUglyBackpressure() {

        Flux<Integer> flux = Flux.range(1, 10)
                .log();

        flux.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .expectError(IndexOutOfBoundsException.class)
                .verify();
    }
}
