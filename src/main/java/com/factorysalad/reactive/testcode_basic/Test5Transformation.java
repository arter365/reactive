package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test5Transformation {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test5Transformation t5 = new Test5Transformation();
        t5.map();
        t5.flatMap();
        t5.groupBy();
    }

    // map은 synchronous하게 동작한다.
    public void map() {
        System.out.println("map start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .map(p -> {
                    p.setAge(p.getAge() + 10);
                    return p;
                })
                .subscribe(p -> log.info(p.toString()));

        System.out.println("map end -----");
    }

    /*
    Flux나 Mono로 asynchronous call을 하고 싶다.
    이럴때는 flatMap을 사용한다. flatMap은 변경하는 Function을 인자(람다방식을 생각해야 한다)로 받아서 Publisher<X>를 리턴한다. (Publisher == Flat or Mono)
    비동기적으로 변경하는 내용을 Publisher로 감싸고 있다.
    하지만 map과 FlatMap의 큰 차이는 동기와 비동기의 차이도 있지만 map을 사용하면 Flux<Publisher<U>>를 리턴하여 사용이 어렵고
    FlatMap을 사용하면 이전 Publisher의 Flux를 처리하여 한개 이상의 Flux를 리턴한다. 또한 여러 Flux를 한줄로 만들어서 리턴해 준다.
    */
    public void flatMap() {
        System.out.println("flatMap start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(2, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .flatMap(p -> {
                    p.setAge(p.getAge() + 10);
                    // Mono 또는 Just를 만들어서 리턴한다. flatMap은 리턴한다고 정의되어 있을 뿐 구현은 람다식을 사용하여 직접해야 한다.
                    return Flux.just(p);
                })
                .subscribe(p -> log.info(p.toString()));
        System.out.println("flatMap end -----");
    }

    public void groupBy() {
        System.out.println("groupBy start -----");
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "강감찬", 19));
        personList.add(new Person(1, "홍길동", 23));
        personList.add(new Person(3, "이순신", 30));

        Flux.fromIterable(personList)
                .groupBy(Person::getIdPerson)
                .flatMap(Flux::collectList)
                .subscribe(x -> log.info(x.toString()));
        System.out.println("groupBy end -----");
    }

}
