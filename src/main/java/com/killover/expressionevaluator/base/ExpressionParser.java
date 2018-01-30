package com.killover.expressionevaluator.base;

import com.killover.expressionevaluator.exception.ExpressionParseException;

import java.util.List;

/**
 * Created by killover on 2016/11/1.
 *
 * @param <T> 待解析的对象类型
 */
public abstract class ExpressionParser<T> {
    public abstract List<ExpressionElement> parse(T expression) throws ExpressionParseException;
}
