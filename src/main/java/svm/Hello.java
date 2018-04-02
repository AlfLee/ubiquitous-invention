package svm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Hello {

    public static void main(String[] args)
    {
    	 // ����ѵ������a{10.0, 10.0} �� ��b{-10.0, -10.0}����ӦlableΪ{1.0, -1.0}
    	  List<Double> label = new ArrayList<Double>();
    	  List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
    	 // getData(nodeSet, label, "E:\\project1\\file\\train.txt");
    	  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\train.txt");
    	 // getData(nodeSet, label,  "E:\\eclipse-workspace\\EnterpriseRating\\file\\train1.txt");
    	  int dataRange=nodeSet.get(0).length;
    	  svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // ѵ������������
    	  for (int i = 0; i < datas.length; i++) {
    	   for (int j = 0; j < dataRange; j++) {
    	    datas[i][j] = nodeSet.get(i)[j];
    	   }
    	  }

    	  double[] lables = new double[label.size()]; // a,b ��Ӧ��lable
    	  for (int i = 0; i < lables.length; i++) {
    	   lables[i] = label.get(i);
    	  }
    	 
    	// ����svm_problem����
    	  svm_problem problem = new svm_problem();
    	  problem.l = nodeSet.size(); // ��������
    	  problem.x = datas; // ѵ����������
    	  problem.y = lables; // ��Ӧ��lable����
    	 
    	// ����svm_parameter����
    	  svm_parameter param = new svm_parameter();
    	  param.svm_type = svm_parameter.EPSILON_SVR;
    	  param.kernel_type = svm_parameter.LINEAR;
    	  param.cache_size = 100;
    	  param.eps = 0.00001;
    	  param.C = 1.9;
    	// ѵ��SVM����ģ��
    	  System.out.println(svm.svm_check_parameter(problem, param));
    	// �������û�����⣬��svm.svm_check_parameter()��������null,���򷵻�error������
    	  svm_model model = svm.svm_train(problem, param);
    	  // svm.svm_train()ѵ����SVM����ģ��
    	 
    	  // ��ȡ��������
    	  List<Double> testlabel = new ArrayList<Double>();
    	  List<svm_node[]> testnodeSet = new ArrayList<svm_node[]>();
    	//  getData(testnodeSet, testlabel, "E:\\project1\\file\\test.txt");
    	  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\test.txt");
    	// getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\EnterpriseRating\\file\\test1.txt");
    	  
    	  svm_node[][] testdatas = new svm_node[testnodeSet.size()][dataRange]; // ѵ������������
    	  for (int i = 0; i < testdatas.length; i++) {
    	   for (int j = 0; j < dataRange; j++) {
    	    testdatas[i][j] = testnodeSet.get(i)[j];
    	   }
    	  }
    	  double[] testlables = new double[testlabel.size()]; // a,b ��Ӧ��lable
    	  for (int i = 0; i < testlables.length; i++) {
    	   testlables[i] = testlabel.get(i);
    	  }
    	 
    	//������ǵ�ǰ��ȡ���ݵ�����
    	  double err = 0.0 ;
    	  double count = 0 ;
    	  for (int i = 0; i < testdatas.length; i++) {
    	   int truevalue = (int) testlables[i];
    	 
    	   System.out.print(truevalue + " ");
    	   
    	   double predictValue = svm.svm_predict(model, testdatas[i]);
    	  
    	   System.out.println(Math.round(predictValue));
    	   if(Math.round(predictValue) != truevalue){
    		   count++; 		   
    	   }
    	 //  err += Math.abs(predictValue - truevalue);
    	   err=count/testdatas.length;
    	  }
    	  //System.out.println("err=" + err / datas.length);
    	  System.out.println("err="+err);
    	 }
    	 
    	 public static void getData(List<svm_node[]> nodeSet, List<Double> label,
    	   String filename) {
    	  try {
    	 
    	   FileReader fr = new FileReader(new File(filename));
    	   BufferedReader br = new BufferedReader(fr);
    	   String line = null;
    	   while ((line = br.readLine()) != null) {
    	    String[] datas = line.split(",");
    	    svm_node[] vector = new svm_node[datas.length - 1];
    	    for (int i = 0; i < datas.length - 1; i++) {
    	     svm_node node = new svm_node();
    	     node.index = i + 1;
    	     node.value = Double.parseDouble(datas[i]);
    	     vector[i] = node;
    	    }
    	    nodeSet.add(vector);
    	    double lablevalue = Double.parseDouble(datas[datas.length - 1]);
    	    label.add(lablevalue);
    	   }
    	  } catch (Exception e) {
    	   e.printStackTrace();
    	  }
    	 
    	 }

	}

