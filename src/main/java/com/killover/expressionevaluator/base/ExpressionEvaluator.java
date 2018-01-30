package com.killover.expressionevaluator.base;

import com.killover.expressionevaluator.exception.ExpressionEmptyException;
import com.killover.expressionevaluator.exception.ExpressionFormatException;
import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.*;
import com.killover.expressionevaluator.operator.generator.OperatorGenerator;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.BracketOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.UnaryOperatorWrapper;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by killover on 2016/10/31.
 */
public abstract class ExpressionEvaluator {

    static public Operand evaluate(List<ExpressionElement> expressionElements, OperatorGenerator operatorGenerator) throws ExpressionEmptyException,ExpressionFormatException, ExpressionOperatorException{

        Stack<Operand> operandStack = new Stack<Operand>(); //值栈
        Stack<Operator> operatorStack = new Stack<Operator>(); //操作数栈

        if (expressionElements == null || expressionElements.size() == 0){
            throw new ExpressionEmptyException("表达式为空");
        }

        try {
            for (int i=0; i<expressionElements.size(); ++i){
                ExpressionElement element = expressionElements.get(i);

                //如果是操作数
                if (element instanceof Operand){
                    operandStack.push((Operand) element);
                }

                //如果是操作符
                else if (element instanceof Operator){
                    if (element instanceof BracketOperatorWrapper) {
                        element = operatorGenerator.generate((BracketOperatorWrapper)element);
                    }

                    if (element instanceof LeftBracketOperator){
                        operatorStack.push((Operator)element);
                        continue;
                    }


                    Operator operatorOutStack = (Operator) element;
                    Operator operatorInStack = operatorStack.peek();

                    int compareOperator = compareOperatorPriority(operatorInStack, operatorOutStack);
                    //栈内操作符优先级小于栈外操作符，栈外操作符入栈
                    if (compareOperator < 0){
                        operatorStack.push(operatorOutStack);
                    }
                    //栈外操作符是右括号，栈内操作符是左括号
                    else if (compareOperator == 0){
                        operatorStack.pop();
                        continue;
                    }
                    //栈内操作符优先级大于栈外操作符，取操作数和栈内操作符进行运算，然后再取栈顶操作符和栈外操作符进行比较，
                    //直到遇到比栈外操作符优先级低的栈内操作符，然后将该栈外操作符入栈
                    else {
                        for ( ; compareOperator > 0; operatorInStack = operatorStack.peek(),
                                                    compareOperator = compareOperatorPriority(operatorInStack, operatorOutStack)){
                            //一元操作符
                            if (operatorInStack instanceof UnaryOperatorWrapper){
                                Operand operand = operandStack.pop();
                                UnaryOperator unaryOperatorInStack = operatorGenerator.generate((UnaryOperatorWrapper)operatorInStack, operand.value().getClass());
                                Operand resultOperand = unaryOperatorInStack.operate(operand);
                                operandStack.push(resultOperand);
                                operatorStack.pop();
                            }
                            else if (operatorInStack instanceof BinaryOperatorWrapper){
                                Operand operand2 = operandStack.pop();
                                Operand operand1 = operandStack.pop();
                                BinaryOperator binaryOperatorInStack = operatorGenerator.generate((BinaryOperatorWrapper)operatorInStack, operand1.value().getClass(), operand2.value().getClass());
                                Operand resultOperand = binaryOperatorInStack.operate(operand1, operand2);
                                operandStack.push(resultOperand);
                                operatorStack.pop();
                            }
                            else if (operatorInStack instanceof UnaryOperator){
                                UnaryOperator unaryOperatorInStack = (UnaryOperator) operatorInStack;
                                Operand operand = operandStack.pop();
                                Operand resultOperand = unaryOperatorInStack.operate(operand);
                                operandStack.push(resultOperand);
                                operatorStack.pop();
                            }
                            //二元操作符
                            else if(operatorInStack instanceof BinaryOperator){
                                BinaryOperator binaryOperatorInStack = (BinaryOperator) operatorInStack;
                                Operand operand2 = operandStack.pop();
                                Operand operand1 = operandStack.pop();
                                Operand resultOperand = binaryOperatorInStack.operate(operand1, operand2);
                                operandStack.push(resultOperand);
                                operatorStack.pop();
                            }
                            //其他类型的操作符
                            else {
                                throw new ExpressionOperatorException("表达式类别未定：一元或二元运算符，建议直接继承UnaryOperator或BinaryOperator，或者自定义一个新的n元运算符类型");
                            }
                        }

                        //这是括号的情况，要将栈内的左括号出栈
                        if (compareOperator == 0){
                            operatorStack.pop();
                        }
                        //栈外操作符压栈
                        else if (compareOperator < 0){
                            operatorStack.push(operatorOutStack);
                        }
                    }

                }
                else {
                    //可在扩展ExpressionElement的类型（操作数和操作符之外）之后放在这里
                }
            }

            //最后留在栈顶的数字就是最后的结果
            Operand result = operandStack.pop();

            if (!operatorStack.empty() || !operandStack.empty()){
                throw new ExpressionFormatException("表达式格式错误！", expressionElements.toString());
            }

            return result;

        }catch (EmptyStackException ese){
            throw new ExpressionFormatException("表达式格式错误！", expressionElements.toString());
        }


    }

    /**
     * 根据两个操作符的优先级，需要operatorOutStack入栈，则返回-1；需要先拿出operatorInStack计算后再取出栈顶操作数接着和operatorOutStack比较，则返回1（直到返回-1才将operatorOutStack入栈）;
     * 如果operatorOutStack是右括号，遇到所有操作数都返回1；则要遇到左括号为止才结束，返回0。
     * @param operatorInStack 栈顶操作符
     * @param operatorOutStack 栈外待比较操作符
     * @return
     */
    static private int compareOperatorPriority(Operator operatorInStack, Operator operatorOutStack){

        if (operatorOutStack instanceof RightBracketOperator){
            if (operatorInStack instanceof LeftBracketOperator){
                return 0;
            }
            else {
                return 1;
            }
        }

        int pIn = operatorInStack.getPriority();
        int pOut = operatorOutStack.getPriority();

        if (pIn == pOut){
            return 1;
        }
        else if(pIn < pOut){
            return -1;
        }
        else {
            return 1;
        }


    }


}
