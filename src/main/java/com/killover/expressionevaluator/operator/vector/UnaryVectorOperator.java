package com.killover.expressionevaluator.operator.vector;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operand.VectorOperand;
import com.killover.expressionevaluator.operand.model.Vector;
import com.killover.expressionevaluator.operator.BinaryOperator;
import com.killover.expressionevaluator.operator.UnaryOperator;
import com.killover.expressionevaluator.operator.generator.OperatorGenerator;
import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.UnaryOperatorWrapper;

/**
 * Created by killover on 2016/11/17.
 */
public class UnaryVectorOperator extends UnaryOperator<Vector, Vector> {

    static private OperatorGenerator generator = new OperatorGenerator();
    private UnaryOperatorWrapper operatorWrapper;

    public UnaryVectorOperator(String symbol, Integer priority){
        super(priority);
        this.operatorWrapper = new UnaryOperatorWrapper(symbol, priority);
    }

    @Override
    public Operand<Vector> operate(Operand<Vector> operand)  throws ExpressionOperatorException {

        if (operatorWrapper == null) {
            throw new ExpressionOperatorException("请为BinaryVectorOperator的子类传入OperatorWrapper!");
        }

        Vector resultVector = new Vector();

        Vector v = operand.value();


        for (int i = 0; i < v.size(); ++i) {
            Operand vElement = v.get(i);

            UnaryOperator operator = generator.generate(operatorWrapper, vElement.value().getClass());

            Operand thisResult = operator.operate(vElement);

            resultVector.add(thisResult);
        }

        return new VectorOperand(resultVector);

    }


    public void setOperatorWrapper(UnaryOperatorWrapper operatorWrapper) {
        this.operatorWrapper = operatorWrapper;
    }
}
