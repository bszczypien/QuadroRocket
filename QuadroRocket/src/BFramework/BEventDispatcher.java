package BFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

public class BEventDispatcher 
{
	private HashMap<String, Vector<InternalDispatcherStruct>> _registreredFunc;
	
	public BEventDispatcher()
	{
		_registreredFunc = new HashMap<String, Vector<InternalDispatcherStruct>>();
	}
	
	/*
	 * todo: remember about fixing functions unregistering internally
	 * */
	public void dispatchEvent(String pEventName)
	{
		if(!_registreredFunc.containsKey(pEventName))
			return;
		Vector<InternalDispatcherStruct> lVec = _registreredFunc.get(pEventName);
		
		for(int i = 0; i < lVec.size(); ++i)
		{
			try {
				lVec.get(i).funcToBeCalled.invoke(lVec.get(i).objToBeCalled, null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * todo: remember about fixing functions unregistering internally
	 * */
	public void dispatchEvent(BEvent pEvent)
	{
		if(!_registreredFunc.containsKey(pEvent.getEventName()))
			return;
		Vector<InternalDispatcherStruct> lVec = _registreredFunc.get(pEvent.getEventName());
		
		for(int i = 0; i < lVec.size(); ++i)
		{
			try {
				lVec.get(i).funcToBeCalled.invoke(lVec.get(i).objToBeCalled, pEvent);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addEventListener(Object pReciver, String pEventName, String pMethodToBeCalled)
	{
		Method lNewMethod = null;
		try {
			lNewMethod = pReciver.getClass().getMethod(pMethodToBeCalled);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		if(lNewMethod == null)
		{
			System.out.println("BEventDispatcher: couldn't find method called: " + pMethodToBeCalled);
			return;
		}
		
		if(!_registreredFunc.containsKey(pEventName))
			_registreredFunc.put(pEventName, new Vector<InternalDispatcherStruct>());
		_registreredFunc.get(pEventName).add(new InternalDispatcherStruct(lNewMethod, pReciver));
	}
	
	public void removeEventListener(Object pReciver, String pEventName, String pMethodToBeCalled)
	{
		if(!_registreredFunc.containsKey(pEventName))
			return;
		Vector<InternalDispatcherStruct> lVec = _registreredFunc.get(pEventName);
		int lSize = lVec.size();
		for(int i = 0; i < lSize; ++i)
		{
			if(lVec.get(i).compareToFunc(pMethodToBeCalled, pReciver))
				lVec.remove(i--);
		}
	}
	
	public void removeEventListeners()
	{
		_registreredFunc = new HashMap<String, Vector<InternalDispatcherStruct>>();
	}
}
