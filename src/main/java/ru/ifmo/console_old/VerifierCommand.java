package ru.ifmo.console_old;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

public class VerifierCommand {
    private String name;
    private LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<>();
    private String[] flags;
    private String description;

    public VerifierCommand(String name, String description, Parameter[] parameters, String[] flags) {
        this.name = name;
        this.flags = flags;
        this.description=description;
        for (Parameter parameter : parameters) {
            this.parameters.put(parameter.getName(),parameter);
        }
    }

    public boolean verify(String com){
        com = deleteExtraSpace(com);
        if(com.split(" ").length==1 & com.split(" ")[0].equals(name)){
            //System.out.println("noooo");
            return parameters.isEmpty();
        }
        //основная часть
//        int count1=0;
//        int count2=0;
//        for (String arg : Arrays.copyOfRange(com.split(" "),1,com.split(" ").length)) {
//            count2 =0;
//            for (String par : parameters.keySet()) {
//                if(parameters.get(par).verify(arg)){
//                    //System.out.println(par+" verified");
//                    count2++;
//                }else {
//                    //System.out.println(par+" not verified");
//                }
//            }
//            if(count2==1) count1++;
//            count2=0;
//        }
        //return count1==parameters.size();


        CommandBuilder builder = new CommandBuilder(this);
        for (String arg : Arrays.copyOfRange(com.split(" "),1,com.split(" ").length)){
            builder.addParameter(arg);
        }
        return builder.isReady();
    }

    private String deleteExtraSpace(String a){
        while(a.contains("  ")|a.contains(" =")|a.contains("= ")){
            a=a.replace("  ", " ");
            a=a.replace(" =","=");
            a=a.replace("= ","=");
        }
        return a;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", flags=" + Arrays.toString(flags) +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Parameter[] getParameters() {
        int i =0;
        Set<String> keyset = parameters.keySet();
        Parameter[] par = new Parameter[keyset.size()];
        for (String s : keyset) {
            par[i++] = parameters.get(s);
        }
        return par;
    }

    public Parameter getParameter(String name){
        return parameters.get(name);
    }

    public boolean haveFlag(String flag){
        for (String s : flags) {
            if(s.equals(flag)) return true;
        }
        return false;
    }
}
