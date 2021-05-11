package com.factorysalad.reactive.model;

public class Person {
    private Integer idPerson;
    private String names;
    private Integer age;

    public Person(Integer idPerson, String names, Integer age) {
        this.idPerson = idPerson;
        this.names = names;
        this.age = age;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", names='" + names + '\'' +
                ", age=" + age +
                '}';
    }
}
