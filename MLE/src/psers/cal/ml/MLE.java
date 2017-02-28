package psers.cal.ml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;



public class MLE {
	public MLEOption parametarMleOption;
	public MLEDataProc dataProc;
	
	private double miu;
	private double nuo;
	
	public MLE(String trainDataFilePath)
	{
		parametarMleOption = new MLEOption(trainDataFilePath);
		dataProc = new MLEDataProc(parametarMleOption);
	}
	
	public static void MLEHelp()
	{
		System.out.println("java MLE psers.cal.ml.trainDataFilePath [--outPath outPathValue]\n");
	}
	
	public void calMLE()
	{
		double sum = 0;
		for(int i = 0;i<dataProc.heightIntegers.size();i++)
		{
			sum += dataProc.heightIntegers.get(i);
		}
		miu = sum / dataProc.PerNum;
		
		sum = 0;
		for(int i = 0;i<dataProc.heightIntegers.size();i++)
		{
			sum += Math.pow((dataProc.heightIntegers.get(i)-miu),2);
		}
		
		nuo = Math.sqrt(sum/dataProc.PerNum);
	}
	
	public void WriteResultToFile(String fileName)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);

		printStream.println("miu: " + miu +  " nuo: " + nuo);
		
		
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
	
	public static void main(String[] argv) 
	{
		if(argv.length != 1 && argv.length != 3)
		{
			MLEHelp();
			System.exit(1);
		}
		String outFilePath = "";
		String trainFilePath = argv[0];
		if(argv.length ==3)
		{
			if(argv[1].toLowerCase().equals("--outpath"))
				outFilePath = argv[2];
			else 
			{
				MLEHelp();
				System.exit(1);
			}
		}
		MLE mle = new MLE(trainFilePath);
		mle.calMLE();
		mle.WriteResultToFile(outFilePath);
		
		System.out.println("finish MLE");

	}
}
