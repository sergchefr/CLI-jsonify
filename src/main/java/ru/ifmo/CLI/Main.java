package ru.ifmo.CLI;

import ru.ifmo.CLI.parameter_validators.StringValidator;

public class Main {
    public static void main(String[] args){
        ParameterVerifier.addValidator("String",new StringValidator());
        ParameterVerifier par = ParameterVerifier.builder().name("param").description("descr").limitations("String:no_space").build();
        System.out.println(par.verify("asefesfasasef"));
    }
}
