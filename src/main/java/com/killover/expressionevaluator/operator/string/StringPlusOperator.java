package com.killover.expressionevaluator.operator.string;

import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.StringOperand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/10/31.
 */
public class StringPlusOperator extends BinaryOperator<String, String, String> {

    private final static int OPERATOR_PRIORITY_STRING_PLUS = 3;

    public StringPlusOperator(){
        super(OPERATOR_PRIORITY_STRING_PLUS);
    }

    @Override
    public Operand<String> operate(Operand<String> operand1, Operand<String> operand2){
        return new StringOperand(operand1.value() + operand2.value());
    }
}
