package com.killover.expressionevaluator.operator.long_string;

import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.StringOperand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/6.
 */
public class LongPlusStringOperator  extends BinaryOperator<Long, String, String> {
    public LongPlusStringOperator(){
        super(3);
    }

    @Override
    public Operand<String> operate(Operand<Long> operand1, Operand<String> operand2){
        return new StringOperand(operand1.value().toString()+operand2.value());
    }
}

