package com.practice.draw.draweducation;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.practice.draw.draweducation.BubbleSurfaceView;
import com.practice.draw.draweducation.DrawLessonTwo;

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
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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

public class DrawLessonTwo extends Activity {
	BubbleSurfaceView bv;
	private ProgressDialog dialog = null;
	ProgressDialog mDialog;

	// �ŧi�S���u�H���g���H

	private Handler mThreadHandler;

	// �ŧi�S���u�H
	File file;
	private HandlerThread mThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_lesson_two);
		DisplayMetrics metrics = new DisplayMetrics(); // �^���ù��j�p
		getWindowManager().getDefaultDisplay().getMetrics(metrics);// �^���ù��j�p
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

		// ��Worker�ݩR�A���ݨ�u�@ (�}��Thread)

		mThread.start();

		// ���S���u�H���g���H�A�o�ˤ~�ଣ���u�@ (���Thread�W��Handler)

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
			mDialog = new ProgressDialog(DrawLessonTwo.this);
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
			dialog.setTitle("����");
			dialog.setMessage("�T�w�n�h�X��");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			// dialog.setCancelable(false);
			dialog.setPositiveButton("�T�w",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (setting.upload != null) {
								setting.upload.recycle();
								setting.upload = null;
							}
							// ���UPositiveButton�n������
							finish();
						}
					});
			dialog.setNeutralButton("�T�w�æs��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {
								File file = new File(Environment
										.getExternalStorageDirectory(),
										"MJCamera");
								// �Y�ؿ����s�b�h�إߥؿ�
								if (!file.mkdirs()) {
									Log.e("LOG_TAG", "�L�k�إߥؿ�");
								}
								long time = System.currentTimeMillis();
								file = new File(file, time / 1000 + ".png");
								FileOutputStream out = new FileOutputStream(
										file);
								// �N Bitmap���Y�����w�榡���Ϥ��üg�J�ɮצ�y
								bv.getSignatureBitmap().compress(
										Bitmap.CompressFormat.PNG, 90, out);
								setting.upload = bv.getSignatureBitmap();
								// ��s�������ɮצ�y
								out.flush();
								out.close();
								SingleMediaScanner test = new SingleMediaScanner(
										DrawLessonTwo.this, file);
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
			dialog.setNegativeButton("����",
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

		// �Ѹu�u�H (����Thread)

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
			Toast.makeText(this, "�}�lø��", Toast.LENGTH_SHORT).show();
			setting.pen = 0;
			break;
		case R.id.item_eraser:
			Toast.makeText(this, "��ܤF�����", Toast.LENGTH_SHORT).show();
			setting.pen = 1;
			break;
		case R.id.item_mission_finish:
			Toast.makeText(this, "�s��", Toast.LENGTH_SHORT).show();
			mDialog = new ProgressDialog(this);

			mDialog.setCancelable(false);

			try {
				file = new File(Environment.getExternalStorageDirectory(),
						"Draw");
				// �Y�ؿ����s�b�h�إߥؿ�
				if (!file.mkdirs()) {
					Log.e("LOG_TAG", "�L�k�إߥؿ�");
				}
				long time = System.currentTimeMillis();
				file = new File(file, time / 1000 + ".JPEG");
				FileOutputStream out = new FileOutputStream(file);
				// �N Bitmap���Y�����w�榡���Ϥ��üg�J�ɮצ�y

				int A;
				int mBitmapWidth = bv.getSignatureBitmap().getWidth();
				int mBitmapHeight = bv.getSignatureBitmap().getHeight();
				int pixelColor;
				Bitmap newBitmap = Bitmap.createBitmap(bv.getSignatureBitmap(),
						0, 0, mBitmapWidth, mBitmapHeight);

				for (int i = 0; i < mBitmapWidth; i++) {
					for (int j = 0; j < mBitmapHeight; j++) {

						pixelColor = newBitmap.getPixel(i, j);
						A = Color.alpha(pixelColor);

						if (A == 0) {
							newBitmap.setPixel(i, j,
									Color.argb(255, 255, 255, 255));
						}
					}
				}

				newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
				Toast.makeText(this, "�s��", Toast.LENGTH_SHORT).show();
				new MyAsyncTaskforputdata().execute();
				SingleMediaScanner test = new SingleMediaScanner(this, file);
				// new Thread(new Runnable() {
				// public void run() {
				//
				//
				//
				// }
				// }).start();
				// SingleMediaScanner test = new SingleMediaScanner(
				// context, file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.item_mission_explain:
			Toast.makeText(this, "���Ȼ���", Toast.LENGTH_SHORT).show();
			Bitmap temp = null;
			setting.drawpicture = bv.getSignatureBitmap();

			Intent intent = new Intent();
			intent.setClass(DrawLessonTwo.this, Lessonone.class);
			startActivity(intent);

			finish();

			break;
		case R.id.item_clear:
			Toast.makeText(this, "�M��", Toast.LENGTH_SHORT).show();

			AlertDialog.Builder dialog = new AlertDialog.Builder(
					DrawLessonTwo.this);
			dialog.setTitle("����");
			dialog.setMessage("�T�w�n�M�Ŷ�");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			// dialog.setCancelable(false);
			dialog.setPositiveButton("�T�w",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (setting.upload != null) {
								setting.upload.recycle();
								setting.upload = null;
							}
							// ���UPositiveButton�n������
							Intent intent = new Intent();
							intent.setClass(DrawLessonTwo.this,
									DrawLessonTwo.class);
							startActivity(intent);
							finish();
						}
					});

			dialog.setNegativeButton("����",
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

	private class MyAsyncTaskforputdata extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			return postDatanotsearch();

		}

		protected void onPostExecute(String result) {
			// myDialog = ProgressDialog.show(DemoActivity.this, "", "wait",
			// true);
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postDatanotsearch() {
			uploadFile(file.getPath());
			p np;
			setting.path = "";
			for (int i = 0; i < (bv.s); i++) {
				np = bv.paths.get(i);
				setting.path = setting.path + "p" + np.pen + ";";
				if (np.pen == 0) {
					setting.path = setting.path + "c" + np.a + "," + np.r + ","
							+ np.g + "," + np.b + ";w" + np.w + ";";
					setting.path = setting.path + "l" + np.pathlog + "";

				} else {
					setting.path = setting.path + "w" + np.w + ";";
					setting.path = setting.path + "l" + np.pathlog + "";
				}
				setting.path = setting.path + "!";
			}
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					"http.protocol.content-charset", "UTF-8");
			HttpPost httppost = new HttpPost(
					"http://imagenetapi.appspot.com/coursedb");
			setting.path = setting.path + setting.path;
			setting.path = setting.path + setting.path;
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("count", (Math
						.floor(setting.path.length() / 500) + 1) + ""));
				int s = (int) (Math.floor(setting.path.length() / 500) + 1);

				nameValuePairs.add(new BasicNameValuePair("width",
						setting.screenweight + ""));
				nameValuePairs.add(new BasicNameValuePair("deviceid",
						android.os.Build.MODEL));
				nameValuePairs.add(new BasicNameValuePair("hight",
						setting.screenheit + ""));
				nameValuePairs.add(new BasicNameValuePair("pin", setting.pin
						+ ""));
				// Log.v("s", s + "");
				if (s > 1) {
					int i = 0;
					for (i = 0; i < (s - 1); i++) {
						nameValuePairs
								.add(new BasicNameValuePair("path" + i,
										setting.path.substring(i * 500,
												(i + 1) * 500)));
					}
					nameValuePairs.add(new BasicNameValuePair("path" + i,
							setting.path.substring(i * 500)));

				} else {
					nameValuePairs.add(new BasicNameValuePair("path" + 0,
							setting.path));
				}
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						"UTF-8"));
				// Execute HTTP Post Request
				HttpResponse httpResponse = httpclient.execute(httppost);

				String content = "";

				content += EntityUtils.toString(httpResponse.getEntity());
				httpclient.getConnectionManager().shutdown();
				return content;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return null;
		}
	}

	public int uploadFile(String sourceFileUri) {

		Log.v("asd1", sourceFileUri);
		String fileName = sourceFileUri;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(
						"http://gn00254192.hostei.com/UploadToServer.php");

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();
				conn.disconnect();
			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

			}

			return 0;
		} // End else block
	}

}