package com.factorysalad.reactive.testcode_basket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Step0 {
    public static void main(String[] args) {
        Step0 step0 = new Step0();
        step0.monoTest();
    }

    public void monoTest() {
        String name = "andrew";
        /*
        // 아래와 같이 작성하면 에러가 발생한다.
        Mono<String> mono = Mono.just(name)
                .log()
                .subscribe();
        */
        Mono.just(name)
                .log()
                .subscribe();
        /*
        11:05:56.039 [main] INFO reactor.Mono.Just.1 - | onSubscribe([Synchronous Fuseable] Operators.ScalarSubscription)
        11:05:56.044 [main] INFO reactor.Mono.Just.1 - | request(unbounded)
        11:05:56.044 [main] INFO reactor.Mono.Just.1 - | onNext(andrew)
        11:05:56.049 [main] INFO reactor.Mono.Just.1 - | onComplete()
         */
    }

    public void manualTest() {
        Flux<String> seq = Flux.just("빨강", "초록", "파랑")
                .flatMap(ball ->
                        // Map과 달리 하나가 들어갔을 때 한개 이상이 나올 수 있다는 것이 중요함.
                        Flux.just(ball + " 다이아", ball + " 다이아")
                ); // Integeran를 Flux<Integer>로 1-N 변환
        seq.subscribe(System.out::println);
    }
}
