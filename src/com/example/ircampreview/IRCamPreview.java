package com.example.ircampreview;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IRCamPreview extends Activity {
	// Variables for controlling the Camera Preview
	private int cameraID;
	private Camera mCamera;
	private CameraPreview mPreview;
	
	// Variables for the IR Grid
	private LinearLayout mIRTbl;
	private IRGrid mIRGrid;
	
	// Miscellaneous variables
	private static final String LOG_TAG = "Ecube IR Cam Preview";
	DecimalFormat d = new DecimalFormat("#.##");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ircam_preview);
		
		// Initialize Camera controller
		int cameraId = checkCameraHardware(this);
		if (cameraID >= 0)	
			try {
				mCamera = Camera.open(cameraId);
			} catch (Exception e){
				Log.e(LOG_TAG, "Cannot access the camera");
			}
		
		// Add CameraPreview to layout
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		// Initialize the IR Grid
		mIRTbl = (LinearLayout) findViewById(R.id.ir_grid_tbl);
		mIRGrid = new IRGrid(this.getApplicationContext(), mIRTbl);
		
		//Stub
		float[] colorValues = new float[64];
		for (int i = 0; i < 64; i++){
			colorValues[i] = (float) Math.random();
			//Log.e(LOG_TAG, "ColorValues: " + 255 * colorValues[i]);
		}
		mIRGrid.updateIRGrid(colorValues, mIRTbl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ircam_preview, menu);
		return true;
	}

	// check if there is a camera on the device
	private int checkCameraHardware(Context context){
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			cameraID = findFrontFacingCamera();
			if (cameraID < 0){
				Toast.makeText(this, "No front facing camera found", Toast.LENGTH_LONG).show();
			} else {
				return cameraID;
			}
		} else {
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
			return -1;
		}
		return -1;
	}
	
	// return fron facing camera id
	private int findFrontFacingCamera(){
		int cameraID = -1;
		
		for (int i = 0; i < Camera.getNumberOfCameras(); i++){
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK){
				cameraID = i;
				break;
			}
		}
		return cameraID;
	}
}
