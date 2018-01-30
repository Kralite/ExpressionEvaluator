package com.killover.expressionevaluator.operator.vector;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.VectorOperand;
import com.killover.expressionevaluator.operand.model.Vector;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.generator.OperatorGenerator;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;

/**
 * Created by killover on 2016/11/16.
 */
public class BinaryOtherWithVectorOperator extends BinaryOperator<Object, Vector , Vector> {

    static private OperatorGenerator generator = new OperatorGenerator();
    private BinaryOperatorWrapper operatorWrapper;

    public BinaryOtherWithVectorOperator(String symbol, Integer priority){
        super(priority);
        this.operatorWrapper = new BinaryOperatorWrapper(symbol, priority);
    }

    @Override
    public Operand<Vector> operate(Operand<Object> operand1, Operand<Vector> operand2) throws ExpressionOperatorException {
        if (operatorWrapper == null) {
            throw new ExpressionOperatorException("请为BinaryVectorOperator的子类传入OperatorWrapper!");
        }

        Vector resultVector = new Vector();

        Vector v2 = operand2.value();

        for (int i = 0; i < v2.size(); ++i) {
            Operand v2element = v2.get(i);

            BinaryOperator plusOperator = generator.generate(operatorWrapper, operand1.value().getClass(), v2element.value().getClass());

            Operand thisResult = plusOperator.operate(operand1, v2element);

            resultVector.add(thisResult);
        }

        return new VectorOperand(resultVector);
    }


    public void setOperatorWrapper(BinaryOperatorWrapper operatorWrapper) {
        this.operatorWrapper = operatorWrapper;
    }
}