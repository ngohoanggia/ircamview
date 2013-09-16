package com.example.ircampreview;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

// Helper Class to set up the IR Grid
public class IRGrid {
	// Variables concerning the grid
	private int rows, cols;
	private ColorPicker mColorPicker = new ColorPicker();
	
	// Miscellaneous Variables
	DecimalFormat d = new DecimalFormat("#.##");
	
	public IRGrid(Context context, LinearLayout mIRTbl){
		// Set the row and col dimensions of the grid
		rows = 4;
		cols = 16;
		initIRGrid(context, mIRTbl);
	}
	
	// Initialize the grid following the dimension of the IR grid
	private void initIRGrid(Context context, LinearLayout mIRTbl){
		LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
		LinearLayout.LayoutParams colParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
		
		//Log.e(LOG_TAG, "First Child count" + mIRTbl.getChildCount());
		for (int i = 0; i < rows; i++){
			LinearLayout mTblRow = new LinearLayout(context);
			mTblRow.setLayoutParams(rowParams);
			mTblRow.setOrientation(LinearLayout.HORIZONTAL);
			//mTblRow.setBackgroundResource(R.drawable.grid_cell);
			
			mIRTbl.addView(mTblRow);
		}
		
		for (int i = 0; i < mIRTbl.getChildCount() ; i++){
			LinearLayout row = (LinearLayout) mIRTbl.getChildAt(i);
			//Log.e(LOG_TAG, "Child " + row);
			for (int k = 0; k < cols; k++){
				TextView mText = new TextView(context);
				
				mText.setGravity(Gravity.CENTER);
				mText.setTextColor(Color.WHITE);
				mText.setLayoutParams(colParams);
				mText.setBackgroundResource(R.drawable.grid_cell);
				
				row.addView(mText);
			}
		}
		//Log.e(LOG_TAG, "Child count" + mIRTbl.getChildCount());
	}
	
	// Update the grid cells according to the values array passed in
	public void updateIRGrid(float[] values, LinearLayout mIRTbl){
		String[] colors = mColorPicker.getColorMatrix(values);
		
		int counter = 0;
		for (int i = 0; i < mIRTbl.getChildCount(); i++){
			LinearLayout row = (LinearLayout) mIRTbl.getChildAt(i);
			for (int j = 0; j < row.getChildCount(); j++){
				TextView cell = (TextView) row.getChildAt(j);
				
				// Update the text and the color of the cells
				cell.setText(d.format(values[counter]));
				cell.setBackgroundColor(Color.parseColor(colors[counter]));
				counter = counter + 1;
			}
		}
	}
}
