package com.factorysalad.reactive.testcode_basket;

import com.factorysalad.reactive.model.FruitInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 여기서는 하나의 스트림에서 여러 개의 스트림으로 갈라질 때 Flux와 Mono를 어떻게 적절히 섞어서 사용했는지 예제를 통해 보도록 하겠습니다.

// 출처 : https://tech.kakao.com/2018/05/29/reactor-programming/
public class Step1 {

    public static void main(String[] args) {
        Step1 s1 = new Step1();
        s1.basket();
    }

    public void basket() {
        // 재료 ===========================================================================================

        // basket1부터 basket3까지 3개의 과일바구니가 있으며, 과일바구니 안에는 과일을 중복해서 넣을 수 있다.
        final List<String> basket1 = Arrays.asList("kiwi", "orange", "lemon", "orange", "lemon", "kiwi");
        final List<String> basket2 = Arrays.asList("banana", "lemon", "lemon", "kiwi");
        final List<String> basket3 = Arrays.asList("strawberry", "orange", "lemon", "grape", "strawberry");
        // // 이 바구니를 List로 가지는 baskets
        final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
        // Flux.fromIterable에 Iterable type의 인자를 넘기면 이 Iterable을 Flux로 변환해준다.
        final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);

        // ===============================================================================================

        /*
        basketFlux로부터 각각의 바구니들을 꺼내야 한다.
        이렇게 값을 꺼내서 새로운 Publisher로 바꿔줄 수 있는 대표적인 연산자는 아래와 같다.
        1) flatMap : 리턴하는 Publisher가 비동기로 동작할 때 순서를 보장하지 않는다.
        2) flatMapSequential : flatMap과 달리 순서를 보장하며 오는 대로 구독하고 결과는 순서에 맞게 리턴하는 역할을 해서, 비동기 환경에서 동시성을 지원하면서도 순서를 보장할 때 쓰인다.
        3) concatMap : flatMap과 달리 순서를 보장하며 인자로 지정된 함수에서 리턴하는 Publisher의 스트림이 다 끝난 후에 그다음 넘어오는 값의 Publisher스트림을 처리한다.
         */
        basketFlux.concatMap(basket -> {    // concatMap으로 하나씩 꺼낸다. 한단계를 낮추면 basket이 나온다.
            // --------------- 중복 제거 테스트 ---------------
            System.out.println("Test 1) basket : " + basket);
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();

            // 테스트
            distinctFruits.subscribe(f -> {
                System.out.print("Test 2) distinctFruits : ");
                System.out.println(f);
            });
            /*
            중복 제거 테스트 결과
            Test 1) basket : [kiwi, orange, lemon, orange, lemon, kiwi]
            Test 2) distinctFruits : [kiwi, orange, lemon]  // 중복 제거됨.
            Test 1) basket : [banana, lemon, lemon, kiwi]
            Test 2) distinctFruits : [banana, lemon, kiwi]  // 중복 제거됨.
            Test 1) basket : [strawberry, orange, lemon, grape, strawberry]
            Test 2) distinctFruits : [strawberry, orange, lemon, grape] // 중복 제거됨.
             */

            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
                    // 테스트 : basket을 이터러블하게 만들어서 반복하면 fruit가 하나씩 나온다.
                    .doOnNext(fruit -> {
                        System.out.print("Test 3) fruit : ");
                        System.out.println(fruit);  // f -> 이렇게 적으면 ::printf 이렇게 적지 못하는 것 같다.
                    })
                    /*
                    Test 1) basket : [kiwi, orange, lemon, orange, lemon, kiwi]
                    Test 2) distinctFruits : [kiwi, orange, lemon]
                    Test 3) fruit : kiwi
                    Test 3) fruit : orange
                    Test 3) fruit : lemon
                    Test 3) fruit : orange
                    Test 3) fruit : lemon
                    Test 3) fruit : kiwi

                    Test 1) basket : [banana, lemon, lemon, kiwi]
                    Test 2) distinctFruits : [banana, lemon, kiwi]
                    Test 3) fruit : banana
                    Test 3) fruit : lemon
                    Test 3) fruit : lemon
                    Test 3) fruit : kiwi

                    Test 1) basket : [strawberry, orange, lemon, grape, strawberry]
                    Test 2) distinctFruits : [strawberry, orange, lemon, grape]
                    Test 3) fruit : strawberry
                    Test 3) fruit : orange
                    Test 3) fruit : lemon
                    Test 3) fruit : grape
                    Test 3) fruit : strawberry
                     */
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다. (Flux를 리턴한다)
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }) // 각 과일별로 개수를 Map으로 리턴
                    ) // concatMap으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<>() {
                        {
                            putAll(accumulatedMap);
                            putAll(currentMap);
                        }
                    }); // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
            return Flux.zip(distinctFruits, countFruitsMono, FruitInfo::new);
        }).subscribe(System.out::println);
    }
}
