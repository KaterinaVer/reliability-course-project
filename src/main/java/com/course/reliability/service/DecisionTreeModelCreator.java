package com.course.reliability.service;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

public class DecisionTreeModelCreator {
    private final static Logger LOG = LoggerFactory.getLogger(DecisionTreeModelCreator.class);
    private final static String FILE_DATA = "customer_information.csv";

    public static void main(String[] args) {
        DecisionTreeModelCreator testDecisionTree = new DecisionTreeModelCreator();
        testDecisionTree.trainDecisionTreeModel();
    }
    public void trainDecisionTreeModel(){
        SparkSession spark = SparkSession.builder().master("local").appName("TestDecisionTreeReliability")
                .config("spark.sql.warehouse.dir", "working").getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Loads data.
        Dataset<Row> datasetCredit = spark.read().schema(buildSchema()).csv(FILE_DATA);
        datasetCredit = datasetCredit.select(
                col("id"),
                col("age"),
                col("salary"),
                col("real_estate"),
                col("debt"),
                col("elec"),
                col("gas"),
                col("reliability")
        );

        // Show some data
        datasetCredit.show();

        // Verify Schema
        datasetCredit.printSchema();

        // Decision Tree algo
        // Split data 70% and 30%
        Dataset<Row>[] datas = datasetCredit.randomSplit(new double[] {0.7, 0.3});

        // separate dataset
        JavaRDD<Row> train = datas[0].toJavaRDD();
        JavaRDD<Row> test = datas[1].toJavaRDD();


        // Training data
        @SuppressWarnings("serial")
        JavaRDD<LabeledPoint> trainLabeledPoints = train.map(
                (Function<Row, LabeledPoint>) row -> new LabeledPoint(row.getDouble(7), // reliability
                        Vectors.dense(
                                row.getInt(1),
                                row.getDouble(2),
                                row.getInt(3),
                                row.getDouble(4),
                                row.getDouble(5),
                                row.getDouble(6)
                        )));

        trainLabeledPoints.cache();

        // Validation data
        @SuppressWarnings("serial")
        JavaRDD<LabeledPoint> testLabeledPoints = test.map(
                (Function<Row, LabeledPoint>) row -> new LabeledPoint(row.getDouble(7), // reliability
                        Vectors.dense(
                                row.getInt(1),
                                row.getDouble(2),
                                row.getInt(3),
                                row.getDouble(4),
                                row.getDouble(5),
                                row.getDouble(6)
                        )));
        testLabeledPoints.cache();

        Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
        String impurity = "gini";
        int maxDepth = 6;
        int maxBins = 20;
        int numClass = 2;

        // Find a model ...
        final DecisionTreeModel model = DecisionTree.trainClassifier(trainLabeledPoints, numClass, categoricalFeaturesInfo, impurity, maxDepth, maxBins);

        LOG.info("--------------- MODEL ---------------------" );
        LOG.info(model.toDebugString());
        LOG.info("------------------------------------" );

        // Evaluate model on test instances and compute test error
        JavaPairRDD<Double, Double> predictionAndLabel =
                testLabeledPoints.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
        double testErr =
                predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testLabeledPoints.count();

        LOG.info("Test Error: " + testErr);
        LOG.info("Learned classification tree model:\n" + model.toDebugString());

        model.save(jsc.sc(), "target/tmp/myDecisionTreeClassificationModel");
    }

    protected StructType buildSchema() {
        return new StructType(
                new StructField[] {
                        DataTypes.createStructField("id", DataTypes.IntegerType, true),
                        DataTypes.createStructField("age", DataTypes.IntegerType, true),
                        DataTypes.createStructField("salary", DataTypes.DoubleType, true),
                        DataTypes.createStructField("real_estate", DataTypes.IntegerType, true),
                        DataTypes.createStructField("debt", DataTypes.DoubleType, true),
                        DataTypes.createStructField("elec", DataTypes.DoubleType, true),
                        DataTypes.createStructField("gas", DataTypes.DoubleType, true),
                        DataTypes.createStructField("reliability", DataTypes.DoubleType, true) });
    }
}
