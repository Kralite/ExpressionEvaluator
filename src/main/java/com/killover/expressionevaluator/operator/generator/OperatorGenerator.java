package com.killover.expressionevaluator.operator.generator;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.model.Regex;
import com.killover.expressionevaluator.operand.model.Vector;
import com.killover.expressionevaluator.operator.*;
import com.killover.expressionevaluator.operator.bool.BooleanAndOperator;
import com.killover.expressionevaluator.operator.bool.BooleanNotOperator;
import com.killover.expressionevaluator.operator.bool.BooleanOrOperator;
import com.killover.expressionevaluator.operator.doubleNumber.*;
import com.killover.expressionevaluator.operator.double_string.DoublePlusStringOperator;
import com.killover.expressionevaluator.operator.double_string.StringPlusDoubleOperator;
import com.killover.expressionevaluator.operator.integer.*;
import com.killover.expressionevaluator.operator.longNumber.*;
import com.killover.expressionevaluator.operator.long_double.*;
import com.killover.expressionevaluator.operator.long_string.LongPlusStringOperator;
import com.killover.expressionevaluator.operator.long_string.StringPlusLongOperator;
import com.killover.expressionevaluator.operator.string.StringEqualOperator;
import com.killover.expressionevaluator.operator.string.StringPlusOperator;
import com.killover.expressionevaluator.operator.regex.StringRegexOperator;
import com.killover.expressionevaluator.operator.vector.*;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.BracketOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.UnaryOperatorWrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by killover on 2016/11/1.
 */
public class OperatorGenerator {

    private Map<BinaryOperatorGenerationMapKey, Class<? extends Operator>> binaryMap;
    private Map<UnaryOperatorGenerationMapKey, Class<? extends Operator>> unaryMap;


    static private Map<BinaryOperatorGenerationMapKey, Class<? extends Operator>> binaryMap_default = new HashMap<BinaryOperatorGenerationMapKey, Class<? extends Operator>>();
    static private Map<UnaryOperatorGenerationMapKey, Class<? extends Operator>> unaryMap_default = new HashMap<UnaryOperatorGenerationMapKey, Class<? extends Operator>>();

    static {
        initDefaultGenerator(unaryMap_default, binaryMap_default);
    }

    public OperatorGenerator(){
        binaryMap = binaryMap_default;
        unaryMap = unaryMap_default;
    }

    //二元操作符的生成器
    public BinaryOperator generate(BinaryOperatorWrapper wrapper, Class classOfOperand1, Class classOfOperand2) throws ExpressionOperatorException{
        BinaryOperatorGenerationMapKey key = new BinaryOperatorGenerationMapKey(wrapper, classOfOperand1, classOfOperand2);
        Class operatorClass = binaryMap.get(key);
        BinaryOperator operatorInstance;
        try {
            operatorInstance = (BinaryOperator)operatorClass.newInstance();
        }catch (InstantiationException ie){
            try {
                Constructor<?> operatorConstructor = operatorClass.getDeclaredConstructor(new Class[]{String.class, Integer.class});
                operatorInstance = (BinaryOperator)operatorConstructor.newInstance(new Object[]{wrapper.getSymbol(), wrapper.getPriority()});
            }catch (NoSuchMethodException nsme){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (InstantiationException ie2){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (InvocationTargetException ite){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (IllegalAccessException iae2){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }


        }catch (IllegalAccessException iae){
            throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
        }
        return operatorInstance;
    }

    public UnaryOperator generate(UnaryOperatorWrapper wrapper, Class classOfOperand)throws ExpressionOperatorException{
        Class operatorClass = unaryMap.get(new UnaryOperatorGenerationMapKey(wrapper, classOfOperand));
        UnaryOperator operatorInstance;
        try {
            operatorInstance = (UnaryOperator)operatorClass.newInstance();
        }catch (InstantiationException ie){
            try {
                Constructor<?> operatorConstructor = operatorClass.getDeclaredConstructor(new Class[]{String.class, Integer.class});
                operatorInstance = (UnaryOperator) operatorConstructor.newInstance(new Object[]{wrapper.getSymbol(), wrapper.getPriority()});
            }catch (NoSuchMethodException nsme){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (InstantiationException ie2){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (InvocationTargetException ite){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }catch (IllegalAccessException iae2){
                throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
            }
        }catch (IllegalAccessException iae){
            throw new ExpressionOperatorException("操作符" + wrapper.getSymbol() + "未定义。请确定应用程序中有该操作符及对该类型操作数的操作已定义。");
        }
        return operatorInstance;
    }

    public BracketOperator generate(BracketOperatorWrapper wrapper) throws ExpressionOperatorException{
        if (wrapper.getSymbol() == null){
            throw new ExpressionOperatorException("括号操作符为空");
        }
        if ("(".equals(wrapper.getSymbol()) || "（".equals(wrapper.getSymbol())){
            return new LeftBracketOperator();
        }
        else if(")".equals(wrapper.getSymbol()) || "）".equals(wrapper.getSymbol())){
            return new RightBracketOperator();
        }
        else {
            throw new ExpressionOperatorException("括号符书写错误。请保证括号是\'(\'或\')\'");
        }
    }

    public void addUnaryGenerationMap(UnaryOperatorGenerationMapKey key, Class<? extends Operator> operatorClass){
        unaryMap.put(key, operatorClass);
    }

    public void addBinaryGenerationMap(BinaryOperatorGenerationMapKey key, Class<? extends Operator> operatorClass){
        binaryMap.put(key, operatorClass);
    }

    static private void initDefaultGenerator(Map<UnaryOperatorGenerationMapKey, Class<? extends Operator>> unaryMap,
                                             Map<BinaryOperatorGenerationMapKey, Class<? extends Operator>> binaryMap  ){
        //逻辑非
        UnaryOperatorGenerationMapKey booleanNot
                = new UnaryOperatorGenerationMapKey(new UnaryOperatorWrapper("!", 1), Boolean.class);
        unaryMap.put(booleanNot, BooleanNotOperator.class);

        //逻辑与
        BinaryOperatorGenerationMapKey booleanAnd
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("&&", 1), Boolean.class, Boolean.class);
        binaryMap.put(booleanAnd, BooleanAndOperator.class);

        //逻辑或
        BinaryOperatorGenerationMapKey booleanOr
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("||", 1), Boolean.class, Boolean.class);
        binaryMap.put(booleanOr, BooleanOrOperator.class);

        //字符串加
        BinaryOperatorGenerationMapKey stringPlus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), String.class, String.class);
        binaryMap.put(stringPlus, StringPlusOperator.class);

        //字符串等
        BinaryOperatorGenerationMapKey stringEqual
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), String.class, String.class);
        binaryMap.put(stringEqual, StringEqualOperator.class);

        //长整形加字符串
        BinaryOperatorGenerationMapKey longPlusString
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Long.class, String.class);
        binaryMap.put(longPlusString, LongPlusStringOperator.class);

        //字符串加长整形
        BinaryOperatorGenerationMapKey stringPlusLong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), String.class, Long.class);
        binaryMap.put(stringPlusLong, StringPlusLongOperator.class);

        //浮点型加字符串
        BinaryOperatorGenerationMapKey doublePlusString
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Double.class, String.class);
        binaryMap.put(doublePlusString, DoublePlusStringOperator.class);

        //字符串加浮点型
        BinaryOperatorGenerationMapKey stringPlusDouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), String.class, Double.class);
        binaryMap.put(stringPlusDouble, StringPlusDoubleOperator.class);

        //整型大于
        BinaryOperatorGenerationMapKey integerGreaterthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper(">", 2), Integer.class, Integer.class);
        binaryMap.put(integerGreaterthan, IntegerGreaterthanOperator.class);

        //整型小于
        BinaryOperatorGenerationMapKey integerLessthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("<", 2), Integer.class, Integer.class);
        binaryMap.put(integerLessthan, IntegerLessthanOperator.class);

        //整型等于
        BinaryOperatorGenerationMapKey integerEqual
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), Integer.class, Integer.class);
        binaryMap.put(integerEqual, IntegerEqualOperator.class);

        //整形加
        BinaryOperatorGenerationMapKey integerPlus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Integer.class, Integer.class);
        binaryMap.put(integerPlus, IntegerPlusOperator.class);

        //整形减
        BinaryOperatorGenerationMapKey integerMinus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("-", 3), Integer.class, Integer.class);
        binaryMap.put(integerMinus, IntegerMinusOperator.class);

        //整形乘
        BinaryOperatorGenerationMapKey integerMutiple
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("*", 4), Integer.class, Integer.class);
        binaryMap.put(integerMutiple, IntegerMultipleOperator.class);

        //整形除
        BinaryOperatorGenerationMapKey integerDivision
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("/", 4), Integer.class, Integer.class);
        binaryMap.put(integerDivision, IntegerDivisionOperator.class);

        //长整型大于
        BinaryOperatorGenerationMapKey longGreaterthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper(">", 2), Long.class, Long.class);
        binaryMap.put(longGreaterthan, LongGreaterthanOperator.class);

        //长整型小于
        BinaryOperatorGenerationMapKey longLessthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("<", 2), Long.class, Long.class);
        binaryMap.put(longLessthan, LongLessthanOperator.class);

        //长整型等于
        BinaryOperatorGenerationMapKey longEqual
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), Long.class, Long.class);
        binaryMap.put(longEqual, LongEqualOperator.class);

        //长整形加
        BinaryOperatorGenerationMapKey longPlus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Long.class, Long.class);
        binaryMap.put(longPlus, LongPlusOperator.class);

        //长整形减
        BinaryOperatorGenerationMapKey longMinus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("-", 3), Long.class, Long.class);
        binaryMap.put(longMinus, LongMinusOperator.class);

        //长整形乘
        BinaryOperatorGenerationMapKey longMutiple
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("*", 4), Long.class, Long.class);
        binaryMap.put(longMutiple, LongMultipleOperator.class);

        //长整形除
        BinaryOperatorGenerationMapKey longDivision
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("/", 4), Long.class, Long.class);
        binaryMap.put(longDivision, LongDivisionOperator.class);

        //浮点数大于
        BinaryOperatorGenerationMapKey doubleGreaterthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper(">", 2), Double.class, Double.class);
        binaryMap.put(doubleGreaterthan, DoubleGreaterthanOperator.class);

        //浮点数小于
        BinaryOperatorGenerationMapKey doubleLessthan
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("<", 2), Double.class, Double.class);
        binaryMap.put(doubleLessthan, DoubleLessthanOperator.class);

        //浮点数等于
        BinaryOperatorGenerationMapKey doubleEqual
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), Double.class, Double.class);
        binaryMap.put(doubleEqual, DoubleEqualOperator.class);

        //浮点数加
        BinaryOperatorGenerationMapKey doublePlus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Double.class, Double.class);
        binaryMap.put(doublePlus, DoublePlusOperator.class);

        //浮点数减
        BinaryOperatorGenerationMapKey doubleMinus
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("-", 3), Double.class, Double.class);
        binaryMap.put(doubleMinus, DoubleMinusOperator.class);

        //浮点数乘
        BinaryOperatorGenerationMapKey doubleMutiple
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("*", 4), Double.class, Double.class);
        binaryMap.put(doubleMutiple, DoubleMultipleOperator.class);

        //浮点数除
        BinaryOperatorGenerationMapKey doubleDivision
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("/", 4), Double.class, Double.class);
        binaryMap.put(doubleDivision, DoubleDivisionOperator.class);

        //浮点数大于长整型
        BinaryOperatorGenerationMapKey doubleGreaterthanlong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper(">", 2), Double.class, Long.class);
        binaryMap.put(doubleGreaterthanlong, DoubleGreaterthanLongOperator.class);

        //浮点数小于长整型
        BinaryOperatorGenerationMapKey doubleLessthanlong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("<", 2), Double.class, Long.class);
        binaryMap.put(doubleLessthanlong, DoubleLessthanLongOperator.class);

        //浮点数等于长整型
        BinaryOperatorGenerationMapKey doubleEquallong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), Double.class, Long.class);
        binaryMap.put(doubleEquallong, DoubleEqualLongOperator.class);

        //浮点数加长整型
        BinaryOperatorGenerationMapKey doublePluslong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Double.class, Long.class);
        binaryMap.put(doublePluslong, DoublePlusLongOperator.class);

        //浮点数减长整型
        BinaryOperatorGenerationMapKey doubleMinuslong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("-", 3), Double.class, Long.class);
        binaryMap.put(doubleMinuslong, DoubleMinusLongOperator.class);

        //浮点数乘长整型
        BinaryOperatorGenerationMapKey doubleMutiplelong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("*", 4), Double.class, Long.class);
        binaryMap.put(doubleMutiplelong, DoubleMultipleLongOperator.class);

        //浮点数除长整型
        BinaryOperatorGenerationMapKey doubleDivisionlong
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("/", 4), Double.class, Long.class);
        binaryMap.put(doubleDivisionlong, DoubleDivisionLongOperator.class);

        //长整型大于浮点数
        BinaryOperatorGenerationMapKey longGreaterthandouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper(">", 2), Long.class, Double.class);
        binaryMap.put(longGreaterthandouble, LongGreaterthanDoubleOperator.class);

        //长整型小于浮点数
        BinaryOperatorGenerationMapKey longLessthandouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("<", 2), Long.class, Double.class);
        binaryMap.put(longLessthandouble, LongLessthanDoubleOperator.class);

        //长整型等于浮点数
        BinaryOperatorGenerationMapKey longEqualdouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("==", 2), Long.class, Double.class);
        binaryMap.put(longEqualdouble, LongEqualDoubleOperator.class);

        //长整形加浮点数
        BinaryOperatorGenerationMapKey longPlusdouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("+", 3), Long.class, Double.class);
        binaryMap.put(longPlusdouble, LongPlusDoubleOperator.class);

        //长整形减浮点数
        BinaryOperatorGenerationMapKey longMinusdouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("-", 3), Long.class, Double.class);
        binaryMap.put(longMinusdouble, LongMinusDoubleOperator.class);

        //长整形乘浮点数
        BinaryOperatorGenerationMapKey longMutipledouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("*", 4), Long.class, Double.class);
        binaryMap.put(longMutipledouble, LongMultipleDoubleOperator.class);

        //长整形除浮点数
        BinaryOperatorGenerationMapKey longDivisiondouble
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("/", 4), Long.class, Double.class);
        binaryMap.put(longDivisiondouble, LongDivisionDoubleOperator.class);

        //正则表达式匹配
        BinaryOperatorGenerationMapKey stringMatchRegex
                = new BinaryOperatorGenerationMapKey(new BinaryOperatorWrapper("~", 5), String.class, Regex.class);
        binaryMap.put(stringMatchRegex, StringRegexOperator.class);

        //向量相加
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量相减
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("-", 3), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量相乘
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("*", 4), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量相除
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("/", 4), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量相与
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("&&", 1), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量相或
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("||", 1), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量非
        unaryMap.put(new UnaryOperatorGenerationMapKey(
                        new UnaryOperatorWrapper("!", 1), Vector.class),
                UnaryVectorOperator.class);

        //向量相等
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量大于
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper(">", 2), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量小于
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("<", 2), Vector.class, Vector.class),
                BinaryVectorOperator.class);

        //向量加字符串
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Vector.class, String.class),
                BinaryVectorWithOtherOperator.class);

        //向量等于字符串
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Vector.class, String.class),
                BinaryVectorWithOtherOperator.class);

        //向量加长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量减长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("-", 3), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量乘长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("*", 4), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量除长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("/", 4), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量大于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper(">", 2), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量小于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("<", 2), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量小于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Vector.class, Long.class),
                BinaryVectorWithOtherOperator.class);

        //向量加浮点
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量减浮点
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("-", 3), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量乘浮点
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("*", 4), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量除浮点
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("/", 4), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量大于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper(">", 2), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量小于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("<", 2), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量小于长整型
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Vector.class, Double.class),
                BinaryVectorWithOtherOperator.class);

        //向量与bool
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("&&", 1), Vector.class, Boolean.class),
                BinaryVectorWithOtherOperator.class);

        //向量或bool
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("||", 1), Vector.class, Boolean.class),
                BinaryVectorWithOtherOperator.class);


        //字符串加向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), String.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //字符串等于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), String.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型加向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型减向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("-", 3), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型乘向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("*", 4), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型除向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("/", 4), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型大于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper(">", 2), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型小于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("<", 2), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型等于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Long.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //浮点加向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("+", 3), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //浮点减向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("-", 3), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //浮点乘向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("*", 4), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //浮点除向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("/", 4), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型大于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper(">", 2), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型小于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("<", 2), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //长整型等于向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("==", 2), Double.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //bool与向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("&&", 1), Boolean.class, Vector.class),
                BinaryOtherWithVectorOperator.class);

        //bool或向量
        binaryMap.put(new BinaryOperatorGenerationMapKey(
                        new BinaryOperatorWrapper("||", 1), Boolean.class, Vector.class),
                BinaryOtherWithVectorOperator.class);



    }

}
