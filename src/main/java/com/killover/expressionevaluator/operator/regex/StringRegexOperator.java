package com.killover.expressionevaluator.operator.regex;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.model.Regex;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/4.
 */
public class StringRegexOperator extends BinaryOperator<String, Regex, Boolean> {

    public StringRegexOperator(){
        super(5);
    }

    @Override
    public Operand<Boolean> operate(Operand<String> operand1, Operand<Regex> operand2){
        return new BooleanOperand(operand2.value().match(operand1.value()));
    }
}
