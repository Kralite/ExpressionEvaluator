package com.killover.expressionevaluator.exception;

/**
 * Created by killover on 2016/10/31.
 */
public class ExpressionFormatException extends Exception {
    public ExpressionFormatException(String msg, String expression){
        super(msg + "。错误的表达式为： " + expression);
    }
}
