package com.killover.expressionevaluator.operand;

import com.killover.expressionevaluator.operand.model.Variable;

/**
 * Created by killover on 2016/11/2.
 */
public class VariableOperand extends Operand<Variable> {
    public VariableOperand (Variable value){
        super(value);
    }
}
