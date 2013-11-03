package gl_quadroSimulator;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/*
 * main activity that keeps opengl es content (with simulator of quadrocopter remote controlling)
 * */
public class GLESCanvas extends Activity
{
	private GLSurfaceView _glSurface;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		_glSurface = new SimulatorSufaceView(this);
        setContentView(_glSurface);
	}
	
	@Override     
	public void onDestroy()
	{   
		super.onDestroy();
	}
	
	@Override protected void onStart() 
	{
		super.onStart();
	}
}
