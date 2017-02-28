package psers.cal.ml;

public class MLEOption 
{
	//	The path of trainData to construct undirected graph
	private String trainDataFilePath;
	
	
	public MLEOption(String _trainDataFilePath)
	{
		trainDataFilePath = _trainDataFilePath;
	}
	
	
	
	public String getTrainDataFilePath()
	{
		return trainDataFilePath;
	}

}
