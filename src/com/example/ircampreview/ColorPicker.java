package com.example.ircampreview;

public class ColorPicker {
	public ColorPicker(){}
	
	private String colorFloatToHex(float value){
		String red = Integer.toHexString((int) (255 * value));
		String green = Integer.toHexString((int) (255 * (1-value)));
		String blue = Integer.toHexString(5);	// arbitrary blue value;
		String alpha = Integer.toHexString(100); // arbitrary alpha value
		
		if (red.length() == 1){
			red = "0" + red;
		}
		if (green.length() == 1){
			green = "0" + green;
		}
		if (blue.length() ==1){
			blue = "0" + blue;
		}
		
		String colorHex = "#" + alpha + red + green + blue;
		return colorHex;
	}
	
	public String[] getColorMatrix(float[] values){
		String[] colorMatrix = new String[values.length]; 
		for (int i = 0; i < values.length; i++){
			colorMatrix[i] = colorFloatToHex(values[i]);
		}
		
		return colorMatrix;
	}
}
