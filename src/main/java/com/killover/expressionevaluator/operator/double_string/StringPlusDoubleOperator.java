package com.killover.expressionevaluator.operator.double_string;

import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.StringOperand;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.Operator;

/**
 * Created by killover on 2016/11/6.
 */
public class StringPlusDoubleOperator extends BinaryOperator<String, Double, String> {
    public StringPlusDoubleOperator(){
        super(3);
    }

    @Override
    public Operand<String> operate(Operand<String> operand1, Operand<Double> operand2){
        return new StringOperand(operand1.value()+operand2.value().toString());
    }
}
