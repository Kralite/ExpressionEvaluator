package com.killover.expressionevaluator.operator.string;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.Operator;

/**
 * Created by killover on 2016/11/1.
 */
public class StringEqualOperator extends BinaryOperator<String, String, Boolean> {

    private static final int OPERATOR_PRIORITY_STRING_EQUAL = 2;

    public StringEqualOperator(){super(OPERATOR_PRIORITY_STRING_EQUAL);}

    @Override
    public Operand<Boolean> operate(Operand<String> operand1, Operand<String> operand2){
        return new BooleanOperand(operand1.value().equals(operand2.value()));
    }

    public static void  main(String[] args){
        Operator o = new StringEqualOperator();
        System.out.println(o.getPriority());
    }
}
