package com.telecom.softmax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telecom.mapper.FaultMapper;

public class softmaxReg {
	private int numClass;    // num of categories
	private int dim;        // dimension of features, bias not included
	private Double theta[][]; // weight
	private FaultMapper faultmapper;
	
	public softmaxReg(int _numClass, int _dim)
	{
		this.numClass = _numClass;
		this.dim = _dim;
		this.theta = new Double[_numClass][_dim+1];
		for (int i = 0; i < _dim + 1; i++) 
			for (int j = 0; j < _numClass; j++) 
				theta[j][i] = 0.01;
	}
	
	public int getNum(String filename)
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
	}
	
	public int loadData(String filename, int total, double data[][], double label[][], boolean randPer)
	{
		File file = new File(filename);
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
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String thisline = null;
			int i = 0;
			while((thisline = reader.readLine()) != null){
				//String[] seperate = thisline.split("   ");
				String[] seperate = thisline.split(",");
				data[rIdx[i]][0] = 1;
				
				int j;
				for(j = 1 ; j < dim+1 ; j++)
				{
				   // BigDecimal a = new BigDecimal(seperate[j]);
					//double b= a.doubleValue();
					double b = Double.parseDouble(seperate[j-1]);
					data[rIdx[i]][j] = b;
				}
			   // BigDecimal a = new BigDecimal(seperate[j]);
				//double b= a.doubleValue(); 
				double b = Double.parseDouble(seperate[j-1]);
				tempLabel = b;
				LabelTrans(tempLabel, label[rIdx[i]]);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void Train(String filenameTrain, int epoch, double alpha, Boolean saveModel)
	{
		int numTrain = getNum(filenameTrain); 
		double[][] dataTrain=new double[numTrain][dim+1];
		double[][] labelTrain = new double[numTrain][numClass];
		
		if (loadData(filenameTrain, numTrain, dataTrain, labelTrain, true) == 0) {
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
	/*	File file = new File("E:\\C++\\新建文件夹\\"+outname);  //存放数组数据的文件  
	    FileWriter out;
		try {
			out = new FileWriter(file);

		    for (int i = 0; i < numClass; i++) {
				for (int j = 0; j < dim + 1; j++) {
					out.write(theta[i][j].toString());
					if(j != dim ) 
						out.write(" ");
				}
				out.write("\r\n");
			}
	    
		    out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //文件写入流*/
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
	
	public void Predict(String filenameTest, Boolean modelType, String filenameModel)
	{
		int numTest = getNum(filenameTest); 
		double[][] dataTest=new double[numTest][dim+1];
		double[][] labelTest = new double[numTest][numClass];
		
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeSpecialFloatingPointValues();
   	    Gson gson = gsonBuilder.create();
   	    String softmodel = "[[29838.522485659778,385.8714254023972,1237.7257678286253,39983.56615766451,77512.31677035925,7515.271725241682,2718.441190619295,-73439.19536575851],[6809.510000002898,193.36070314349155,3960.215949323246,125369.7768523521,49823.58589297272,-14176.328384682633,1160.0488734744151,107125.60925883226],[14498.692064547822,261.1072393501476,3195.1844817077517,101117.94245760774,38081.38738407014,5110.821776260707,1592.8143879748973,-42139.06946261044],[-34863.97450413498,-446.8161833266861,-3893.092177612727,-123617.58056227311,-131882.43936300123,2312.453452247347,-3849.277380215838,26281.03864488816],[-16282.70004607552,-393.47318456935636,-4499.984021247467,-142853.65490534337,-33534.80068441238,-762.1685690667259,-1621.9770718533484,-17828.333075345847]]";
   	    theta = gson.fromJson(softmodel,Double[][].class);
		if (loadData(filenameTest, numTest, dataTest, labelTest, false) == 0) {
			System.out.println("INFO    Error: Predict failed");
			return;
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
				if (Math.abs(predict[j1] - max) < 0.001) { count++;
				System.out.println(j1);}
			}
		}
		
		System.out.println("INFO    Number of testing samples = "+numTest);
		System.out.println("INFO    Number of samples correctly classified = "+count);
		System.out.println("INFO    Classification accuracy = "+(double)count / numTest);
	}
}
