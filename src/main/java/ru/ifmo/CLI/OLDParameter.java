package ru.ifmo.CLI;

import java.math.BigDecimal;

public class OLDParameter {
    private String name;
    private String description;

    private String limitations;
    private String type;
    private BigDecimal minLim;//значения
    private BigDecimal maxLim;

    public OLDParameter(String name, String limitations, String description) {
        this.description = description;
        this.limitations = limitations;
        this.name = name;

        if(limitations.contains(":")){
            type=limitations.split(":") [0];

            String limit = limitations.split(":")[1];
            String minvalStr = limit.split(";")[0].substring(1);
            String maxvalStr = limit.split(";")[1];
            maxvalStr = maxvalStr.substring(0,maxvalStr.length()-1);

            minLim=new BigDecimal(minvalStr);
            maxLim=new BigDecimal(maxvalStr);
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

        public OLDParameter build() {
            if (description.isEmpty()) description = "нет описания для данного параметра";
            if (name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
            return new OLDParameter(name, limitations, description);
        }
    }


    public boolean verify(String param){
        switch (type){
            case "String":
                return true;
            case "int", "long", "double", "float":
                try {
                    BigDecimal a =new BigDecimal(param.trim());
                    return inLim(a);
                } catch (Exception e) {
                    return false;
                }
        }
        return false;
    }
    private boolean inLim(BigDecimal arg) {
        String limit = limitations.split(":")[1];
        String minvalStr = limit.split(";")[0].substring(1);
        String maxvalStr = limit.split(";")[1];
        maxvalStr = maxvalStr.substring(0,maxvalStr.length()-1);


        if (!minvalStr.substring(1).equals("-inf")) {
            try {
                if(minvalStr.charAt(0)=='('){
                    if (arg.compareTo(minLim)<=0) return false;
                }else if (minvalStr.charAt(0)=='['){
                    if (arg.compareTo(minLim)<0) return false;
                }else{
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        if (!maxvalStr.substring(0, maxvalStr.length() - 1).equals("+inf")){
            if(minvalStr.charAt(0)=='('){
                if (arg.compareTo(minLim)>=0) return false;
            }else if (minvalStr.charAt(0)=='['){
                if (arg.compareTo(minLim)>0) return false;
            }else{
                return false;
            }
        }
        return true;
    }
}
