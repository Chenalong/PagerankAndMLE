package psers.cal.ml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MLEDataProc 
{
	//	The path of trainData to construct undirected graph
	private MLEOption parameterOption;
	
	public Vector<Double> heightIntegers;
	public int PerNum;
	
	public MLEDataProc(MLEOption _parameterOption)
	{
		parameterOption = _parameterOption;
		heightIntegers = new Vector<Double>();
		PerNum = 0;
		readTrainData();
	}
	
	private void readTrainData()
	{
		String trainFilePathString = parameterOption.getTrainDataFilePath();
		try 
		{
			File trainFile = new File(trainFilePathString);
			if(!trainFile.isFile() || !trainFile.exists())
			{
				System.out.println("Open File failed");
				System.exit(1);
			}
			FileInputStream fiStream = new FileInputStream(trainFile);
			InputStreamReader iStreamReader = new InputStreamReader(fiStream);	//�ֽ���ͨ���ַ���������
			BufferedReader bReader = new BufferedReader(iStreamReader);			
			String str = "";
			while((str = bReader.readLine()) != null)
			{
				str = str.trim();
				if(str.isEmpty())
				{
					System.out.println("it is empty");
					continue;
				}
				heightIntegers.add(new Double(str));
				PerNum += 1;
			}
			bReader.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO: handle exception
			System.out.println(e);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
	
}
