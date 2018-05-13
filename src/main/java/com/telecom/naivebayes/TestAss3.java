package com.telecom.naivebayes;

import static org.mockito.Mockito.ignoreStubs;

import java.util.ArrayList;


/**
 *
 * @author daq
 */
public class TestAss3 {

    public static void main(String[] args) {

       //String[] dataPaths = new String[]{"breast-cancer.data", "segment.data"};
    	String[] dataPaths = new String[]{"E:\\大学\\毕业设计\\材料\\NaiveBayes\\NaiveBayes\\test.txt"};
        for (String path : dataPaths) {
            DataSet dataset = new DataSet(path);
//
//            // conduct 10-cv 
           Evaluation eva = new Evaluation(dataset, "NaiveBayes");
          eva.crossValidation();
//
//      // print mean and standard deviation of accuracy
      System.out.println("Dataset:" + path + ", mean and standard deviation of accuracy:" + eva.getAccMean() + "," + eva.getAccStd());
        }
    }
}
