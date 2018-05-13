package com.telecom.softmax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class softmaxRex {
	private int numClass;    // num of categories
	private int dim;        // dimension of features, bias not included
	private Double theta[][]; // weight
	
	public Double[][] getTheta() {
		return theta;
	}

	public softmaxRex(int _numClass, int _dim)
	{
		this.numClass = _numClass;
		this.dim = _dim;
		this.theta = new Double[_numClass][_dim+1];
		for (int i = 0; i < _dim + 1; i++) 
			for (int j = 0; j < _numClass; j++) 
				theta[j][i] = 0.01;
	}
	
	public softmaxRex() {};
	
	/*public int getNum(String filename)
	{
		int num = 0;
		File file = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String thisline = null;
			
			while((thisline = reader.readLine()) != null)
			{
				num++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}*/
	
	public int loadData(List<List<Double>> traindata,List<Double> trainlabel, int total, double data[][], double label[][], boolean randPer)
	{;
		int[] rIdx = new int[total];
		for(int i = 0; i < total; i++) 
		{
			rIdx[i] = i;
		}
		
		if (randPer == true) {
			int rNum = 0;
			int tmp = 0;
			 Random rdm = new Random(System.currentTimeMillis());
			for(int i = 0; i < total; i++) {
				rNum =Math.abs(rdm.nextInt())%total;
				tmp = rIdx[i];
				rIdx[i] = rIdx[rNum];
				rIdx[rNum] = tmp;
			}
		}
		
		double tempLabel = -1;
		for(int i = 0;i<total;i++){
			data[rIdx[i]][0] = 1;
			
			int j;
			for(j = 1 ; j < dim+1 ; j++)
			{

				double b = traindata.get(i).get(j-1);
				data[rIdx[i]][j] = b;
			}

			double b = trainlabel.get(i);
			tempLabel = b;
			LabelTrans(tempLabel, label[rIdx[i]]);
		}
		return 1;
	}
	
	
	public void LabelTrans(double temp, double label[])
	{
		for (int i = 0; i < numClass; i++) {
			label[i] = 0;
		}
		int t = (int)(temp+0.1);
		label[t] = 1;
	}
	
	public void Train(List<List<Double>> traindata,List<Double> trainlabel,int numTrain, int epoch, double alpha, Boolean saveModel)
	{ 
		double[][] dataTrain=new double[numTrain][dim+1];
		double[][] labelTrain = new double[numTrain][numClass];
		
		if (loadData(traindata,trainlabel, numTrain, dataTrain, labelTrain, true) == 0) {
			System.err.println("INFO    [Error]: Training failed"); 
			return;
		}
		
		double[] prob = new double[numClass];
		for (int i = 0; i < epoch; i++) {
			for (int j = 0; j < numTrain; j++) {
				for (int k = 0; k < numClass; k++) {
					prob[k] = 0;
					for (int m1 = 0; m1 < dim + 1; m1++) {
						prob[k] += theta[k][m1] * dataTrain[j][m1];
					}
				}
				CalcProb(prob);
				for (int m2 = 0; m2 < numClass; m2++) {
					for (int n = 0; n < dim + 1; n++) {
						theta[m2][n] += (alpha * (labelTrain[j][m2] - prob[m2]) * dataTrain[j][n]);
					}
				}
			}	
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		String outname = time + ".txt";
		
	}
	
	public void CalcProb(double x[])
	{
		double max = 0.0;
		double sum = 0.0;

		for (int i = 0; i < numClass; i++) if (max < x[i]) max = x[i];
		for (int i = 0; i < numClass; i++) {
			x[i] = Math.exp(x[i] - max);		// avoid overflow
			sum += x[i];
		}
		for (int i = 0; i < numClass; i++) 
			x[i] /= sum;
	}
	public void CalcProbPre(double x[],int num)
	{
		double max = 0.0;
		double sum = 0.0;

		for (int i = 0; i < num; i++) if (max < x[i]) max = x[i];
		for (int i = 0; i < num; i++) {
			x[i] = Math.exp(x[i] - max);		// avoid overflow
			sum += x[i];
		}
		for (int i = 0; i < num; i++) 
			x[i] /= sum;
	}
	
	public double Analysis(List<List<Double>> testdata,List<Double> testlabel,int numTest, Boolean modelType, String filenameModel)
	{ 
		double[][] dataTest=new double[numTest][dim+1];
		double[][] labelTest = new double[numTest][numClass];
		if (loadData(testdata, testlabel,numTest, dataTest, labelTest, false) == 0) {
			System.out.println("INFO    Error: Predict failed");
			return 0;
		}
		
		int count = 0;
		double[] predict = new double[numClass];
		double max = 0;
		
		for (int i = 0; i < numTest; i++) {
			for (int j = 0; j < numClass; j++) {
				predict[j] = 0;
				for (int k = 0; k < dim + 1; k++) {
					predict[j] += theta[j][k] * dataTest[i][k];
				}
			}
			CalcProb(predict);
			for (int j = 0; j < numClass; j++) {
				if (predict[j] >= max) max = predict[j];		
			}

			for (int j1 = 0; j1 < numClass; j1++) {
				if (Math.abs(predict[j1] - max) < 0.001 && (int)labelTest[i][j1] == 1) count++;
			}
		}
		
		System.out.println("INFO    Number of testing samples = "+numTest);
		System.out.println("INFO    Number of samples correctly classified = "+count);
		System.out.println("INFO    Classification accuracy = "+(double)count / numTest);
		return (double)count / numTest;
	}
	
	public int[] Predict(List<List<Double>> testdata,int numTest,int dim,int numClass, double[][] theta)
	{
		double[][] dataTest=new double[numTest][dim+1];
		for(int i = 0;i<numTest;i++){
			dataTest[i][0] = 1;
			
			int j;
			for(j = 1 ; j < dim+1 ; j++)
			{

				double b = testdata.get(i).get(j-1);
				dataTest[i][j] = b;
			}
		}
		int count = 0;
		double[] predict = new double[numClass];
		double max = 0;
		int[] prevalue = new int[numTest];
		for (int i = 0; i < numTest; i++) {
			for (int j = 0; j < numClass; j++) {
				predict[j] = 0;
				for (int k = 0; k < dim + 1; k++) {
					predict[j] += theta[j][k] * dataTest[i][k];
				}
			}
			CalcProbPre(predict,numClass);

			for (int j = 0; j < numClass; j++) {
				if (predict[j] >= max)
				{
				    max = predict[j];	
				}
				
			}
			
			for (int j1 = 0; j1 < numClass; j1++) {
				if (Math.abs(predict[j1] - max) < 0.001) prevalue[i] = j1;
			}
		}

		return prevalue;
	}
}
