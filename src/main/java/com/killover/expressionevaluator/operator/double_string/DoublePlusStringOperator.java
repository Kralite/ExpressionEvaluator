package com.killover.expressionevaluator.operator.double_string;

import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.StringOperand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/6.
 */
public class DoublePlusStringOperator extends BinaryOperator<Double, String, String> {
    public DoublePlusStringOperator(){
        super(3);
    }

    @Override
    public Operand<String> operate(Operand<Double> operand1, Operand<String> operand2){
        return new StringOperand(operand1.value().toString()+operand2.value());
    }
}
