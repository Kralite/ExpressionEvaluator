package com.killover.expressionevaluator.operator.variable;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.IntegerOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.model.Variable;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.integer.IntegerPlusOperator;

/**
 * Created by killover on 2016/11/2.
 */
public class VariableIntegerPlusOperator extends BinaryOperator<Variable, Integer, Integer> {

    public VariableIntegerPlusOperator(){
        super(3);
    }

    @Override
    public Operand<Integer> operate(Operand<Variable> operand1, Operand<Integer> operand2) throws ExpressionOperatorException{
        Object value1NoType = operand1.value().getValue();
        if (value1NoType instanceof Integer){
            Operand<Integer> operandTmp1 = new IntegerOperand((Integer)value1NoType);
            return new IntegerPlusOperator().operate(operandTmp1, operand2);
        }
        else {
            throw new ExpressionOperatorException("");
        }
    }
}
