package com.practice.draw.draweducation;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Lessonone extends Activity {

		private ViewPager viewPager ;
		private View currentViewPage;
		private ImageView startbtn;
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_guide);
	         
	        viewPager = (ViewPager)this.findViewById(R.id.viewPager);
	
	        final LayoutInflater li = LayoutInflater.from(this);
	        final Intent intent = new Intent();	
	    	intent.setClass(this,DrawLessonOne.class);
	        //new 一個ArrayList<view> 來存放每個Page
	    	final ArrayList<View> arrayView = new ArrayList<View>();
	       
	        arrayView.add(li.inflate(R.layout.explainexampleone, null));
	        arrayView.add(li.inflate(R.layout.lesson_one_explain, null));      
	        PagerAdapter apdter = new PagerAdapter() {
	    
	   @Override
	   public boolean isViewFromObject(View arg0, Object arg1) {
		    // TODO Auto-generated method stub
		    return arg0 == arg1;
	   }
	   
	   @Override
	   public int getCount() {
		    // TODO Auto-generated method stub
		    return arrayView.size();
	   }
	 
	   @Override
	   public void destroyItem(ViewGroup container, int position,
	     Object object) {
	    // TODO Auto-generated method stub
	    ((ViewPager)container).removeView(arrayView.get(position));
	   }
	 
	//   @Override
	//   public CharSequence getPageTitle(int position) {
//	    // TODO Auto-generated method stub
//	     
//	    return titleArray.get(position);//這裡需回傳Title的名稱,position就是每個Page的index 
	//   }
	 
	   @Override
	   public Object instantiateItem(ViewGroup container, int position) {
	    // TODO Auto-generated method stub
	    ((ViewPager)container).addView(arrayView.get(position));
	    return arrayView.get(position);
	   }
	    
	  };
	  
	  viewPager.setAdapter(apdter);
	  viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {//監聽當ViewPager被改變Page時
	    
	   @Override
	   public void onPageSelected(int arg0) {
	    // TODO Auto-generated method stub
 		   
		   if (arg0==1)
		   {
			   startbtn = (ImageView) findViewById(R.id.startbtn);
			  
			 
			   startbtn.setOnClickListener(new Button.OnClickListener(){ 
			      @Override
			      public void onClick(View v) {
		       //    TODO Auto-generated method stub
			    	  
						startActivity(intent);
						finish();
			      }         
			  });   
		   }
	   }
	    
	    
	   @Override
	   public void onPageScrolled(int index, float arg1, int arg2) {
	     
	    //onPageScrolled 
		   
		   
	   }
	    
	   @Override
	   public void onPageScrollStateChanged(int index) {
	     
		  
	   }
	  });
	  
	  
	  
	  

	  
	  
	    }
	}