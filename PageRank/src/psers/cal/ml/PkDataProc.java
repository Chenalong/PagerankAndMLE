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

public class PkDataProc {
	
	//	The path of trainData to construct undirected graph
	private PkOption parameterOption;
	
	
	public Vector<Integer> fromIdVec;
	public Vector<Integer> toIdVec;
	public Map<Integer, String> i2strMap;
	public Map<String, Integer> str2iMap;
	public int maxId;
	
	public PkDataProc(PkOption _parameterOption)
	{
		parameterOption = _parameterOption;
		fromIdVec = new Vector<Integer>();
		toIdVec = new Vector<Integer>();
		i2strMap = new HashMap<Integer, String>();
		str2iMap = new HashMap<String, Integer>();
		maxId = 0;
		
		readTrainData();
	}
	
	
	private void readTrainData()
	{
		String trainFilePathString = parameterOption.getTrainDataFilePath();
		String sep = parameterOption.getSep();
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
				String[] filed;
				if(sep.equals(" ") || sep.isEmpty())
					filed = str.split("\\s+");
				else 
				{
					filed = str.split(sep);
				}
				if(filed.length != 2)
				{
					System.out.println("data farmat is wrong");
					continue;
				}
				
				if(str2iMap.containsKey(filed[0]))
					fromIdVec.add(str2iMap.get(filed[0]));
				else 
				{
					fromIdVec.add(maxId);
					i2strMap.put(maxId, filed[0]);
					str2iMap.put(filed[0], maxId++);
				}
				
				if(str2iMap.containsKey(filed[1]))
					toIdVec.add(str2iMap.get(filed[1]));
				else 
				{
					toIdVec.add(maxId);
					i2strMap.put(maxId, filed[1]);
					str2iMap.put(filed[1], maxId++);
				}
			}
			bReader.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
	
	
}
