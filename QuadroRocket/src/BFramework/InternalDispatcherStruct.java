package BFramework;

import java.lang.reflect.Method;

public class InternalDispatcherStruct 
{
	public String func;
	public Object objToBeCalled;
	public Method funcToBeCalled;
	
	public InternalDispatcherStruct(Method pMethod, Object pObject)
	{
		funcToBeCalled = pMethod;
		func = funcToBeCalled.getName();
		objToBeCalled = pObject;
	}
	
	public boolean compareToFunc(String pFuncName, Object pObject)
	{
		return func.equals(pFuncName) && (pObject == objToBeCalled);
	}
}
