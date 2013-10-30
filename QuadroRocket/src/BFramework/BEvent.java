package BFramework;

public class BEvent 
{
	private String _name;
	
	public BEvent(String pName)
	{
		_name = pName;
	}
	
	public String getEventName()
	{
		return _name;
	}
	
	public boolean compareEventName(String pName)
	{
		return pName.equals(_name);
	}
}
