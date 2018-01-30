package com.killover.expressionevaluator.operator.vector;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.VectorOperand;
import com.killover.expressionevaluator.operand.model.Vector;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.Operator;
import com.killover.expressionevaluator.operator.generator.OperatorGenerator;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;

/**
 * Created by killover on 2016/11/16.
 */
public class BinaryVectorWithOtherOperator extends BinaryOperator<Vector, Object , Vector>{

    static private OperatorGenerator generator = new OperatorGenerator();
    private BinaryOperatorWrapper operatorWrapper;

    public BinaryVectorWithOtherOperator(String symbol, Integer priority){
        super(priority);
        this.operatorWrapper = new BinaryOperatorWrapper(symbol, priority);
    }

    @Override
    public Operand<Vector> operate(Operand<Vector> operand1, Operand<Object> operand2) throws ExpressionOperatorException {
        if (operatorWrapper == null) {
            throw new ExpressionOperatorException("请为BinaryVectorOperator的子类传入OperatorWrapper!");
        }

        Vector resultVector = new Vector();

        Vector v1 = operand1.value();

        for (int i = 0; i < v1.size(); ++i) {
            Operand v1element = v1.get(i);

            BinaryOperator plusOperator = generator.generate(operatorWrapper, v1element.value().getClass(), operand2.value().getClass());

            Operand thisResult = plusOperator.operate(v1element, operand2);

            resultVector.add(thisResult);
        }

        return new VectorOperand(resultVector);
    }


    public void setOperatorWrapper(BinaryOperatorWrapper operatorWrapper) {
        this.operatorWrapper = operatorWrapper;
    }
}
