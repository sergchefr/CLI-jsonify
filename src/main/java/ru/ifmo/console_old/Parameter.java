package ru.ifmo.console_old;

public class Parameter {
    private String name;
    private String limitations;
    private String description;
    private String type;
    private String limit;

    public Parameter(String name, String limitations, String description) {
        this.description = description;
        this.limitations = limitations;
        this.name = name;

        if(limitations.contains(":")){
            type=limitations.split(":") [0];
            limit=limitations.split(":") [1];
        }else{
            type = limitations;
            limit = null;
        }
    }

    public boolean verify(String param){
        //System.out.println(param);
        param=param.split("=")[1];
        switch (type){
            case "String":
                return !param.contains(" ");
            case "int":
                try{
                    return inLim(Integer.parseInt(param));
                } catch (NumberFormatException e) {
                    //System.out.println("suiiir");
                    return false;
                }
            case "double":
                try{
                    return inLim(Double.parseDouble(param));
                } catch (NumberFormatException e) {
                    return false;
                }
        }
        return true;
    }

    private boolean inLim(int arg) {
        //System.out.println("yeppp");
        if(limit==null)return true;
        String minvalStr = limit.split(";")[0];
        String maxvalStr = limit.split(";")[1];
        if (!minvalStr.substring(1).equals("-inf")) {
            try {
                if (minvalStr.toCharArray()[0] == '(' & arg <= Integer.parseInt(minvalStr.substring(1))) return false;
                if (minvalStr.toCharArray()[0] == '[' & arg < Integer.parseInt(minvalStr.substring(1))) return false;
                //System.out.println("minval: "+Integer.parseInt(minvalStr.substring(1)));
            } catch (Exception e) {
                return false;
            }
        }

        String substring = maxvalStr.substring(0, maxvalStr.length() - 1);
        if (!substring.equals("+inf")) {
            try {
                if (maxvalStr.toCharArray()[maxvalStr.length()-1] == ')' & arg >= Integer.parseInt(substring)) return false;
                if (maxvalStr.toCharArray()[maxvalStr.length()-1] == ']' & arg > Integer.parseInt(substring)) return false;
                //System.out.println("maxval: "+maxvalStr.toCharArray()[maxvalStr.length()-1]);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private boolean inLim(double arg) {
        if(limit==null)return true;
        String minvalStr = limit.split(";")[0];
        String maxvalStr = limit.split(";")[1];
        if (!minvalStr.substring(1).equals("-inf")) {
            try {
                if (minvalStr.toCharArray()[0] == '(' & arg <= Double.parseDouble(minvalStr.substring(1))) return false;
                if (minvalStr.toCharArray()[0] == '[' & arg < Double.parseDouble(minvalStr.substring(1))) return false;
            } catch (Exception e) {
                return false;
            }
        }

        String substring = maxvalStr.substring(0, maxvalStr.length() - 1);
        if (!substring.equals("+inf")) {
            try {
                if (maxvalStr.toCharArray()[maxvalStr.length()-1] == ')' & arg >= Double.parseDouble(substring)) return false;
                if (maxvalStr.toCharArray()[maxvalStr.length()-1] == ']' & arg > Double.parseDouble(substring)) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public String getDescription(){
        return name+": "+limitations+". "+description;
    }

    public String getLimitations() {
        return limitations;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", limitations='" + limitations + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
