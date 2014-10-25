/*-----------------------------------------------------------------------------------------*\
Wahib-ul-Haq


 \*----------------------------------------------------------------------------------------*/

 
package tum.andrive.lanedetection;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class LaneDetector extends Activity implements CvCameraViewListener2 {

	private CameraBridgeViewBase mOpenCvCameraView;
	private static final String TAG = "OCVSample::Activity";
	private Mat img;
	private int houghValue;
	private Mat mRgba;
	private int screen_w, screen_h;  
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:
				{
					Log.i(TAG, "OpenCV loaded successfully");
					System.loadLibrary("LaneDetectionNative");
					mOpenCvCameraView.enableView();
					
				}
				break;
				
				default:
				{
					super.onManagerConnected(status);
				} break;
			}

		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_lane_test);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.AndriveLaneView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        
        Intent intent = getIntent();
        houghValue = Integer.valueOf(intent.getStringExtra("houghvalue"));
        Toast.makeText(getApplicationContext(), String.valueOf(houghValue), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPause() {
    	super.onPause();
    	if (mOpenCvCameraView != null) {
    		mOpenCvCameraView.disableView();
		}
    };
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (mOpenCvCameraView != null) {
			mOpenCvCameraView.disableView();
		}
    };
    
//    android.hardware.Camera.Size set_resolution(){  
//        List<android.hardware.Camera.Size> mResolutionList = mOpenCvCameraView.getw
//     int lowest= mResolutionList.size()-1;  
//     android.hardware.Camera.Size resolution = mResolutionList.get(lowest);  
//     // This has worked fine with my phone, but not sure the resolutions are sorted  
//     mOpenCvCameraView.setResolution(resolution);  
//     return resolution;   
//   }  
    
    
    @Override
    public void onCameraViewStarted(int width, int height) {
    	
    	 //android.hardware.Camera.Size r= set_resolution();   
         //Do we know if the two of them (view and camera) match  
         //screen_w=r.width;  
         //screen_h=r.height;  
         
      //mRgba = new Mat(screen_w, screen_h, CvType.CV_8UC4);  
      //mGrey = new Mat(screen_w, screen_h, CvType.CV_8UC1); 
      
      Log.v("Screen Resolution","Height: "+height+" Width: "+width); 
      //mRgba = new Mat(width, height, CvType.CV_8UC4);
      
    	
    	img = new Mat(height, width, CvType.CV_8UC4);
    };
    
    @Override
    public void onCameraViewStopped() {
    	img.release();
    };
    
    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	
    	//nativeThreshold(inputFrame.rgba().getNativeObjAddr(), img.getNativeObjAddr());
    	//nativeCannyEdge(inputFrame.rgba().getNativeObjAddr(), img.getNativeObjAddr());
    	
    	
    	//Mat low_res = new Mat(screen_w, screen_h, CvType.CV_8UC1);  
        // 1280 x 720  
        //Log.v("MyActivity","width: "+screen_w+" height: "+screen_h);  
        //Imgproc.resize(mGrey,low_res,new Size(),0.25,0.25,Imgproc.INTER_LINEAR);  
        //Imgproc.equalizeHist( low_res, low_res );   
        
    	//Imgproc.resize(inputFrame.rgba(), inputFrame.rgba(), new Size(), 0.25, 0.25, Imgproc.INTER_LINEAR);
    	
    	mainDelegate(inputFrame.rgba().getNativeObjAddr(), img.getNativeObjAddr(), houghValue);
    	    	
    	return img;
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.andrive, menu);
        return true;
    }
    
    //public native void nativeThreshold(long input, long output);
    
    //public native void nativeCannyEdge(long input, long output);
    
    public native void mainDelegate(long input, long output, int houghValue);
    //public native int processImage(long output);

        

}
