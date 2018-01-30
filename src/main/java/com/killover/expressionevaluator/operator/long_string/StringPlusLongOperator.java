package com.killover.expressionevaluator.operator.long_string;

import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.StringOperand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/6.
 */
public class StringPlusLongOperator extends BinaryOperator<String, Long, String> {
    public StringPlusLongOperator(){
        super(3);
    }

    @Override
    public Operand<String> operate(Operand<String> operand1, Operand<Long> operand2){
        return new StringOperand(operand1.value()+operand2.value().toString());
    }
}
