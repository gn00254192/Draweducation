package com.practice.draw.draweducation;

import java.util.Timer;
import java.util.TimerTask;
import com.practice.draw.draweducation.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstButtonView extends Activity {
	private boolean change = false;
	private Button but1;
	public ProgressDialog myDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		final TextView test = (TextView) findViewById(R.id.textView1);
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						if (change) {
							change = false;
							test.setTextColor(Color.TRANSPARENT); // 讓文字透明
						} else {
							change = true;
							test.setTextColor(Color.DKGRAY);
						}
					}
				});
			}
		};
		timer.schedule(task, 1, 800); // 參數分別是(timer需要做什麼事情，執行後多久開始執行，閃爍速度多快)
		GetButtonView();
		setButtonEvent();
	}

	public void GetButtonView() {
		but1 = (Button) findViewById(R.id.button1);
	}

	public void setButtonEvent() {
		but1.setOnClickListener(buttonListener);
	}

	private Button.OnClickListener buttonListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			myDialog = ProgressDialog.show(FirstButtonView.this, "請稍後片刻....",
					"正在載入中", true);
			new Thread() {
				public void run() {
					try {
						sleep(3000);
						Intent intent = new Intent();
						intent.setClass(FirstButtonView.this, Firstmenu.class);// Testone跳到Testtwo這個Activity
						startActivity(intent);
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {

						myDialog.dismiss();
					}
				}
			}.start();

		}

	};
}
