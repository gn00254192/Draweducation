package com.practice.draw.draweducation;

public class filter {
	/* w, h*/


	public static float[][] filter2(float[][] gray, float[][] Filter, int w,
			int h) {
		// TODO Auto-generated method stub
		float[][] result = new float[w][h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				result[x][y]=0;
			}
		}
		int fsize=(int) Math.floor(Filter.length/2);
		for (int y = fsize; y < h-fsize; y++) {
			for (int x = fsize; x < w-fsize; x++) {
				for (int i=-fsize;i<=fsize;i++){
					for(int j=-fsize;j<=fsize;j++)
					{
						result[x][y]=(result[x][y]+gray[x+i][y+j]*Filter[fsize+i][fsize+j]);
					}
				}
			}
		}
		return result;
	}


}
