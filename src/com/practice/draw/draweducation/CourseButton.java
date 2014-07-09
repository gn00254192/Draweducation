package com.practice.draw.draweducation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CourseButton extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_button);
	}

	public void Lessonone(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessonone.class);
		startActivity(intent);
	}

	public void Lessontwo(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessontwo.class);
		startActivity(intent);
	}

	public void Lessonthree(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessonthree.class);
		startActivity(intent);
	}

	public void Lessonfour(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessonfour.class);
		startActivity(intent);
	}

	public void Lessonfive(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessonfive.class);
		startActivity(intent);

	}

	public void Lessonsix(View v) {
		Intent intent = new Intent();
		intent.setClass(CourseButton.this, Lessonsix.class);
		startActivity(intent);
	}
}
