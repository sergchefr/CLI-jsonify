package ru.ifmo.console_old;

import java.util.LinkedList;

public class CommandBuilder {
    private VerifierCommand command;
    private LinkedList<String> keyset;
    private String res;

    public CommandBuilder(VerifierCommand command) {
        this.command = command;
        keyset = new LinkedList<>();
        for (Parameter t1 :command.getParameters()) {
            keyset.addLast(t1.getName());
        }
        //System.out.println("%keyset%:"+keyset);
        res=command.getName();
    }

    public boolean addParameter(String parameter){
        //System.out.println("%addParameter%:"+parameter);
        //System.out.println("%keyset1%:"+keyset);
        int counteq =0;
        int countsp =0;
        for (char c : parameter.toCharArray()) {
            if(c=='=') counteq++;
            if(c==' ') countsp++;
        }
        //System.out.println("%parameter%"+parameter);
        if(counteq!=1) return false;
        //if(countsp!=0) return false; //че это за х вообще?

        var t2 = command.getParameter(parameter.split("=")[0]);
        if(t2==null) return false;
        //System.out.println("verification("+parameter+")"+t2.verify(parameter));
        if(t2.verify(parameter)){
            //System.out.println("zzz");
            res=res+" "+parameter;
            keyset.remove(parameter.split("=")[0]);
            return true;
        }
        return false;
    }

    public boolean isReady(){
        return keyset.isEmpty();
    }

    public String build(){
        if(!isReady()) throw new RuntimeException("объект еще не готов, его нельзя создать");
        return res;
    }

    public Parameter nextParameter(){
        if(keyset.isEmpty()) throw new RuntimeException("объект уже готов");
        return command.getParameter(keyset.peek());
    }

}
