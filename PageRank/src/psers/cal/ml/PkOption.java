package psers.cal.ml;

import java.util.*;

public class PkOption
{
	
	//	The path of trainData to construct undirected graph
	private String trainDataFilePath;
	
	//	The separator of fields in trainData, default = ' '
	private String sep;
	
	//	Damping parameter for PageRank, default = 0.85
	private double alpha;
	
	//  Maximum number of iterations in power method eigenvalue solver. default = 20
	private int maxIter;
	
	//	Error tolerance used to check convergence in power method solver. default = 0.001
	private double errTol;
	
	
	public PkOption(String _trainDataFilePath,String _sep,double _alpha,int _maxIter,double _errTol)
	{
		trainDataFilePath = _trainDataFilePath;
		sep = _sep;
		alpha = _alpha;
		maxIter = _maxIter;
		errTol = _errTol;
	}
	
	public String getTrainDataFilePath()
	{
		return trainDataFilePath;
	}
	
	public void setSep(String _sep) {
		sep = _sep;
	}
	
	public String getSep() 
	{
		return sep;
	}
	
	public void setAlpha(double _alpha)
	{
		alpha = _alpha;
	}
	
	public double getAlpha() 
	{
		return alpha;
	}
	
	public void setMaxIter(int _maxIter)
	{
		maxIter = _maxIter;
	}
	
	public int getMaxIter() 
	{
		return maxIter;
	}
	
	public void setErrTol(double _errTol)
	{
		errTol = _errTol;
	}
	
	public double getErrTol() 
	{
		return errTol;
	}

}
