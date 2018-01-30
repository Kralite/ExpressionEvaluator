package com.killover.expressionevaluator.operand.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by killover on 2016/11/17.
 */
public class RegViralbleName {

    private String regexStr;
    private Pattern pattern;
    private String name;

    public RegViralbleName(String regexStr){
        this.regexStr = regexStr;
        pattern = Pattern.compile(regexStr);
    }

    public Boolean match(String virableName){
        Matcher matcher = pattern.matcher(virableName);
        return matcher.matches();
    }

    public String getRegexStr() {
        return regexStr;
    }

    public void setRegexStr(String regexStr) {
        this.regexStr = regexStr;
        pattern = Pattern.compile(regexStr);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
