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
public class BinaryVectorOperator extends BinaryOperator<Vector, Vector, Vector> {

    static private OperatorGenerator generator = new OperatorGenerator();
    private BinaryOperatorWrapper operatorWrapper;

    public BinaryVectorOperator(String symbol, Integer priority){
        super(priority);
        this.operatorWrapper = new BinaryOperatorWrapper(symbol, priority);
    }

    @Override
    public Operand<Vector> operate(Operand<Vector> operand1, Operand<Vector> operand2)  throws ExpressionOperatorException {

        if (operatorWrapper == null) {
            throw new ExpressionOperatorException("请为BinaryVectorOperator的子类传入OperatorWrapper!");
        }

        Vector resultVector = new Vector();

        Vector v1 = operand1.value();
        Vector v2 = operand2.value();

        if (v1.size() != v2.size()){
            throw new ExpressionOperatorException("两个向量维度不等不能相加: v1.size = " + v1.size() + "; v2.size = " + v2.size());
        }

        for (int i = 0; i < v1.size(); ++i) {
            Operand v1element = v1.get(i);
            Operand v2element = v2.get(i);

            BinaryOperator plusOperator = generator.generate(operatorWrapper, v1element.value().getClass(), v2element.value().getClass());

            Operand thisResult = plusOperator.operate(v1element, v2element);

            resultVector.add(thisResult);
        }

        return new VectorOperand(resultVector);

    }


    public void setOperatorWrapper(BinaryOperatorWrapper operatorWrapper) {
        this.operatorWrapper = operatorWrapper;
    }
}
