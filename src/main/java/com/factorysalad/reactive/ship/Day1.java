package com.factorysalad.reactive.ship;

import reactor.core.publisher.Flux;

/*
https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Intro 사이트 학습
 */
public class Day1 {
    public static void main(String[] args) {
        Day1 d1 = new Day1();
        d1.test1();
        d1.test2();
        d1.test3();
    }

    /*
    아래의 코드는 왜 fooA라고 출력하지 않는가?
    -> map을 사용하면 새로운 Flux를 리턴하기 때문이다.
    수정은 test2, test3 참조
     */
    public void test1() {
        Flux<String> flux = Flux.just("A");
        flux.map(s -> "foo" + s);
        flux.subscribe(System.out::println);
    }

    /*
    아래와 같이 새로 만들어진 Flux를 사용하여 출력한다.
     */
    public void test2() {
        Flux<String> flux = Flux.just("A");
        Flux flux2 = flux.map(s -> "foo" + s);
        flux2.subscribe(System.out::println);
    }

    /*
    아래와 같이 Chaining하여 사용한다.
     */
    public void test3() {
        Flux.just("A")
                .map(s -> "foo" + s)
                .subscribe(System.out::println);
    }
}
