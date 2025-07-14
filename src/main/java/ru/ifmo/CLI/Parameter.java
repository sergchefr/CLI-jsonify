package ru.ifmo.CLI;

import ru.ifmo.console_old.ParameterBuilder;

import java.math.BigDecimal;

public class Parameter {
    private String name;
    private String limitations;
    private String description;
    private String type;
    private String limits;// скобки
    private BigDecimal minLim;//значения
    private BigDecimal maxLim;

    public Parameter(String name, String limitations, String description) {
        this.description = description;
        this.limitations = limitations;
        this.name = name;

        if(limitations.contains(":")){
            type=limitations.split(":") [0];
            //тут ненправильно! там же еще и обозначения типа данных!
            limits= ""+limitations.charAt(0)+limitations.charAt(limitations.length()-1);
            minLim=new BigDecimal(limitations.split(";")[0].substring(1));

            String substring = limitations.split(";")[1].substring(0, limitations.split(";")[1].length() - 1);
            maxLim=new BigDecimal(substring);
        }else{
            type = limitations;
        }
    }
    public static ParameterBuilder builder(){
        return new ParameterBuilder();
    }
    static class ParameterBuilder {
        private String name = "";
        private String description = "";
        private String limitations;

        public ParameterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ParameterBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ParameterBuilder limitations(String limitations) {
            this.limitations = limitations;
            return this;
        }

        public Parameter build() {
            if (description.isEmpty()) description = "нет описания для данного параметра";
            if (name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
            return new Parameter(name, limitations, description);
        }
    }


    public boolean verify(String param){
        switch (type){
            case "String":
                return true;
            case "int", "long", "double", "float":
                try {
                    BigDecimal a =new BigDecimal(param.trim());
                } catch (Exception e) {
                    return false;
                }
        }
        return false;
    }

    private boolean inLim(BigDecimal arg) {
        if(limits==null)return true;
//дописать то херня вопрос, но что сделать с бесконечностью?
        if (!minvalStr.substring(1).equals("-inf")) {
            try {
                if (minvalStr.toCharArray()[0] == '(' && arg <= Double.parseDouble(minvalStr.substring(1))) return false;
                if (minvalStr.toCharArray()[0] == '[' && arg < Double.parseDouble(minvalStr.substring(1))) return false;
            } catch (Exception e) {
                return false;
            }
        }

        String substring = maxvalStr.substring(0, maxvalStr.length() - 1);
        if (!substring.equals("+inf")) {
            try {
                if (maxvalStr.toCharArray()[1] == ')' && arg >= Double.parseDouble(substring)) return false;
                if (maxvalStr.toCharArray()[1] == ']' && arg > Double.parseDouble(substring)) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
