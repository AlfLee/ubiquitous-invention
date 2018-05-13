package com.telecom.naivebayes;

public class BayesModel {
    private boolean isclassify;
    private double mean;
    private double dev;
    private double prob;
    private double label;
    public double getLabel() {
		return label;
	}
	public void setLabel(double label) {
		this.label = label;
	}
	private double[] featurelabeltype;
    private double[] featurelabelprob;
    

	public boolean isIsclassify() {
		return isclassify;
	}
	public double[] getFeaturelabeltype() {
		return featurelabeltype;
	}
	public void setFeaturelabeltype(double[] featurelabeltype) {
		this.featurelabeltype = featurelabeltype;
	}
	public double[] getFeaturelabelprob() {
		return featurelabelprob;
	}
	public void setFeaturelabelprob(double[] featurelabelprob) {
		this.featurelabelprob = featurelabelprob;
	}
	public void setIsclassify(boolean isclassify) {
		this.isclassify = isclassify;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getDev() {
		return dev;
	}
	public void setDev(double dev) {
		this.dev = dev;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
}
