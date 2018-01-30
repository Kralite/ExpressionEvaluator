package com.killover.expressionevaluator;

import com.killover.expressionevaluator.evaluator.CommonEvaluator;
import com.killover.expressionevaluator.operand.*;

import java.util.*;

/**
 * Created by killover on 30/01/2018.
 */
public class Sample {


    public static void sample1(){
        String expression = "x/5 + e * 2 - c + 52.4/3";

        //传入变量值
        Map<String, String> params = new TreeMap<>();
        params.put("x", "6");
        params.put("e", "3");
        params.put("c", "49");

        try{
            Operand result = CommonEvaluator.value(expression, params);
            System.out.println("Sample1 result: " + result.value());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sample2(){
        String expression = "\"git\" + str";

        //传入变量值
        Map<String, String> params = new TreeMap<>();
        params.put("str", "hub");

        try{
            Operand result = CommonEvaluator.value(expression, params);
            System.out.println("Sample2 result: " + result.value());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sample3(){
        String expression = "(emailName + \"@\" + emailDomain) ~ regex(\"\\w+@\\w+\\.io\") && delay1 + delay2 > 100 ";

        //传入变量值
        Map<String, String> params = new TreeMap<>();
        params.put("emailName", "killover");
        params.put("emailDomain", "github.io");
        params.put("delay1", "49");
        params.put("delay2", "50");

        try{
            Operand result = CommonEvaluator.value(expression, params);
            System.out.println("Sample3 result: " + result.value());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        sample1();
        sample2();
        sample3();
    }
}
