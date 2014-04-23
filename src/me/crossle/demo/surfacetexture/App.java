package me.crossle.demo.surfacetexture;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView.EGLContextFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;


public class App extends Application
{
	private static final String TAG = App.class.getSimpleName();
	
	private PreservingEGLContextFactory mEGLContextFactory = new PreservingEGLContextFactory();
	private MediaPlayerRenderer mRenderer;
	private MediaPlayer mMediaPlayer;
	
	
	public static App from(Context context)
	{
		return (App)context.getApplicationContext();
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
	}
	
	public Renderer getMediaPlayerRenderer()
	{
		if(mRenderer == null){
			mRenderer = new MediaPlayerRenderer(this);
			mRenderer.setMediaPlayer(getMediaPlayer());
		}
		return mRenderer;
	}
	
	public MediaPlayer getMediaPlayer()
	{
		if(mMediaPlayer == null){
			mMediaPlayer = new MediaPlayer();
			try{
				mMediaPlayer.reset();
				//AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.testvideo);
				//mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				//afd.close();
				//mMediaPlayer.setDataSource("http://www.bbc.co.uk/mediaselector/playlists/hls/tablet/ak/news_ch.m3u8");
				//mMediaPlayer.setDataSource("http://news.downloads.bbc.co.uk.edgesuite.net/mps_aac/public/news/today/1034000/1034200_aac_32k.mp4?at=DXRTlO8t96f9494c3ae5c0afe97142851cd6eb08f8c1ec1c4f7b7670fb0c0");
				mMediaPlayer.setDataSource("http://news.downloads.bbc.co.uk.edgesuite.net/mps_h264_200/public/news/uk/1071000/1071199_h264_176k.mp4?at=FKVdo_K40ecabf918079ffe1b289151e0513d75b09f395be4f7b76df9b5c0");
				mMediaPlayer.prepare();
			}
			catch(Exception e){
				Log.e(TAG, e.getMessage(), e);
			}
		}
		
		return mMediaPlayer;
	}
	
	public EGLContextFactory getEGLContextFactory()
	{
		return mEGLContextFactory;
	}
	
	
	private static class PreservingEGLContextFactory implements EGLContextFactory
	{
		private static final String TAG = App.PreservingEGLContextFactory.class.getSimpleName();
		
		private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
		private EGLContext mPreservedContext;
		
		
		@Override
		public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config)
		{
			if(mPreservedContext == null){
				int[] attrib_list = { EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
				mPreservedContext = egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, attrib_list);
				Log.w(TAG, "Creating OpenGL Context: " + mPreservedContext);
			}
			else{
				Log.w(TAG, "Returning preserved OpenGL Context: " + mPreservedContext);
			}
			return mPreservedContext;
		}
		
		@Override
		public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context)
		{
			Log.w(TAG, "Attempted destroy of OpenGL Context: " + mPreservedContext);
			// No-operation
		}
		
		public void destoryPreservedContext(EGL10 egl)
		{
			EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
			if(!egl.eglDestroyContext(display, mPreservedContext)){
				Log.e(TAG, "display:" + display + " context: " + mPreservedContext);
			}
		}
	}
}
