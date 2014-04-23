package me.crossle.demo.surfacetexture;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;


public class MediaPlayerSurfaceStubActivity extends Activity
{
	private static final String TAG = MediaPlayerSurfaceStubActivity.class.getSimpleName();
	
	private GLSurfaceView mGLSurfaceView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		App app = App.from(this);
		mGLSurfaceView = new GLSurfaceView(this);
		mGLSurfaceView.setEGLContextFactory(app.getEGLContextFactory());
		mGLSurfaceView.setEGLContextClientVersion(2);
		//mGLSurfaceView.setPreserveEGLContextOnPause(true);
		mGLSurfaceView.setRenderer(app.getMediaPlayerRenderer());
		mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		MediaPlayer mp = app.getMediaPlayer();
		if(!mp.isPlaying()){
			mp.start();
		}
		
		setContentView(mGLSurfaceView);
	}
	
	/*
	@Override
	protected void onPause()
	{
		super.onPause();
		mGLSurfaceView.onPause();
	}
	
	@Override
	protected void onResume()
	{
		mGLSurfaceView.onResume();
		super.onResume();
	}
	*/
}
