package com.killover.expressionevaluator.operator.generator;

import com.killover.expressionevaluator.operator.wrapper.BinaryOperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.OperatorWrapper;

/**
 * Created by killover on 2016/11/1.
 */
public class BinaryOperatorGenerationMapKey {
    private OperatorWrapper wrapper;
    private Class classOfOperand1;
    private Class classOfOperand2;

    public BinaryOperatorGenerationMapKey(BinaryOperatorWrapper wrapper, Class classOfOperand1, Class classOfOperand2) {
        this.wrapper = wrapper;
        this.classOfOperand1 = classOfOperand1;
        this.classOfOperand2 = classOfOperand2;
    }

    public OperatorWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(OperatorWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Class getClassOfOperand1() {
        return classOfOperand1;
    }

    public void setClassOfOperand1(Class classOfOperand1) {
        this.classOfOperand1 = classOfOperand1;
    }

    public Class getClassOfOperand2() {
        return classOfOperand2;
    }

    public void setClassOfOperand2(Class classOfOperand2) {
        this.classOfOperand2 = classOfOperand2;
    }

    @Override
    public int hashCode(){
        if (wrapper==null || wrapper.getSymbol()==null || classOfOperand1 == null || classOfOperand2 == null){
            return 0;
        }
        return wrapper.getSymbol().hashCode()  + classOfOperand1.toString().hashCode() + classOfOperand2.toString().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o==null || this.getClass() != o.getClass()){
            return false;
        }

        BinaryOperatorGenerationMapKey key = (BinaryOperatorGenerationMapKey) o;

        if ((wrapper.getSymbol() == null && key.getWrapper().getSymbol() != null) || (wrapper.getSymbol() != null && key.getWrapper().getSymbol() == null)){
            return false;
        }
        if ( (classOfOperand1 == null && key.getClassOfOperand1() != null) || (classOfOperand1 != null && key.getClassOfOperand1() == null) ){
            return false;
        }
        if ( (classOfOperand2 == null && key.getClassOfOperand1() != null) || (classOfOperand2 != null && key.getClassOfOperand1() == null) ){
            return false;
        }

        if ( (wrapper.getSymbol() == key.getWrapper().getSymbol() || wrapper.getSymbol().equals(key.getWrapper().getSymbol()) )
                && ( classOfOperand1 == key.getClassOfOperand1() || classOfOperand1.toString().equals(key.getClassOfOperand1().toString()) )
                && ( classOfOperand2 == key.getClassOfOperand2() || classOfOperand2.toString().equals(key.getClassOfOperand2().toString()) ) ){
            return true;
        }
        else {
            return false;
        }
    }
}
