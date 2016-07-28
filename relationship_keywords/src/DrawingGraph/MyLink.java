package DrawingGraph;

public class MyLink {
	double weight;
	String id;
	
	public MyLink(double weight, String id)
	{
		this.id = id;
		this.weight = weight;
	}
	
	public double GetWeight()
	{
		return weight;
	}
	
	public String GetId()
	{
		return id;
	}

}
