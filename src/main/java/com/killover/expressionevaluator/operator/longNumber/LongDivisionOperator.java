package com.killover.expressionevaluator.operator.longNumber;

import com.killover.expressionevaluator.operand.DoubleOperand;
import com.killover.expressionevaluator.operand.LongOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class LongDivisionOperator extends BinaryOperator<Long, Long, Double> {

    public LongDivisionOperator(){
        super(4);
    }

    @Override
    public Operand<Double> operate(Operand<Long> operand1, Operand<Long> operand2){
        return new DoubleOperand(Double.valueOf(operand1.value()) / Double.valueOf(operand2.value()));
    }
}
