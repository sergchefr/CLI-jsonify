package ru.ifmo.CLI.parameter_validators;

public class IntValidator implements Validator {

    //TODO сделать нормально;

    @Override
    public boolean validate(String limitations, String param) {
        try{
            return inLim(Integer.parseInt(param), limitations);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean inLim(int arg, String limitations) {
        String limit;
        if (limitations.contains(":")) {
            limit = limitations.split(":")[1];
        } else {
            return true;
        }

        if (limit == null) return true;
        String minvalStr = limit.split(";")[0];
        String maxvalStr = limit.split(";")[1];
        if (!minvalStr.substring(1).equals("-inf")) {
            try {
                if (minvalStr.toCharArray()[0] == '(' & arg <= Integer.parseInt(minvalStr.substring(1))) return false;
                if (minvalStr.toCharArray()[0] == '[' & arg < Integer.parseInt(minvalStr.substring(1))) return false;
            } catch (Exception e) {
                return false;
            }
        }

        String substring = maxvalStr.substring(0, maxvalStr.length() - 1);
        if (!substring.equals("+inf")) {
            try {
                if (maxvalStr.toCharArray()[maxvalStr.length() - 1] == ')' & arg >= Integer.parseInt(substring))
                    return false;
                if (maxvalStr.toCharArray()[maxvalStr.length() - 1] == ']' & arg > Integer.parseInt(substring))
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}

