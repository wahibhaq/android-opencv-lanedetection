 
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
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * This is the main Activity which takes Camera input, captures frame, interact with OpenCV library, process frame and display overlays 
 * on lanes detection. It uses Hough Value which is provided by the VerticalSliderActivity. This java activity acts as the Controller
 * here and core image processing work is done by the C++ files
 * 
 * @author Wahib-Ul-Haq, wahib.tech@gmail.com, 2014
 *
 * contains code taken from:
 * 
 * "Lane detection using OpenCV" 
 * https://github.com/jdorweiler/lane-detection
 * Acknowledgments: jdorweiler
 * Contact: jason@transistor.io
 */

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
					System.loadLibrary("LaneDetectionNative"); //library module name defined in Android.mk
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
    
    
    @Override
    public void onCameraViewStarted(int width, int height) {
    	
      Log.v("Screen Resolution","Height: "+height+" Width: "+width); 
      
    	
   	img = new Mat(height, width, CvType.CV_8UC4);
 
    };
    
    @Override
    public void onCameraViewStopped() {
    	img.release();
    };
    
    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	
    	
    	mainDelegate(inputFrame.rgba().getNativeObjAddr(), img.getNativeObjAddr(), houghValue);
    	    	
    	return img;
    	
    	
    }

    
    public native void mainDelegate(long input, long output, int houghValue);

        

}
