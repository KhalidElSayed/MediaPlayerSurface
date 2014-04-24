package me.crossle.demo.surfacetexture;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MediaPlayerSurfaceStubActivity extends Activity
{
	private static final String TAG = MediaPlayerSurfaceStubActivity.class.getSimpleName();
	
	private GLSurfaceView mGLSurfaceView1;
	private GLSurfaceView mGLSurfaceView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		App app = App.from(this);
		setContentView(R.layout.activity_main);
		
		GLSurfaceView sv = (GLSurfaceView)findViewById(R.id.surface1);
		mGLSurfaceView1 = sv;
		sv.setEGLContextFactory(app.getEGLContextFactory());
		sv.setEGLContextClientVersion(2);
		sv.setPreserveEGLContextOnPause(true);
		sv.setRenderer(app.getMediaPlayerRenderer());
		sv.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		sv = (GLSurfaceView)findViewById(R.id.surface2);
		mGLSurfaceView2 = sv;
		sv.setEGLContextFactory(app.getEGLContextFactory());
		sv.setEGLContextClientVersion(2);
		sv.setPreserveEGLContextOnPause(true);
		sv.setRenderer(app.getMediaPlayerRenderer());
		sv.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		MediaPlayer mp = app.getMediaPlayer();
		if(!mp.isPlaying()){
			mp.start();
		}
		
		findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}
	
	/*
	 * @Override
	 * protected void onPause()
	 * {
	 * super.onPause();
	 * mGLSurfaceView.onPause();
	 * }
	 * 
	 * @Override
	 * protected void onResume()
	 * {
	 * mGLSurfaceView.onResume();
	 * super.onResume();
	 * }
	 */
}
