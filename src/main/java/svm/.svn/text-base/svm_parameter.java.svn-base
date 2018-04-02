
package svm;
public class svm_parameter
{
	/* svm_type */
	public static final int C_SVC = 0;
	public static final int NU_SVC = 1;
	public static final int ONE_CLASS = 2;
	public static final int EPSILON_SVR = 3;
	public static final int NU_SVR = 4;

	/* kernel_type */
	public static final int LINEAR = 0;
	public static final int POLY = 1;
	public static final int RBF = 2;
	public static final int SIGMOID = 3;

	public int svm_type;
	public int kernel_type;
	public double degree;	// for poly(��Զ���ʽ�˺���)(Ĭ��3)
	public double gamma;	// for poly/rbf/sigmoid(Ĭ��1/ k)
	public double coef0;	// for poly/sigmoid(Ĭ��0)

	// these are for training only
	public double cache_size; // ����cache�ڴ��С����MBΪ��λ(Ĭ��40)
	public double eps;	// stopping criteria�����������ֹ�о�(Ĭ��0.001)
	public double C;	// for C_SVC, EPSILON_SVR and NU_SVR(Ĭ��1)ѡ���Խ�󣬱�ʾ�Դ������ͷ��̶�Խ�󣬿��ܻᵼ��ģ�͹����
	public int nr_weight;		// for C_SVC(Ĭ��1)
	public int[] weight_label;	// for C_SVC
	public double[] weight;		// for C_SVC
	public double nu;	// for NU_SVC, ONE_CLASS, and NU_SVR(Ĭ��0.5)

	public double p;	// for EPSILON_SVR(Ĭ��0.1)
	public int shrinking;	// use the shrinking heuristics�Ƿ�ʹ������ʽ��0��1(Ĭ��1)
}
