package com.killover.expressionevaluator.operand.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by killover on 2016/11/4.
 */
public class Regex {

    private String regexStr;
    private Pattern pattern;

    public Regex(String regex){
        regexStr = regex;
        pattern = Pattern.compile(regex);
    }

    public Boolean match(String str){
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
