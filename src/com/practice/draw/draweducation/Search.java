package com.practice.draw.draweducation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.practice.draw.draweducation.Constants.Extra;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Search extends Activity {
	ProgressDialog mDialog;

	TextView tv;
	Bitmap[] imageids;
	int sum = 1;
	static int i;
	int count = 0;
	EditText edt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		Button btn = (Button) findViewById(R.id.button1);
		edt = (EditText) findViewById(R.id.editText1);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog = new ProgressDialog(Search.this);
				mDialog.setMessage("Loading...");
				mDialog.setCancelable(false);
				mDialog.show();

				PostTask2 posttask;
				posttask = new PostTask2(edt.getText().toString());

				posttask.execute();

			}
		});
		edt.setImeOptions(EditorInfo.IME_ACTION_DONE);
		edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH||actionId == EditorInfo.IME_ACTION_DONE||actionId == EditorInfo.IME_ACTION_NONE||actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					mDialog = new ProgressDialog(Search.this);
					mDialog.setMessage("Loading...");
					mDialog.setCancelable(false);
					mDialog.show();

					PostTask2 posttask;
					posttask = new PostTask2(edt.getText().toString());

					posttask.execute();

					return true;
				}
				return false;
			}
		});
		edt.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					mDialog = new ProgressDialog(Search.this);
					mDialog.setMessage("Loading...");
					mDialog.setCancelable(false);
					mDialog.show();

					PostTask2 posttask;
					posttask = new PostTask2(edt.getText().toString());

					posttask.execute();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, 0, 0, "more");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 依據itemId來判斷使用者點選哪一個item
		switch (item.getItemId()) {
		case 0:
			if (setting.stat == 1) {
				Intent intent = new Intent(this, Showmore.class);
				startActivity(intent);
			}
			finish();
			break;

		default:
		}
		return super.onOptionsItemSelected(item);
	}

	public class PostTask2 extends AsyncTask<Void, String, String> {
		public String surl;

		public PostTask2(String string) {
			surl = string;
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String doInBackground(Void... params) {

			// All your code goes in here

			// If you want to do something on the UI use progress update
			String str = "", str1 = "";

			URL myUrl = null;
			try {
				setting.search = surl;
				myUrl = new URL("http://mjimagenetapi.appspot.com/search?name="
						+ URLEncoder.encode(surl, "utf-8") + "&asc="
						+ (int) (Math.random() * (65525) + 1));

				Log.v("searchurl", myUrl.toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 取得 URLConnection

			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) myUrl.openConnection();

				conn.setDoInput(true); // 設定為可從伺服器讀取資料
				conn.setDoOutput(false); // 設定為可寫入資料至伺服器
				conn.setRequestMethod("GET"); // 設定請求方式為 GET
				// 以下是設定 MIME 標頭中的 Content-type
				conn.setRequestProperty("Content-type",
						"application/x-www-form-urlencoded");
				conn.connect(); // 開始連接
				// 透過 URLConnection 的 getOutputStream() 取的 OutputStream,
				// 並建立以UTF-8
				// 為編碼的 OutputStreamWriter

				// 利用 URLConnection 的 getInputStream() 取得 InputStream
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));

				imageids = new Bitmap[6];
				int i = 0;
				count = 0;
				if ((str = reader.readLine()) != null) {
					if (Integer.parseInt(str) == 1) {
						setting.stat = 1;
						while ((str = reader.readLine()) != null && count < 30) {
							setting.imagelist[count][0] = str;
							if ((str = reader.readLine()) != null)
								setting.imagelist[count][1] = str;
							Log.v("asdasd", setting.imagelist[count][0] + "");
							// imageids[count] =
							// getBitmapFromURL(setting.imagelist[count][1]);
							count++;

						}
					} else if (Integer.parseInt(str) == 2) {
						setting.stat = 2;
						while ((str = reader.readLine()) != null && count < 30) {
							str = str.substring(2);
							str1 = str.substring(str.indexOf(",") + 1);
							setting.imagelist[count][0] = str.substring(0,
									str.indexOf(",") + str1.indexOf(",") + 2);
							Log.v("url3", setting.imagelist[count][0]);
							setting.imagelist[count][1] = str.substring(
									str.indexOf(",") + str1.indexOf(",") + 2,
									str.length());
							// imageids[count] =
							// getBitmapFromURL(setting.imagelist[count][1]);
							count++;

						}
					}
					LOG.OUT(setting.stat);
				}
				conn.disconnect();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;

		}

		protected void onPostExecute(String reString) {
			if (setting.stat == 2) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						Search.this);
				dialog.setMessage("找不到該物件輪廓，請自行進行繪圖");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								// 按下PositiveButton要做的事
								if (count > 0) {

									Constants.IMAGES = new String[count];
									for (int i = 0; i < count; i++) {

										Constants.IMAGES[i] = setting.imagelist[i][1];
										Log.v("asdasd", Constants.IMAGES[i]
												+ "");

									}
									mDialog.dismiss();

									DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
											.cacheInMemory(false)
											// 1.8.6包使用時候，括號裏面傳入參數true
											.cacheOnDisc()
											// 1.8.6包使用時候，括號裏面傳入參數true
											.imageScaleType(
													ImageScaleType.IN_SAMPLE_INT)
											.bitmapConfig(Bitmap.Config.RGB_565)
											.build();

									ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
											getApplicationContext())
											.defaultDisplayImageOptions(
													defaultOptions)
											.threadPriority(
													Thread.NORM_PRIORITY - 2)
											.denyCacheImageMultipleSizesInMemory()
											.discCacheFileNameGenerator(
													new Md5FileNameGenerator())
											.tasksProcessingOrder(
													QueueProcessingType.LIFO)
											// .writeDebugLogs() // Remove for
											// release app
											.build();

									ImageLoader.getInstance().init(config);
									ImageLoader imageLoader = ImageLoader
											.getInstance();
									imageLoader.clearDiscCache();
									imageLoader.clearMemoryCache();
									Intent intent = new Intent(Search.this,
											ImageGridActivity.class);
									intent.putExtra(Extra.IMAGES,
											Constants.IMAGES);
									startActivity(intent);
									Search.this.finish();
								} else {
									AlertDialog.Builder dialog1 = new AlertDialog.Builder(
											Search.this);
									dialog1.setMessage("找不到該物件，請重新搜尋");
									dialog1.setIcon(android.R.drawable.ic_dialog_alert);
									dialog1.setCancelable(false);
									dialog1.setPositiveButton(
											"OK",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
													// 按下PositiveButton要做的事

												}
											});

									dialog1.show();

								}
							}
						});

				dialog.show();
				mDialog.dismiss();
			} else if (count > 0) {

				Constants.IMAGES = new String[count];
				for (int i = 0; i < count; i++) {

					Constants.IMAGES[i] = setting.imagelist[i][1];
					Log.v("asdasd", Constants.IMAGES[i] + "");

				}
				mDialog.dismiss();

				DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
						.cacheInMemory(false)
						// 1.8.6包使用時候，括號裏面傳入參數true
						.cacheOnDisc()
						// 1.8.6包使用時候，括號裏面傳入參數true
						.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
						.bitmapConfig(Bitmap.Config.RGB_565).build();

				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
						getApplicationContext())
						.defaultDisplayImageOptions(defaultOptions)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.denyCacheImageMultipleSizesInMemory()
						.discCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						// .writeDebugLogs() // Remove for release app
						.build();

				ImageLoader.getInstance().init(config);
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.clearDiscCache();
				imageLoader.clearMemoryCache();
				Intent intent = new Intent(Search.this, ImageGridActivity.class);
				intent.putExtra(Extra.IMAGES, Constants.IMAGES);
				startActivity(intent);
				Search.this.finish();
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						Search.this);
				dialog.setMessage("找不到該物件，請重新搜尋");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);
				dialog.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// 按下PositiveButton要做的事

							}
						});

				dialog.show();
				mDialog.dismiss();

			}
			Log.v("asd", "done");
			super.onPostExecute(reString);

		}

		protected void onProgressUpdate(String progress) {

		}

	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			Log.v("asd", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(5000);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			if (myBitmap != null)
				return getResizedBitmap(myBitmap, 120, 120);
			else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		float finalscale = (scaleWidth < scaleHeight) ? scaleWidth
				: scaleHeight;
		if (finalscale > 1) {
			return bm;
		}
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(finalscale, finalscale);
		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public class PostTaskforchoice extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Log.v("adasd", "ewfwefweffewfwewfwef");

			// All your code goes in here

			// If you want to do something on the UI use progress update
			String str = "";

			URL myUrl = null;
			try {
				myUrl = new URL("http://mjimagenetapi.appspot.com/choice?key="
						+ setting.imagelist[setting.imagenumber][0]);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// 取得 URLConnection
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) myUrl.openConnection();

				conn.setDoInput(true); // 設定為可從伺服器讀取資料
				conn.setDoOutput(false); // 設定為可寫入資料至伺服器
				conn.setRequestMethod("GET"); // 設定請求方式為 GET
				// 以下是設定 MIME 標頭中的 Content-type
				conn.setRequestProperty("Content-type",
						"application/x-www-form-urlencoded");
				conn.connect(); // 開始連接
				// 透過 URLConnection 的 getOutputStream() 取的 OutputStream,
				// 並建立以UTF-8
				// 為編碼的 OutputStreamWriter

				// 利用 URLConnection 的 getInputStream() 取得 InputStream
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				int count = 0;
				imageids = new Bitmap[6];
				int i = 0;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute() {
			super.onPostExecute(null);

		}

		protected void onProgressUpdate() {

		}

	}
}
