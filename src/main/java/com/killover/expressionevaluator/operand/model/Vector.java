package com.killover.expressionevaluator.operand.model;

import com.killover.expressionevaluator.operand.Operand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by killover on 2016/11/16.
 */
public class Vector extends ArrayList<Operand>{

    public Vector(){
        super();
    }

    public Vector(List<Operand> operandList){
        super();
        for (Operand operand: operandList){
            add(operand);
        }
    }

}
