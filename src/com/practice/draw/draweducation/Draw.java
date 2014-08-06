package com.practice.draw.draweducation;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.practice.draw.draweducation.setting;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Draw extends Activity {
	BubbleSurfaceView bv;
	ProgressDialog mDialog;
	private Handler mUI_Handler = new Handler();
	File file;
	// 宣告特約工人的經紀人

	private Handler mThreadHandler;

	// 宣告特約工人

	private HandlerThread mThread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);

		DisplayMetrics metrics = new DisplayMetrics(); // 擷取螢幕大小
		getWindowManager().getDefaultDisplay().getMetrics(metrics);// 擷取螢幕大小
		setting.screenweight = metrics.widthPixels;
		setting.screenheit = metrics.heightPixels;
		setting.draw = this;
		bv = (BubbleSurfaceView) findViewById(R.id.surfaceView);
		bv.setSignatureBitmap(this, metrics.widthPixels, metrics.heightPixels);
		GalleryviewofDraw gv = new GalleryviewofDraw(this, bv,
				metrics.heightPixels, metrics.widthPixels);

	}

	// private Bitmap dijkstra(Bitmap image) {
	// // TODO Auto-generated method stub
	// // TODO Auto-generated method stub
	// mThread = new HandlerThread("name");
	//
	// // 讓Worker待命，等待其工作 (開啟Thread)
	//
	// mThread.start();
	//
	// // 找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
	//
	// mThreadHandler = new Handler(mThread.getLooper());
	// mThreadHandler.post(r1);
	// setting.upload = image;
	// return image;
	//
	// }

	// private Runnable r1 = new Runnable() {
	//
	// public void run() {
	// Long nowTime = System.currentTimeMillis();
	// LOG.OUT(111111);
	// Bitmap image = setting.upload;
	//
	// short r;
	// short g;
	// short b;
	// short x, y, h, w;
	// int pix, i, j, xx, yy, k;
	// float c;
	// h = (short) image.getHeight();
	// w = (short) image.getWidth();
	// bv.gray = new float[w][h];
	// bv.Cost = new float[w][h];
	// float[][] IX = new float[w][h];
	// float[][] IY = new float[w][h];
	// image = image.copy(Bitmap.Config.ARGB_8888, true);
	// for (y = 0; y < h; y++) {
	// for (x = 0; x < w; x++) {
	// pix = image.getPixel(x, y);
	// r = (short) ((pix >> 16) & 0xff); // bitwise
	// g = (short) ((pix >> 8) & 0xff);
	// b = (short) (pix & 0xff);
	// bv.gray[x][y] = (short) FloatMath.floor((r + g + b) / 3);
	//
	// }
	// }
	//
	// if ((float) h / w > (float) setting.screenheit
	// / setting.screenweight) {
	// bv.rh = (short) setting.screenheit;
	// bv.rw = (short) ((float) bv.rh / h * w);
	// bv.rs = (float) h / bv.rh;
	// bv.rx = (setting.screenweight - bv.rw) / 2;
	// bv.ry = 0;
	// } else {
	// bv.rw = (short) setting.screenweight;
	// bv.rh = (short) ((float) bv.rw / w * h);
	// bv.rs = (float) w / bv.rw;
	// bv.ry = (setting.screenheit - bv.rh) / 2;
	// bv.rx = 0;
	// }
	//
	// image = image.copy(Bitmap.Config.ARGB_8888, true);
	// for (y = 0; y < h; y++) {
	// for (x = 0; x < w; x++) {
	// pix = image.getPixel(x, y);
	// r = (short) ((pix >> 16) & 0xff); // bitwise
	// g = (short) ((pix >> 8) & 0xff);
	// b = (short) (pix & 0xff);
	// bv.gray[x][y] = (short) FloatMath.floor((r + g + b) / 3);
	//
	// }
	// }
	//
	// /* FG */
	//
	// float[][] fIY = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
	// float[][] fIX = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
	// float gmin = 1024, gmax = 0;
	//
	// IX = filter.filter2(bv.gray, fIX, w, h);
	// IY = filter.filter2(bv.gray, fIY, w, h);
	// for (y = 0; y < h; y++) {
	// for (x = 0; x < w; x++) {
	// bv.Cost[x][y] = FloatMath.sqrt((IX[x][y] * IX[x][y])
	// + (IY[x][y] * IY[x][y]));
	// IX[x][y] = (bv.Cost[x][y] != 0) ? IX[x][y] / bv.Cost[x][y]
	// : 0;
	// IY[x][y] = (bv.Cost[x][y] != 0) ? IY[x][y] / bv.Cost[x][y]
	// : 0;
	// if (bv.Cost[x][y] < gmin) {
	// gmin = bv.Cost[x][y];
	// }
	// if (bv.Cost[x][y] > gmax) {
	// gmax = bv.Cost[x][y];
	// }
	// }
	// }
	//
	// gmax = gmax - gmin;
	// for (y = 0; y < h; y++) {
	// for (x = 0; x < w; x++) {
	// // Log.v("bv.Cost", bv.Cost[x][y]+"");
	// bv.Cost[x][y] = (1 - (float) ((bv.Cost[x][y] - gmin) / gmax))
	// * setting.WG;
	// // Log.v("Cost1", Cost[x][y]+"");
	// }
	//
	// }
	//
	// /** FZ **/
	// float[][] log = {
	// { (float) 0.0238691276314142, (float) 0.0459736568407521,
	// (float) 0.0499314897271405,
	// (float) 0.0459736568407521,
	// (float) 0.0238691276314142 },
	// { (float) 0.0459736568407521, (float) 0.00605502716771119,
	// -(float) 0.0922653041811346,
	// (float) 0.00605502716771119,
	// (float) 0.0459736568407521 },
	// { (float) 0.0499314897271405, -(float) 0.0922653041811346,
	// -(float) 0.318150616106542,
	// -(float) 0.0922653041811346,
	// (float) 0.0499314897271405 },
	// { (float) 0.0459736568407521, (float) 0.00605502716771119,
	// -(float) 0.0922653041811346,
	// (float) 0.00605502716771119,
	// (float) 0.0459736568407521 },
	// { (float) 0.0238691276314142, (float) 0.0459736568407521,
	// (float) 0.0499314897271405,
	// (float) 0.0459736568407521,
	// (float) 0.0238691276314142 } };
	//
	// bv.gray = filter.filter2(bv.gray, log, w, h);
	//
	// for (y = 1; y < h - 1; y++) {
	// for (x = 1; x < w - 1; x++) {
	// if ((((bv.gray[x][y] < 0) && ((bv.gray[x + 1][y] > 0)
	// || (bv.gray[x - 1][y] > 0)
	// || (bv.gray[x][y + 1] > 0) || (bv.gray[x][y - 1] > 0))) ||
	// ((bv.gray[x][y] == 0) && (((bv.gray[x + 1][y] * bv.gray[x - 1][y]) < 0)
	// || ((bv.gray[x][y + 1] * bv.gray[x][y - 1]) < 0)
	// || ((bv.gray[x + 1][y + 1] * bv.gray[x - 1][y - 1]) < 0) || ((bv.gray[x +
	// 1][y - 1] * bv.gray[x - 1][y + 1]) < 0))))
	// && (bv.Cost[x][y] < setting.th)) {
	// // image.setPixel(x, y, Color.argb(255, 0, 255, 255));
	// } else
	// bv.Cost[x][y] = bv.Cost[x][y] + setting.WZ + setting.WL;
	//
	// }
	// }
	//
	// Long spentTime = System.currentTimeMillis();
	// LOG.OUT((spentTime - nowTime) / 1000);
	// setting.done = true;
	// }
	//
	// };

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if (resultCode == RESULT_CANCELED) {
		// // action cancelled
		// }
		// if (resultCode == RESULT_OK) {
		// mDialog = new ProgressDialog(Draw.this);
		// mDialog.setMessage("Loading...");
		// mDialog.setCancelable(false);
		// mDialog.show();
		// ImageView im = (ImageView) findViewById(R.id.ImageView1);
		// Uri selectedimg = data.getData();
		//
		// try {
		// Long nowTime = System.currentTimeMillis();
		// im.setImageBitmap(dijkstra(decodeUri(selectedimg)));
		// Long spentTime = System.currentTimeMillis();
		// LOG.OUT((spentTime - nowTime) / 1000);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// mDialog.dismiss();
		//
		// }
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
							setting.imagenumber = -1;
							//mDialog.dismiss();						
							finish();
						}
					});
			dialog.setNeutralButton("確定並存檔",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							mDialog = new ProgressDialog(Draw.this);
							mDialog.setMessage("存檔中...");
							mDialog.setCancelable(false);
							mDialog.show();
							// TODO Auto-generated method stub
							
							try {
								file = new File(
										Environment.getExternalStorageDirectory(),
										"Draw");
								// 若目錄不存在則建立目錄
								if (!file.mkdirs()) {
									Log.e("LOG_TAG", "無法建立目錄");
								}
								long time = System.currentTimeMillis();
								file = new File(file, time / 1000 + ".JPEG");
								FileOutputStream out = new FileOutputStream(file);
								// 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
																
								int A;
								int mBitmapWidth =bv.getSignatureBitmap().getWidth();
								int mBitmapHeight =bv.getSignatureBitmap().getHeight();
								int pixelColor;
								Bitmap newBitmap = Bitmap.createBitmap(bv.getSignatureBitmap(), 0, 0, mBitmapWidth, mBitmapHeight);
								
								
								for (int i = 0; i <mBitmapWidth; i++) {   
						            for (int j = 0; j <mBitmapHeight; j++) {  
						            
						            	pixelColor = newBitmap.getPixel(i, j);
						            	A=Color.alpha(pixelColor);
						            	
						            	if(A==0 )
						            	{
						            		newBitmap.setPixel(i, j, Color.argb(255, 255, 255,255));
						            	}
						            }
						        }
							
								newBitmap.compress(
										Bitmap.CompressFormat.JPEG, 90, out);
								setting.upload = newBitmap;
								out.flush();
								out.close();
								SingleMediaScanner test = new SingleMediaScanner(
										Draw.this, file);	
								mDialog.dismiss();
								Draw.this.finish();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							new MyAsyncTaskforputdata().execute();
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

		// if (mThreadHandler != null) {
		//
		// mThreadHandler.removeCallbacks(r1);
		//
		// }

		// 解聘工人 (關閉Thread)

		// if (mThread != null) {
		//
		// mThread.quit();
		//
		// }

	}

	@Override
	public void onResume() {
		try {
			if (setting.imagenumber > -1 && setting.controlpictureload==1) {
				mDialog = new ProgressDialog(Draw.this);
				mDialog.setMessage("Loading...");
				mDialog.setCancelable(false);
				mDialog.show();
				PostTask2 posttask;
				posttask = new PostTask2(
						setting.imagelist[setting.imagenumber][1]);

				posttask.execute();
				setting.pin = bv.s;
			}
		} catch (Exception e) {

		}

		super.onResume();
	}

	public class PostTask2 extends AsyncTask<Void, String, String> {
		public String surl;
		public Bitmap bmp;

		public PostTask2(String string) {
			surl = string;
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String doInBackground(Void... params) {

			bmp = getBitmapFromURL(setting.imagelist[setting.imagenumber][1]);

			return "surl";

		}

		protected void onPostExecute(String reString) {
			ImageView imv = (ImageView) ((Activity) setting.draw)
					.findViewById(R.id.ImageView1);
			imv.setImageBitmap(bmp);
			mDialog.dismiss();

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
				return getResizedBitmap(myBitmap, 320, 320);
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

	private class MyAsyncTaskforputdata extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			if (setting.stat == 1) {
				LOG.OUT(12312323);

				return "";
			} else
				return postData();
		}

		protected void onPostExecute(String result) {
			// myDialog = ProgressDialog.show(DemoActivity.this, "", "wait",
			// true);
			setting.imagenumber = -1;
			mDialog.dismiss();

			Draw.this.finish();

		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData() {
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
					"http://mjimagenetapi.appspot.com/pathdb");
			setting.path = setting.path + setting.path;
			setting.path = setting.path + setting.path;
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("count", (Math
						.floor(setting.path.length() / 500) + 1) + ""));
				int s = (int) (Math.floor(setting.path.length() / 500) + 1);
				nameValuePairs.add(new BasicNameValuePair("node",
						setting.imagelist[setting.imagenumber][0].substring(0,
								8)));
				if(setting.imagelist[setting.imagenumber][0].length()>9)
					nameValuePairs.add(new BasicNameValuePair("nodenumber",
							setting.imagelist[setting.imagenumber][0].substring(9,
									setting.imagelist[setting.imagenumber][0]
											.length() - 1)));
					else
						nameValuePairs.add(new BasicNameValuePair("nodenumber",
								setting.imagelist[setting.imagenumber][0]));
				nameValuePairs.add(new BasicNameValuePair("width",
						setting.screenweight + ""));
				nameValuePairs.add(new BasicNameValuePair("deviceid",
						android.os.Build.MODEL));
				nameValuePairs.add(new BasicNameValuePair("hight",
						setting.screenheit + ""));
				nameValuePairs.add(new BasicNameValuePair("search",
						setting.search));
				nameValuePairs.add(new BasicNameValuePair("url",
						"http://summer3c.host56.com/upload/" + file.getName()));
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
			//
			// dialog.dismiss();
			//
			// Log.e("uploadFile", "Source File not exist :" + imagepath);

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(
						"http://summer3c.host56.com/UploadToServer.php");

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

			} catch (MalformedURLException ex) {

				// dialog.dismiss();
				ex.printStackTrace();

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				// dialog.dismiss();
				e.printStackTrace();

			}

			return 0;


		} // End else block
	}
}
