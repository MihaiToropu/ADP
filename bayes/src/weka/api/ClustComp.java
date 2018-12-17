package weka.api;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.BallTree;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import weka.core.converters.ConverterUtils.DataSource;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class ClustComp {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub


        BufferedReader reader = null;
        BufferedReader readerTest = null;
        BufferedReader reader2 = null;
        BufferedReader readerTest2 = null;

        reader = new BufferedReader(new FileReader("iris.arff"));
        readerTest = new BufferedReader(new FileReader("iris.arff"));
        reader2 = new BufferedReader(new FileReader("diabetes.arff"));
        readerTest2 = new BufferedReader(new FileReader("diabetes.arff"));
        Instances train = null;
        Instances test = null;
        Instances train2 = null;
        Instances test2 = null;
        try {
            train = new Instances(reader);
            test = new Instances(readerTest);
            train2 = new Instances(reader2);
            test2 = new Instances(readerTest2);
            train2.setClassIndex(train2.numAttributes() - 1);
            test2.setClassIndex(test2.numAttributes() - 1);
            train2.setClassIndex(train2.numAttributes() - 1);
            test2.setClassIndex(test2.numAttributes() - 1);

            DataSource source = new DataSource("iris.arff");
            Instances dataset = source.getDataSet();
            dataset.setClassIndex(dataset.numAttributes()-1);

            DataSource source2 = new DataSource("diabetes.arff");
            Instances dataset2 = source2.getDataSet();
            dataset2.setClassIndex(dataset2.numAttributes()-1);

            System.out.println("======================================================");

            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(dataset);
            Evaluation evalNb = new Evaluation(dataset);
            evalNb.evaluateModel(nb, dataset);
            System.out.println(evalNb.toSummaryString());

            System.out.println("======================================================");

            NaiveBayes nb2 = new NaiveBayes();
            nb2.buildClassifier(dataset2);
            Evaluation evalNb2 = new Evaluation(dataset2);
            evalNb2.evaluateModel(nb2, dataset2);
            System.out.println(evalNb2.toSummaryString());

            System.out.println("======================================================");

            BayesNet bn = new BayesNet();
            bn.buildClassifier(dataset);
            Evaluation evalBn = new Evaluation(dataset);
            evalNb.evaluateModel(bn, dataset);
            System.out.println(evalNb.toSummaryString());

            System.out.println("======================================================");

            BayesNet bn2 = new BayesNet();
            bn2.buildClassifier(dataset2);
            Evaluation evalBn2 = new Evaluation(dataset2);
            evalNb2.evaluateModel(bn2, dataset2);
            System.out.println(evalNb2.toSummaryString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}


