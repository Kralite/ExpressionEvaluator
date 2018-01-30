package com.killover.expressionevaluator.operator.doubleNumber;

import com.killover.expressionevaluator.operand.DoubleOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class DoubleDivisionOperator extends BinaryOperator<Double, Double, Double> {

    public DoubleDivisionOperator(){
        super(4);
    }

    @Override
    public Operand<Double> operate(Operand<Double> operand1, Operand<Double> operand2){
        return new DoubleOperand(operand1.value() / operand2.value());
    }
}
