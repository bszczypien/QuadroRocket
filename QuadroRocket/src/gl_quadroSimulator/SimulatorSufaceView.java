package gl_quadroSimulator;

import android.content.Context;
import android.opengl.GLSurfaceView;

/*
 * SimulatorSufaceView - create the environment of simulator according to OpenGL ES 2.0
 * */
public class SimulatorSufaceView extends GLSurfaceView
{
	public SimulatorSufaceView(Context context)
	{
        super(context);
        
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new SimulatorRenderer());
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
}
