package psers.cal.ml;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;


public class PkGraph {
	
	private PkOption parameterOption;
	public int maxId;
	

	public double[][] q;
	

	public double[][] S;
	

	public double[][] G;
	
	// 储存训练数据集
	private Vector<Integer> fromIdVec;
	private Vector<Integer> toIdVec;
	
	public PkGraph(PkOption _parameterOption,Vector<Integer> _fromIdVec,Vector<Integer> _toIdVector,int _maxId)
	{
		parameterOption = _parameterOption;
		fromIdVec = _fromIdVec;
		toIdVec = _toIdVector;
		maxId = _maxId;
		
		q = new double[2][maxId];		
		S = new double[maxId][maxId];
		G = new double[maxId][maxId];
		
		
		for(double[] v: S)
			Arrays.fill(v, 0);
		

		initG();
		
	
		initQ();
		
	}
	

	private void initG() 
	{
		int fromId,toId;
		for(int i = 0;i<fromIdVec.size();i++)
		{
			fromId = fromIdVec.get(i);
			toId = toIdVec.get(i);
			S[toId][fromId] += 1;
		}
		
		for(int j = 0;j<maxId;j++)
		{
			double OutDegree = 0;
			for(int i =0;i<maxId;i++)
				OutDegree += S[i][j];

			for(int i =0;i<maxId;i++)
			{
				if(S[i][j] > 0.5)	//防止除零
					S[i][j] /= OutDegree;
				
				// 特别注意没有出度节点的计算
				if(OutDegree > 0.5)
					G[i][j] = parameterOption.getAlpha() * S[i][j] + (1 - parameterOption.getAlpha()) / maxId;
				else
					G[i][j] = 1.0 / maxId;	
			}
		}
	}
	
	private void initQ()
	{
		Random rd = new Random();
		for(int i = 0;i<maxId;i++)
			q[0][i] = 5 * rd.nextDouble();
	}
	
	public void printQ(double[] ptr)
	{
		for (int i = 0; i < ptr.length; i++) 
		{  
            System.out.print(ptr[i] + ", ");  
        }  
        System.out.println();  
	}
	
	
	public void printG()
	{
		System.out.println("G: =========");
		for (int i = 0; i < G.length; i++) 
		{  
            for (int j = 0; j < G[i].length; j++) {  
                System.out.print(G[i][j] + ", ");  
            }  
            System.out.println("\n");  
        }  
	}
}

