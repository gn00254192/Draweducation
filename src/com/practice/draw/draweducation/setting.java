package com.practice.draw.draweducation;

import android.content.Context;
import android.graphics.Bitmap;

public class setting {
	static int s = 0;
	static Bitmap upload = null;
	static  short pen = 0;
	static float penweith=2f;
	static String path="";
	static String[] node=new String[5];
	static int select=0;
	static float WG=(float) 0.3;
	static float WZ=(float) 0.3;
	static float WL=(float) 5;
	static float th=(float) 0.28;
	static float TH=(float) Math.cos(Math.PI/4);
	static short weight=5;
	static int screenheit,screenweight;
	static boolean done=false;
	static int check;
	static int stat=-1;
	static int imagenumber=-1;
	static String search;
	static int pin=-1;
	static Context draw;
	static String[][] imagelist=new String[100][2];
	static Bitmap takepicture= null;
	static Bitmap drawpicture= null;
	static int controlpictureload=1;
	static Boolean agree;
}
