package psers.cal.ml;
/*
 * PageRank analysis of graph structure
 * Chen Along <562963835@qq.com>
 * All rights reserved.
 * BSD LICENSE.
 * 
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;
import java.util.Scanner;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SimplePageRankAlg 
{
	/*
	 * Return the PageRank of the nodes in the graph.
	 * 
	 * PageRank computes a ranking of the nodes in the graph G based on
	 * the structure of the incoming links. It was originally designed as
	 * an algorithm to rank web pages.
	 */
	
	public PkOption parameterOption;
	public PkGraph pkGraphItem;
	PkDataProc dataProc;
	private String outFilePathString;
	
	public static void PkHelp()
	{
		System.out.println("java psers.cal.ml.SimplePageRankAlg trainDataFilePath \n"
				+ "[--sep sepValue] [--alpha alphaValue] [--maxIter maxIterValue] [--errTol errTolValue] [--outPath outPathValue]");
	}
	
	public void WriteResultToFile(String fileName,int cur)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		
		for(int i = 0;i<dataProc.maxId; i++)
		{
			printStream.println(dataProc.i2strMap.get(i) + ": " + pkGraphItem.q[cur][i]);
		}
		
		if(fileName.isEmpty())	
		{
			System.out.println(byteArrayOutputStream);
		}
		else				
		{
			File fp = new File(fileName);
			if(!fp.exists())
			{
				try 
				{
					fp.createNewFile();
				} 
				catch (IOException ex) 
				{
					// TODO: handle exception
					System.out.println(ex);
				}
			}
			try 
			{
				FileOutputStream fileOutputStream = new FileOutputStream(fp);
				new PrintStream(fileOutputStream).print(byteArrayOutputStream);
			} 
			catch (Exception ex) 
			{
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
	}
	
	
	public SimplePageRankAlg(String _trainDataFilePath,String _sep,double _alpha,int _maxIter,double _errTol,String _outFilePath)
	{
		outFilePathString = _outFilePath;
		
		parameterOption = new PkOption(_trainDataFilePath, _sep, _alpha, _maxIter, _errTol);
		
		
		dataProc = new PkDataProc(parameterOption);
		
		
		pkGraphItem = new PkGraph(parameterOption,dataProc.fromIdVec,dataProc.toIdVec,dataProc.maxId);
		
	}
	
	 
    private double calDistance(double[] q1, double[] q2) 
    {  
        double sum = 0;  
  
        if (q1.length != q2.length) 
        {  
            return -1;  
        }  
  
        for (int i = 0; i < q1.length; i++) 
        {  
            sum += Math.pow(q1[i] - q2[i], 2);  
        }  
        return Math.sqrt(sum);  
    }  
    
  
    private void vectorMulMatrix(double[][] m,double[] v,double[] ret) 
    {  
        if(m.length ==0 || m[0].length != v.length || v.length != ret.length)
        {
        	System.out.println("vectorMulMatrix has failed");
        	System.exit(1);
        }

        for (int i = 0; i < m.length; i++) {  
            double sum = 0;  
            for (int j = 0; j < m[i].length; j++) 
            {  
                double temp = m[i][j]  * v[j];  
                sum += temp;  
            }  
            ret[i] = sum; 
        }  
    }  
    
    
    public void calPageRank(double[][]q,double[][] G) 
    {  
        int maxId = pkGraphItem.maxId;
        int cur = 0;
        int iter = 1;
        
        while (iter <= parameterOption.getMaxIter()) 
        {  
            vectorMulMatrix(G, q[cur],q[cur ^ 1]); 
            double dis = calDistance(q[cur], q[cur ^ 1]);  
            System.out.println("q:");  
            pkGraphItem.printQ(q[cur]);
            if (dis <= parameterOption.getErrTol()) 
                break;   
            cur ^= 1;  
            iter += 1;
        }
        
        WriteResultToFile(outFilePathString, cur);
    }  
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	int len = args.length;

    	if(len < 1 || (len % 2 == 0))
    	{
    		PkHelp();
    		System.exit(1);
    	}
    	
    	String trainDataFilePath = args[0];		//训练数据路径
    	
    	String sep = "";						//数据每条记录字段的分隔符
    	double alpha = 0.85;					//任意时刻用户到达某页面后并继续向后浏览的概率
    	int maxIter = 20;						//最大迭代次数
    	double errTol = 0.0001;					//迭代计算的误差参数
    	String outPathString = "";				//结果输出文件路径
    	int i = 1;
    	while(i < len)
    	{
    		String inputString = args[i].toLowerCase();
    		switch (inputString) {
			case "--sep":
				sep = args[i+1];
				i += 2;
				break;
			case "--alpha":
				alpha = Double.parseDouble(args[i+1]);
				i += 2;
				break;
			case "--maxiter":
				maxIter = Integer.parseInt(args[i+1]);
				i += 2;
				break;
			case "--errtol":
				errTol = Double.parseDouble(args[i+1]);
				i += 2;
				break;
			case "--outpath":
				outPathString = args[i+1];
				i += 2;
				break;
			default:
				PkHelp();
				System.exit(1);
				break;
			}
    	}
    	
    	System.out.println("sep:" + sep);
    	SimplePageRankAlg pkAlg = new SimplePageRankAlg(trainDataFilePath, sep, alpha, maxIter, errTol,outPathString);
    	pkAlg.calPageRank(pkAlg.pkGraphItem.q, pkAlg.pkGraphItem.G);
    	System.out.println("finish pagerank calculation");
    }
    
    
}
