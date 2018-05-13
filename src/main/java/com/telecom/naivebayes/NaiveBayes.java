package com.telecom.naivebayes;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.assertj.core.api.Assertions.offset;
import static org.mockito.Matchers.doubleThat;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.LifecycleListener;
import org.mockito.asm.tree.IincInsnNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Michael Kong
 */
public class NaiveBayes extends Classifier {

	boolean isClassfication[];
   ArrayList <Double>lblClass=new ArrayList<Double>();  //存储目标值的种类
   ArrayList<Integer>lblCount=new ArrayList<Integer>();//存储目标值的个数
   ArrayList<Float>lblProba=new ArrayList<Float>();//存储对应的label的概率
   CountProbility countlblPro;
   /*@ClassListBasedLabel是将训练数组按照 label的顺序来分类存储*/
   ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel=new  ArrayList<ArrayList<ArrayList<Double>>> ();
    public NaiveBayes() {
    }
    
    @Override
    public String trainalldata(boolean[] isCategory, double[][] features, double[] labels){
    	isClassfication=isCategory;
    	countlblPro=new CountProbility(isCategory,features,labels);
    	countlblPro.getlblClass(lblClass, lblCount, lblProba);  	
    	ArrayList<ArrayList<Double>> trainingList=countlblPro.UnionFeaLbl(features, labels); //union the features[][] and labels[]
    	ClassListBasedLabel=countlblPro.getClassListBasedLabel(lblClass, trainingList);
    	int max_index; //用于记录使概率取得最大的那个索引
    	int index=0; //这个索引是 标识不同的labels 所对应的概率
    	//ArrayList<Double> pro_=new ArrayList<Double>(); //这个概率数组是存储features[] 在不同labels下对应的概率
    	BayesModel[][] matrix_prob = new BayesModel[ClassListBasedLabel.size()][isClassfication.length];
		for(ArrayList<ArrayList<Double>> elements: ClassListBasedLabel)  //依次取不同的label值对应的元祖集合
		{
		 	ArrayList<Double> pro=new ArrayList<Double>();//存同一个label对应的所有概率，之后其中的元素自乘
			double probility=1.0;  //计算概率的乘积
			int k = 0;
			for(int i=0;i<isClassfication.length-1;i++)
			{
				k++;
				matrix_prob[index][i] = new BayesModel();
				
				if(isClassfication[i])  //用于对属性的离散还是连续做判断
				{
					List<Double> alllabeltype = new ArrayList<>();
					
					for(int ii = 0;ii<features.length;ii++) 
					{
						
						if(!alllabeltype.contains(features[ii][i]))
						{
							alllabeltype.add(features[ii][i]);
						}
					}
					double[] translable = new double[alllabeltype.size()];
					double[] translablepro = new double[alllabeltype.size()];
					//matrix_prob[index][i].setFeaturelabeltype();
					matrix_prob[index][i].setIsclassify(true);
					int count=0;
					for(int ii = 0 ; ii < alllabeltype.size(); ii++)
					{
					    translable[ii] = alllabeltype.get(ii);
						for(ArrayList<Double> element:elements) //依次取labels中的所有元祖
						{
							if(element.get(i).equals(alllabeltype.get(ii))) //如果这个元祖的第index数据和b相等，那么就count就加1
								count++;
						}
						if(count==0)
						{
							//matrix_prob[index][i].setProb(1/(double)(elements.size()+1));
							translablepro[ii] = 1/(double)(elements.size()+1);
						}
						else
						{
							//matrix_prob[index][i].setProb(count/(double)elements.size()); //统计完所有之后  计算概率值 并加入
							translablepro[ii] = count/(double)elements.size();
						}
					
					}
					//matrix_prob[index][i].setFeaturelabelprob(featurelabelprob);
					matrix_prob[index][i].setFeaturelabeltype(translable);
					matrix_prob[index][i].setFeaturelabelprob(translablepro);
				}
				else
				{
					//matrix_prob[index][i].setIsclassify(false);
		  			double Sdev;
		    		double Mean;

    				Mean=countlblPro.getMean(elements, i);
    				Sdev=countlblPro.getSdev(elements, i);
	    			matrix_prob[index][i].setMean(Mean);
	    			matrix_prob[index][i].setDev(Sdev);
    				
	        	}
				
				
			}
			matrix_prob[index][k] = new BayesModel();
			matrix_prob[index][k].setIsclassify(true);
			matrix_prob[index][k].setProb(lblProba.get(index));
			matrix_prob[index][k].setLabel(lblClass.get(index));
			index++;
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
	   	gsonBuilder.serializeSpecialFloatingPointValues();
	   	Gson gson = gsonBuilder.create();		    	
	   	String json = gson.toJson(matrix_prob);
	   	return json;
    }
    @Override
    /**
     * @train主要完成求一些概率
     * 1.labels中的不同取值的概率f(Yi);  对应28,29行两段代码
     * 2.将训练数组按目标值分类存储   第37行代码

     * */
    public void train(boolean[] isCategory, double[][] features, double[] labels){
    	isClassfication=isCategory;
    	countlblPro=new CountProbility(isCategory,features,labels);
    	countlblPro.getlblClass(lblClass, lblCount, lblProba);  	
    	ArrayList<ArrayList<Double>> trainingList=countlblPro.UnionFeaLbl(features, labels); //union the features[][] and labels[]
    	ClassListBasedLabel=countlblPro.getClassListBasedLabel(lblClass, trainingList);
    }
    @Override
    /**3.在Y的条件下，计算Xi的概率 f(Xi/Y)；
     * 4.返回使得Yi*Xi*...概率最大的那个label的取值
     * */
    public double predict(double[] features) {
    	
    	int max_index; //用于记录使概率取得最大的那个索引
    	int index=0; //这个索引是 标识不同的labels 所对应的概率
    	ArrayList<Double> pro_=new ArrayList<Double>(); //这个概率数组是存储features[] 在不同labels下对应的概率
    	double[][] matrix_prob = new double[ClassListBasedLabel.size()][features.length+1];
		for(ArrayList<ArrayList<Double>> elements: ClassListBasedLabel)  //依次取不同的label值对应的元祖集合
		{
		 	ArrayList<Double> pro=new ArrayList<Double>();//存同一个label对应的所有概率，之后其中的元素自乘
		 	ArrayList<Double> mea1=new ArrayList<Double>();
		 	ArrayList<Double> dev1=new ArrayList<Double>();
			double probility=1.0;  //计算概率的乘积
			int k = 0;
			for(int i=0;i<features.length;i++)
			{
				k++;
				if(isClassfication[i])  //用于对属性的离散还是连续做判断
				{
					
					int count=0;
					for(ArrayList<Double> element:elements) //依次取labels中的所有元祖
					{
						if(element.get(i).equals(features[i])) //如果这个元祖的第index数据和b相等，那么就count就加1
							count++;
					}
					if(count==0)
					{
						pro.add(1/(double)(elements.size()+1));
					}
					else
						pro.add(count/(double)elements.size()); //统计完所有之后  计算概率值 并加入
				}
				else
				{

		  			double Sdev;
		    		double Mean;
		    		
	    			double probi=1.0;
    				Mean=countlblPro.getMean(elements, i);
    				Sdev=countlblPro.getSdev(elements, i);
    				mea1.add(Mean);
    				dev1.add(Sdev);
    				//System.out.println("Sdev = " +Sdev+ "Mean = " + Mean);
    				if(Sdev!=0)
    				{
    					probi*=((1/(Math.sqrt(2*Math.PI)*Sdev))*(Math.exp(-(features[i]-Mean)*(features[i]-Mean)/(2*Sdev*Sdev))));
    	    			pro.add(probi);
    				}
    				else
    					pro.add(1.5);
    				
	        	}
				matrix_prob[index][i] = pro.get(i);
				
			}
			//System.out.println("mea1"+mea1);
			//System.out.println("dev1"+dev1);
			matrix_prob[index][k]=lblProba.get(index);
			//System.out.println("11:     "+pro);
			for(double pi:pro)
				probility*=pi; //将所有概率相乘
			probility*=lblProba.get(index);//最后再乘以一个 Yi
			//System.out.println(lblProba.get(index));
			pro_.add(probility);// 放入pro_ 至此 一个循环结束，	
			index++;
		}
	 	//System.out.println("12:   " + pro_);
		double max_pro=pro_.get(0);
		max_index=0;
		
		
		for(int i=1;i<pro_.size();i++)
		{
			if(pro_.get(i)>=max_pro)
			{
				max_pro=pro_.get(i);
				max_index=i;
			}	
		}  
		return  lblClass.get(max_index);
    }
    
    @Override 
    public double predictvalue(double[] features,String bayesmodel) {
    	
    	int max_index; //用于记录使概率取得最大的那个索引
    	int index=0; //这个索引是 标识不同的labels 所对应的概率
    	
    	ArrayList<Double> pro_=new ArrayList<Double>(); //这个概率数组是存储features[] 在不同labels下对应的概率
    	
		GsonBuilder gsonBuilder = new GsonBuilder();
	   	gsonBuilder.serializeSpecialFloatingPointValues();
	   	Gson gson = gsonBuilder.create();
	   	BayesModel[][] bayesModel = gson.fromJson(bayesmodel, BayesModel[][].class);
	   	for(int i= 0 ; i < bayesModel.length ; i++)
	   	{
	   		double probility=1.0;  //计算概率的乘积
		 	ArrayList<Double> mea2=new ArrayList<Double>();
		 	ArrayList<Double> dev2=new ArrayList<Double>();
		 	ArrayList<Double> pro=new ArrayList<Double>();//存同一个label对应的所有概率，之后其中的元素自乘
			for(int ii = 0;ii<features.length;ii++)  //依次取不同的label值对应的元祖集合
			{
			 	
				
				if(bayesModel[i][ii].isIsclassify())
				{
					double[] temp = bayesModel[i][ii].getFeaturelabeltype();
					double[] temppro = bayesModel[i][ii].getFeaturelabeltype();
					for(int iii = 0 ; iii<bayesModel[i][ii].getFeaturelabeltype().length;iii++)
					{
						if(temp[iii] == features[i])
						{
							//probility *= temppro[iii];
							pro.add(temppro[iii]);
						}
					}
				}
				else 
				{
					double Mean = bayesModel[i][ii].getMean();
					double Sdev = bayesModel[i][ii].getDev();
					mea2.add(Mean);
					dev2.add(Sdev);
	    			double probi=1.0;
    				if(Sdev!=0)
    				{
    					probi*=((1/(Math.sqrt(2*Math.PI)*Sdev))*(Math.exp(-(features[ii]-Mean)*(features[ii]-Mean)/(2*Sdev*Sdev))));
    	    			pro.add(probi);
    					//probility *= probi;
    				}
    				else
    				{
    					pro.add(1.5);
    					//probility *= 1.5;
    				}
				}
			}
			//System.out.println("mea2"+mea2);
			//System.out.println("dev2"+dev2);
			//System.out.println(bayesModel[i][features.length].getProb());
			//System.out.println("21:   " + pro);
			for(double pi:pro)
				probility*=pi; //将所有概率相乘
			probility*=bayesModel[i][features.length].getProb();//最后再乘以一个 Yi
			//probility *= bayesModel[i][features.length].getProb();
			pro_.add(probility);
	   	}
	   	//System.out.println("22:   " + pro_);
		double max_pro=pro_.get(0);
		max_index=0;
		
		
		for(int i=1;i<pro_.size();i++)
		{
			if(pro_.get(i)>=max_pro)
			{
				max_pro=pro_.get(i);
				max_index=i;
			}	
		}  
		return  bayesModel[max_index][bayesModel[0].length-1].getLabel();
    }
    
    
    public class CountProbility
    {
    	boolean []isCatory;
    	double[][]features;
    	private double[]labels;
    	public CountProbility(boolean[] isCategory, double[][] features, double[] labels)
    	{
    		this.isCatory=isCategory;
    		this.features=features;
    		this.labels=labels;
    	}
    	//获取label中取值情况
    	public void getlblClass(  ArrayList <Double>lblClass,ArrayList<Integer>lblCount,ArrayList<Float>lblProba)
    	{
    		int j=0;
            for(double i:labels)
            {
            	//如果当前的label不存在于lblClass则加入
            	if(!lblClass.contains(i))
            	{
            		lblClass.add(j,i);
            		lblCount.add(j++,1);
            	}
            	else //如果label中已经存在，就将其计数加1
            	{
            		int index=lblClass.indexOf(i); 
            		int count=lblCount.get(index);
            		lblCount.set(index,++count);
            	}
            		
            }
            for(int i=0;i<lblClass.size();i++)
            {
            	System.out.println("值为"+lblClass.get(i)+"的个数有"+lblCount.get(i)+"概率是"+lblCount.get(i)/(float)labels.length);
            	lblProba.add(i,lblCount.get(i)/(float)labels.length);
            }
            
    	}
    	//将label[]和features[][]合并
    	public ArrayList<ArrayList<Double>>  UnionFeaLbl(double[][] features, double[] labels)
    	{
    		ArrayList<ArrayList<Double>>traingList=new  ArrayList<ArrayList<Double>>();
    		for(int i=0;i<features.length;i++)
    		{
    			ArrayList<Double>elements=new ArrayList<Double>();
    			for(int j=0;j<features[i].length;j++)
    			{
    				elements.add(j,features[i][j]);
    			}
    			elements.add(features[i].length,labels[i]);
    			traingList.add(i,elements);
    			
    		}
    		return traingList;
    	}
    	/*将测试数组按label的值分类存储*/
    	public ArrayList<ArrayList<ArrayList<Double>>> getClassListBasedLabel (ArrayList <Double>lblClass,ArrayList<ArrayList<Double>>trainingList)
    	{
    		ArrayList<ArrayList<ArrayList<Double>>> ClassListBasedLabel=new ArrayList<ArrayList<ArrayList<Double>>> () ;
        		for(double num:lblClass)
        		{
    				ArrayList<ArrayList<Double>> elements=new ArrayList<ArrayList<Double>>();
	    			for(ArrayList<Double>element:trainingList)
	    			{
	    				if(element.get(element.size()-1).equals(num))
	    					elements.add(element);
	    			}
    			ClassListBasedLabel.add(elements);
        		}
    			return ClassListBasedLabel;
    	}
    	public double getMean(ArrayList<ArrayList<Double>> elements,int index)
    	{
    		double sum=0.0;
    		double Mean;
    		
    		for(ArrayList<Double> element:elements)
    		{
    			sum+=element.get(index);
    			
    		}
    		Mean=sum/(double)elements.size();
    		return  Mean;
    	}
    	public double getSdev(ArrayList<ArrayList<Double>> elements,int index)
    	{
    		double dev=0.0;
    		double Mean;
    		Mean=getMean(elements,index);
    		for(ArrayList<Double> element:elements)
    		{
    			dev+=Math.pow((element.get(index)-Mean),2);
    		}
    		dev=Math.sqrt(dev/elements.size());
    		return  dev;
    	}
    	
    	
    }
}

