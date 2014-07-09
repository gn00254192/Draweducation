package com.practice.draw.draweducation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.practice.draw.draweducation.BubbleSurfaceView;
import com.practice.draw.draweducation.DrawLessonOne;

import com.practice.draw.draweducation.LOG;
import com.practice.draw.draweducation.R;
import com.practice.draw.draweducation.SingleMediaScanner;
import com.practice.draw.draweducation.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

public class DrawLessonOne extends Activity {
	BubbleSurfaceView bv;
	ProgressDialog mDialog;

	// 宣告特約工人的經紀人

	private Handler mThreadHandler;

	// 宣告特約工人

	private HandlerThread mThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_lesson_one);
		DisplayMetrics metrics = new DisplayMetrics(); // 擷取螢幕大小
		getWindowManager().getDefaultDisplay().getMetrics(metrics);// 擷取螢幕大小
		setting.screenweight = metrics.widthPixels;
		setting.screenheit = metrics.heightPixels;
		bv = (BubbleSurfaceView) findViewById(R.id.surfaceView);
		bv.setSignatureBitmap(this, metrics.widthPixels, metrics.heightPixels);
		setting.pen = 0;

	}

	private Bitmap dijkstra(Bitmap image) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		mThread = new HandlerThread("name");

		// 讓Worker待命，等待其工作 (開啟Thread)

		mThread.start();

		// 找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)

		mThreadHandler = new Handler(mThread.getLooper());
		mThreadHandler.post(r1);
		setting.upload = image;
		return image;

	}

	private Runnable r1 = new Runnable() {

		public void run() {
			Long nowTime = System.currentTimeMillis();
			LOG.OUT(111111);
			Bitmap image = setting.upload;

			short r;
			short g;
			short b;
			short x, y, h, w;
			int pix, i, j, xx, yy, k;
			float c;
			h = (short) image.getHeight();
			w = (short) image.getWidth();
			bv.gray = new float[w][h];
			bv.Cost = new float[w][h];
			float[][] IX = new float[w][h];
			float[][] IY = new float[w][h];
			image = image.copy(Bitmap.Config.ARGB_8888, true);
			for (y = 0; y < h; y++) {
				for (x = 0; x < w; x++) {
					pix = image.getPixel(x, y);
					r = (short) ((pix >> 16) & 0xff); // bitwise
					g = (short) ((pix >> 8) & 0xff);
					b = (short) (pix & 0xff);
					bv.gray[x][y] = (short) FloatMath.floor((r + g + b) / 3);

				}
			}

			if ((float) h / w > (float) setting.screenheit
					/ setting.screenweight) {
				bv.rh = (short) setting.screenheit;
				bv.rw = (short) ((float) bv.rh / h * w);
				bv.rs = (float) h / bv.rh;
				bv.rx = (setting.screenweight - bv.rw) / 2;
				bv.ry = 0;
			} else {
				bv.rw = (short) setting.screenweight;
				bv.rh = (short) ((float) bv.rw / w * h);
				bv.rs = (float) w / bv.rw;
				bv.ry = (setting.screenheit - bv.rh) / 2;
				bv.rx = 0;
			}

			image = image.copy(Bitmap.Config.ARGB_8888, true);
			for (y = 0; y < h; y++) {
				for (x = 0; x < w; x++) {
					pix = image.getPixel(x, y);
					r = (short) ((pix >> 16) & 0xff); // bitwise
					g = (short) ((pix >> 8) & 0xff);
					b = (short) (pix & 0xff);
					bv.gray[x][y] = (short) FloatMath.floor((r + g + b) / 3);

				}
			}

			for (y = 1; y < h - 1; y++) {
				for (x = 1; x < w - 1; x++) {
					if ((((bv.gray[x][y] < 0) && ((bv.gray[x + 1][y] > 0)
							|| (bv.gray[x - 1][y] > 0)
							|| (bv.gray[x][y + 1] > 0) || (bv.gray[x][y - 1] > 0))) || ((bv.gray[x][y] == 0) && (((bv.gray[x + 1][y] * bv.gray[x - 1][y]) < 0)
							|| ((bv.gray[x][y + 1] * bv.gray[x][y - 1]) < 0)
							|| ((bv.gray[x + 1][y + 1] * bv.gray[x - 1][y - 1]) < 0) || ((bv.gray[x + 1][y - 1] * bv.gray[x - 1][y + 1]) < 0))))
							&& (bv.Cost[x][y] < setting.th)) {
						// image.setPixel(x, y, Color.argb(255, 0, 255, 255));
					} else
						bv.Cost[x][y] = bv.Cost[x][y] + setting.WZ + setting.WL;

				}
			}

			Long spentTime = System.currentTimeMillis();
			LOG.OUT((spentTime - nowTime) / 1000);
			setting.done = true;
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			// action cancelled
		}
		if (resultCode == RESULT_OK) {
			mDialog = new ProgressDialog(DrawLessonOne.this);
			mDialog.setMessage("Loading...");
			mDialog.setCancelable(false);
			mDialog.show();
			ImageView im = (ImageView) findViewById(R.id.ImageView1);
			Uri selectedimg = data.getData();

			try {
				Long nowTime = System.currentTimeMillis();
				im.setImageBitmap(dijkstra(decodeUri(selectedimg)));
				Long spentTime = System.currentTimeMillis();
				LOG.OUT((spentTime - nowTime) / 1000);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mDialog.dismiss();

		}
	}

	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 200;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o2);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Show home screen when pressing "back" button,
			// so that this app won't be closed accidentally

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("提醒");
			dialog.setMessage("確定要退出嗎");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			// dialog.setCancelable(false);
			dialog.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (setting.upload != null) {
								setting.upload.recycle();
								setting.upload = null;
							}
							// 按下PositiveButton要做的事
							finish();
						}
					});
			dialog.setNeutralButton("確定並存檔",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {
								File file = new File(Environment
										.getExternalStorageDirectory(),
										"MJCamera");
								// 若目錄不存在則建立目錄
								if (!file.mkdirs()) {
									Log.e("LOG_TAG", "無法建立目錄");
								}
								long time = System.currentTimeMillis();
								file = new File(file, time / 1000 + ".png");
								FileOutputStream out = new FileOutputStream(
										file);
								// 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
								bv.getSignatureBitmap().compress(
										Bitmap.CompressFormat.PNG, 90, out);
								setting.upload = bv.getSignatureBitmap();
								// 刷新並關閉檔案串流
								out.flush();
								out.close();
								SingleMediaScanner test = new SingleMediaScanner(
										DrawLessonOne.this, file);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							finish();
						}
					});
			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

			dialog.show();

			return true;

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mThreadHandler != null) {

			mThreadHandler.removeCallbacks(r1);

		}

		// 解聘工人 (關閉Thread)

		if (mThread != null) {

			mThread.quit();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu_to_draw_nocamera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.item_draw:
			Toast.makeText(this, "開始繪圖", Toast.LENGTH_SHORT).show();
			setting.pen = 0;
			break;
		case R.id.item_eraser:
			Toast.makeText(this, "選擇了橡皮擦", Toast.LENGTH_SHORT).show();
			setting.pen = 1;
			break;
		case R.id.item_mission_finish:
			Toast.makeText(this, "存檔", Toast.LENGTH_SHORT).show();
			try {
				File file = new File(Environment.getExternalStorageDirectory(),
						"MJCamera");
				// 若目錄不存在則建立目錄
				if (!file.mkdirs()) {
					Log.e("LOG_TAG", "無法建立目錄");
				}
				long time = System.currentTimeMillis();
				file = new File(file, time / 1000 + ".png");
				FileOutputStream out = new FileOutputStream(file);
				// 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
				bv.getSignatureBitmap().compress(Bitmap.CompressFormat.PNG, 90,
						out);
				setting.upload = bv.getSignatureBitmap();
				// 刷新並關閉檔案串流
				out.flush();
				out.close();
				SingleMediaScanner scan = new SingleMediaScanner(
						DrawLessonOne.this, file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.item_mission_explain:
			Toast.makeText(this, "任務說明", Toast.LENGTH_SHORT).show();
			Bitmap temp = null; 
			setting.drawpicture = bv.getSignatureBitmap();
	
			Intent intent = new Intent();
			intent.setClass(DrawLessonOne.this, Lessonone.class);
			startActivity(intent);
			
			finish();

			break;
		case R.id.item_clear:
			Toast.makeText(this, "清空", Toast.LENGTH_SHORT).show();

			AlertDialog.Builder dialog = new AlertDialog.Builder(
					DrawLessonOne.this);
			dialog.setTitle("提醒");
			dialog.setMessage("確定要清空嗎");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			// dialog.setCancelable(false);
			dialog.setPositiveButton("確定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (setting.upload != null) {
								setting.upload.recycle();
								setting.upload = null;
							}
							// 按下PositiveButton要做的事
							Intent intent = new Intent();
							intent.setClass(DrawLessonOne.this,
									DrawLessonOne.class);
							startActivity(intent);
							finish();
						}
					});

			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

			dialog.show();

			break;
		}
		return super.onOptionsItemSelected(item);

	}
}