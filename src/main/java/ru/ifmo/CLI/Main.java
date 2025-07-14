package ru.ifmo.CLI;

public class Main {
    public static void main(String[] args){
        Parameter par = Parameter.builder().name("param").description("descr").limitations("String").build();
    }
}
