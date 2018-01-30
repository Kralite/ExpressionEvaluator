package com.killover.expressionevaluator.operator.generator;

import com.killover.expressionevaluator.operator.wrapper.OperatorWrapper;
import com.killover.expressionevaluator.operator.wrapper.UnaryOperatorWrapper;

/**
 * Created by killover on 2016/11/1.
 */
public class UnaryOperatorGenerationMapKey {
    private OperatorWrapper wrapper;
    private Class classOfOperand;

    public UnaryOperatorGenerationMapKey(UnaryOperatorWrapper wrapper, Class classOfOperand) {
        this.wrapper = wrapper;
        this.classOfOperand = classOfOperand;
    }

    public OperatorWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(OperatorWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Class getClassOfOperand() {
        return classOfOperand;
    }

    public void setClassOfOperand1(Class classOfOperand) {
        this.classOfOperand = classOfOperand;
    }

    @Override
    public int hashCode(){
        if (wrapper==null || wrapper.getSymbol()==null || classOfOperand == null){
            return 0;
        }
        return wrapper.getSymbol().hashCode() + classOfOperand.toString().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o==null || this.getClass() != o.getClass()){
            return false;
        }

        UnaryOperatorGenerationMapKey key = (UnaryOperatorGenerationMapKey) o;

        if ((wrapper.getSymbol() == null && key.getWrapper().getSymbol() != null) || (wrapper.getSymbol() != null && key.getWrapper().getSymbol() == null)){
            return false;
        }
        if ( (classOfOperand == null && key.getClassOfOperand() != null) || (classOfOperand != null && key.getClassOfOperand() == null) ){
            return false;
        }

        if ( (wrapper.getSymbol() == key.getWrapper().getSymbol() || wrapper.getSymbol().equals(key.getWrapper().getSymbol()) )
                && ( classOfOperand == key.getClassOfOperand() || classOfOperand.toString().equals(key.getClassOfOperand().toString()) )){
            return true;
        }
        else {
            return false;
        }
    }

}
