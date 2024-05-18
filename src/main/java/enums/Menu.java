package enums;

import java.util.regex.Matcher;

public enum Menu {
    USERNAME("^[a-zA-Z0-9_]{5,20}$"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_!@#$%^&*])[a-zA-Z0-9_!@#&$%^*]{8,}$");


    private String regex;
    Menu(String regex) {
        this.regex = regex;
    }
    public String getRegex() {
        return regex;
    }
    public Matcher getMatcher(String input) {
        return java.util.regex.Pattern.compile(regex).matcher(input);
    }
}
