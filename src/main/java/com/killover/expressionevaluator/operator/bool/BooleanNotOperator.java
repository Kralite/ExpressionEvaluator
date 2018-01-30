package com.killover.expressionevaluator.operator.bool;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.UnaryOperator;

/**
 * Created by killover on 2016/11/1.
 */
public class BooleanNotOperator extends UnaryOperator<Boolean, Boolean> {
    private static final int OPERATOR_PRIORITY_BOOLEAN_NOT = 1;

    public BooleanNotOperator(){
        super(OPERATOR_PRIORITY_BOOLEAN_NOT);
    }

    @Override
    public Operand<Boolean> operate(Operand<Boolean> operand){
        return new BooleanOperand(! (operand.value()));
    }


}
