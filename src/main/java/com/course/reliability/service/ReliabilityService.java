package com.course.reliability.service;

import com.course.reliability.model.CustomerInformation;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReliabilityService {

    private final DecisionTreeModel decisionTreeModel;

    public ReliabilityService(DecisionTreeModel decisionTreeModel) {
        this.decisionTreeModel = decisionTreeModel;
    }

    public Double predictReliability(final CustomerInformation customerInformation){
        return decisionTreeModel.predict(Vectors.dense(customerInformation.getAge(),customerInformation.getSalary(),
                customerInformation.getRealEstate(), customerInformation.getDebt(),customerInformation.getElec(),
                customerInformation.getGas()));
    }
}
