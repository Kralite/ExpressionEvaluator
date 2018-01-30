package com.killover.expressionevaluator.parser;

import com.killover.expressionevaluator.base.ExpressionElement;
import com.killover.expressionevaluator.base.ExpressionParser;
import com.killover.expressionevaluator.exception.ExpressionParseException;
import com.killover.expressionevaluator.operand.*;
import com.killover.expressionevaluator.operand.model.RegViralbleName;
import com.killover.expressionevaluator.operand.model.Regex;
import com.killover.expressionevaluator.operand.model.Variable;
import com.killover.expressionevaluator.operand.model.Vector;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.BracketOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.OperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.UnaryOperatorWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by killover on 2016/11/1.
 */
public class StringExpressionParser extends ExpressionParser<String>{

    private Map<String, OperatorWrapper> operators;

    static private final String SPLIT_REGEX = "regex\\(\".*?\"\\)|v\\(\".*?\"\\)|regV\\(\".*?\"\\)|vector\\(.*?\\)|\\\"(.*?)\\\"|\\'(.*?)\\'|\\d+(\\.\\d+)?(e-?\\d+(\\.\\d+)?)?|\\+|-|\\*|/|&&|!|\\|\\||\\(|\\)|~|>|<|==|\\w+";

    public StringExpressionParser(Map<String, OperatorWrapper> operatorMap){
        this.operators = operatorMap;
    }

    @Override
    public List<ExpressionElement> parse(String expression) throws ExpressionParseException{
        if (expression == null || "".equals(expression.trim())){
            throw new ExpressionParseException("表达式出错：表达式为空");
        }
        expression = "("+expression+")";
        List<String> strElements = splitExpression(expression);
        List<ExpressionElement> elements = new ArrayList<ExpressionElement>();

        for (int i=0; i<strElements.size(); ++i){
            elements.add(generateElement(strElements.get(i)));
        }
        return elements;

    }



    static public Map<String, OperatorWrapper> getDefaltOperatorWrapper(){

        Map<String, OperatorWrapper> wrapperMap = new HashMap<String, OperatorWrapper>();
        wrapperMap.put("(", new BracketOperatorWrapper("("));
        wrapperMap.put(")", new BracketOperatorWrapper(")"));
        wrapperMap.put("&&", new BinaryOperatorWrapper("&&", 1));
        wrapperMap.put("||", new BinaryOperatorWrapper("||", 1));
        wrapperMap.put("==", new BinaryOperatorWrapper("==", 2));
        wrapperMap.put("!", new UnaryOperatorWrapper("!", 1));
        wrapperMap.put(">", new BinaryOperatorWrapper(">", 2));
        wrapperMap.put("<", new BinaryOperatorWrapper("<", 2));
        wrapperMap.put("+", new BinaryOperatorWrapper("+", 3));
        wrapperMap.put("-", new BinaryOperatorWrapper("-", 3));
        wrapperMap.put("*", new BinaryOperatorWrapper("*", 4));
        wrapperMap.put("/", new BinaryOperatorWrapper("/", 4));
        wrapperMap.put("~", new BinaryOperatorWrapper("~", 5));//正则表达式

        return wrapperMap;
    }

    static private List<String> splitExpression(String expression){

        List<String> expressionList = new ArrayList<String>();

        Pattern pattern = Pattern.compile(SPLIT_REGEX);
        //Matcher matcher = pattern.matcher("a+(b*32.5) \"hjhlkj\"&& g~regex(\"dsfj+gh~ x.3\") -5/dfjk||dssgsg!324235=='djkf'");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()){
            //int s = matcher.start();
            //int e = matcher.end();
            String eleStr = matcher.group();
            expressionList.add(eleStr);
        }


        return expressionList;
    }

    protected ExpressionElement generateElement(String strElement) throws ExpressionParseException{

        strElement = strElement.trim();

        //正则表达式
        if (strElement.startsWith("regex(") && strElement.endsWith(")")){
            String regexBody = strElement.substring(6, strElement.length()-1);
            if ((regexBody.startsWith("\"") && regexBody.endsWith("\"")) || (regexBody.startsWith("\'") && regexBody.endsWith("\'"))){
                Regex regex = new Regex(regexBody.substring(1, regexBody.length()-1));
                return new RegexOperand(regex);
            }
            else {
                throw new ExpressionParseException("正则表达式内容请以单引号或双引号包围，且表达式中不应有空格");
            }
        }

        //变量
        if (strElement.startsWith("v(") && strElement.endsWith(")")) {
            String viralbleBody = strElement.substring(2, strElement.length()-1);
            if ( (viralbleBody.startsWith("\"") && viralbleBody.endsWith("\"")) || (viralbleBody.startsWith("\'") && viralbleBody.endsWith("\'")) ){
                Variable variable = new Variable(viralbleBody.substring(1, viralbleBody.length()-1));
                return new VariableOperand(variable);
            }
        }

        //向量
        if (strElement.startsWith("vector(") && strElement.endsWith(")")) {
            String vectorBody = strElement.substring(7, strElement.length()-1);
            String[] vectorElements = vectorBody.split(",");
            Vector vector = new Vector();
            for (int i=0; i<vectorElements.length; ++i) {
                Operand operand = generateOperandForVector(vectorElements[i].trim());
                vector.add(operand);
            }
            return new VectorOperand(vector);
        }

        //对变量名正则匹配的变量
        if (strElement.startsWith("regV(") && strElement.endsWith(")")) {
            String vBody = strElement.substring(5, strElement.length()-1);
            if ( (vBody.startsWith("\"") && vBody.endsWith("\"")) || (vBody.startsWith("\'") && vBody.endsWith("\'")) ){
                RegViralbleName regV = new RegViralbleName(vBody.substring(1, vBody.length()-1));
                return new RegVirableNameOperand(regV);
            }
        }


        //操作符

        if (getOperators().get(strElement)!=null){
            return getOperators().get(strElement);
        }

        //字符串操作数
        if ( (strElement.startsWith("\"") && strElement.endsWith("\"")) || (strElement.startsWith("\'") && strElement.endsWith("\'"))){
            return new StringOperand(strElement.substring(1, strElement.length()-1));
        }
        //长整形操作符
        try {
            Long longOperand = Long.valueOf(strElement);
            return new LongOperand(longOperand);
        }catch (NumberFormatException e){
        }
        //浮点数操作符
        try {
            Double doubleOperand = Double.valueOf(strElement);
            return new DoubleOperand(doubleOperand);
        }catch (NumberFormatException e){
        }

        Variable lolField = new Variable(strElement);
        return new VariableOperand(lolField);
    }

    public Map<String, OperatorWrapper> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, OperatorWrapper> operators) {
        this.operators = operators;
    }

    static private Operand generateOperandForVector(String operandStr) {
        //字符串操作数
        if ( (operandStr.startsWith("\"") && operandStr.endsWith("\"")) || (operandStr.startsWith("\'") && operandStr.endsWith("\'"))){
            return new StringOperand(operandStr.substring(1, operandStr.length()-1));
        }
        //长整形操作符
        try {
            Long longOperand = Long.valueOf(operandStr);
            return new LongOperand(longOperand);
        }catch (NumberFormatException e){
        }
        //浮点数操作符
        try {
            Double doubleOperand = Double.valueOf(operandStr);
            return new DoubleOperand(doubleOperand);
        }catch (NumberFormatException e){
        }
        //bool操作符
        if ( operandStr.equals("true") ){
            return new BooleanOperand(true);
        }
        if (operandStr.endsWith("false")){
            return new BooleanOperand(false);
        }

        return null;
    }

    /**
     * 根据字符串生成操作数
     * @param str
     * @return
     */
    public Operand generateOperand(String str){

        if (str == null) {
            return null;
        }

        if (str.trim().equals("")){
            return new StringOperand("");
        }

        if ("true".equals(str)){
            return new BooleanOperand(true);
        }
        else if ("false".equals(str)){
            return new BooleanOperand(false);
        }

        try {
            Long longValue = Long.valueOf(str);
            Operand<Long> longOperand = new LongOperand(longValue);
            return longOperand;
        }catch (NumberFormatException e){
        }

        try {
            Double doubleValue = Double.valueOf(str);
            Operand<Double> doubleOperand = new DoubleOperand(doubleValue);
            return doubleOperand;
        }catch (NumberFormatException e){
        }

        return new StringOperand(str);
    }

}
