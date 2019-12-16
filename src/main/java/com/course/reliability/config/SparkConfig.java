package com.course.reliability.config;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public SparkSession sparkSession(){
        return SparkSession.builder().master("local").appName("DecisionTreeReliability")
                .config("spark.sql.warehouse.dir", "working").getOrCreate();
    }

    @Bean
    public JavaSparkContext sparkContext(final SparkSession sparkSession){
        return new JavaSparkContext(sparkSession.sparkContext());
    }

    @Bean
    public DecisionTreeModel decisionTreeModel(final JavaSparkContext sparkContext){
        return DecisionTreeModel
                .load(sparkContext.sc(), "model/tmp/myDecisionTreeClassificationModel");
    }
}
