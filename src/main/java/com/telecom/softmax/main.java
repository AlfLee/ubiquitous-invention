package com.telecom.softmax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class main {
	 public static void main(String[] args)
	 {
		 	//String fileTrain = "E:\\C++\\softmaxtest\\irisTrain.txt";
		    String fileTrain ="E:\\eclipse-workspace\\project1\\file\\train.txt";
		 	//String fileTest = "E:\\C++\\softmaxtest\\irisTest.txt";
		    String fileTest = "E:\\eclipse-workspace\\project1\\file\\test.txt";
			int dim = 7;				//dimension of features (bias not included)
			int numClass = 5;			//number of categories
			int epoch =8000;			//Iteration
			double alpha = 0.25;		// Learning Rate
			softmaxReg Classifier = new softmaxReg(numClass, dim);
		 	Classifier.Train(fileTrain, epoch, alpha, false);
		 	Classifier.Predict(fileTest,false,"0");

	 }



}


