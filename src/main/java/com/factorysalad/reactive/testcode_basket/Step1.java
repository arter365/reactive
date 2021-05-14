package com.factorysalad.reactive.testcode_basket;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.FruitInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// https://tech.kakao.com/2018/05/29/reactor-programming/
public class Step1 {

    public static void main(String[] args) {
        Step1 t5 = new Step1();
        t5.basket();
    }

    public void basket() {
        // basket1부터 basket3까지 3개의 과일바구니가 있으며, 과일바구니 안에는 과일을 중복해서 넣을 수 있다.
        final List<String> basket1 = Arrays.asList("kiwi", "orange", "lemon", "orange", "lemon", "kiwi");
        final List<String> basket2 = Arrays.asList("banana", "lemon", "lemon", "kiwi");
        final List<String> basket3 = Arrays.asList("strawberry", "orange", "lemon", "grape", "strawberry");
        // // 이 바구니를 List로 가지는 baskets
        final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
        // Flux.fromIterable에 Iterable type의 인자를 넘기면 이 Iterable을 Flux로 변환해준다.
        final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);

        basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();
            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
                    .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
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
