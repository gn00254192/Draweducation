package com.practice.draw.draweducation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.practice.draw.draweducation.R;


import com.practice.draw.draweducation.Search;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryviewofDraw {
	private final int REQUEST_CODE_INSERT_IMAGE = 112;
	public Integer[] ImageIds = { android.R.drawable.ic_menu_edit,
			R.drawable.eraser, R.drawable.penlarge, R.drawable.pensmall,
			R.drawable.magic, R.drawable.color, R.drawable.undo,
			R.drawable.redo, R.drawable.load, R.drawable.search,
			R.drawable.zoonin, R.drawable.zoonout, R.drawable.move,
			R.drawable.zoon1, R.drawable.clear, R.drawable.save,
			R.drawable.share, android.R.drawable.ic_menu_camera, R.drawable.search };
	Handler mHandler;
	public Context context;
	ProgressDialog mDialog;
	boolean change = true, ini = true;
	float ix;
	int r, onnum;
	Gallery gallery;
	ImageView iv;
	ImageView im;
	BubbleSurfaceView bv;
	File file;

	public GalleryviewofDraw(Context c, BubbleSurfaceView b, int h, int w) {
		bv = b;
		r = (h) / 10;

		context = c;
		gallery = (Gallery) ((Activity) context).findViewById(R.id.gallery);
		im = (ImageView) ((Activity) context).findViewById(R.id.imageView1);
		ImageAdapterOfDraw imageAdapter = new ImageAdapterOfDraw(context);
		imageAdapter.setmImageIds(ImageIds);
		imageAdapter.setHeight(100);
		// 圖片寬度
		imageAdapter.setWidth(100);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					setting.pen = 0;
					Toast.makeText(context, "筆", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					setting.pen = 1;
					Toast.makeText(context, "橡皮擦", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(context, "筆觸放大", Toast.LENGTH_SHORT).show();
					setting.penweith += 5;
					break;
				case 3:
					Toast.makeText(context, "筆觸縮小", Toast.LENGTH_SHORT).show();
					Toast.makeText(context, "筆觸縮小", Toast.LENGTH_SHORT).show();
					if (setting.penweith - 5 < 1)
						setting.penweith = 1;
					else
						setting.penweith -= 5;
					break;
				case 4:
					if (setting.done) {
						setting.pen = 3;
						Toast.makeText(context, "魔術棒", Toast.LENGTH_SHORT)
								.show();
					} else
						Toast.makeText(context, "魔術棒尚未開放", Toast.LENGTH_SHORT)
								.show();
					break;
				case 5:
					Toast.makeText(context, "選擇顏色", Toast.LENGTH_SHORT).show();
					setting.pen = 0;
					new ColorPickerDialog(context, bv, bv.mPaint.getColor())
							.show();
					break;
				case 6:
					Toast.makeText(context, "復原", Toast.LENGTH_SHORT).show();
					bv.undo();
					break;
				case 7:
					Toast.makeText(context, "還原", Toast.LENGTH_SHORT).show();
					bv.redo();
					break;
				case 8:
					Toast.makeText(context, "選擇圖片", Toast.LENGTH_SHORT).show();
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					((Activity) context).startActivityForResult(
							photoPickerIntent, 1);

					bv.zoom1();
					break;
				case 9:
					Toast.makeText(context, "本功能尚未開放", Toast.LENGTH_SHORT)
							.show();
					// Intent intent = new Intent(context, Alist.class);
					// ((Activity) context).startActivity(intent);
					break;
				case 10:

					Toast.makeText(context, "放大", Toast.LENGTH_SHORT).show();
					bv.zoomout();
					break;
				case 11:
					Toast.makeText(context, "縮小", Toast.LENGTH_SHORT).show();
					bv.zoomin();
					break;
				case 12:

					Toast.makeText(context, "移動", Toast.LENGTH_SHORT).show();
					setting.pen = 2;
					break;
				case 13:
					Toast.makeText(context, "置中", Toast.LENGTH_SHORT).show();
					bv.zoom1();
					break;
				case 14:
					Toast.makeText(context, "清空", Toast.LENGTH_SHORT).show();

					AlertDialog.Builder dialog = new AlertDialog.Builder(
							context);
					dialog.setTitle("提醒");
					dialog.setMessage("確定要清空嗎");
					dialog.setIcon(android.R.drawable.ic_dialog_alert);
					// dialog.setCancelable(false);
					dialog.setPositiveButton("確定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if (setting.upload != null) {
										setting.upload.recycle();
										setting.upload = null;
									}
									// 按下PositiveButton要做的事
									Intent intent = new Intent(context,
											Draw.class);
									context.startActivity(intent);
									((Activity) context).finish();

								}
							});

					dialog.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

					dialog.show();

					break;
				case 15:
					Toast.makeText(context, "存檔", Toast.LENGTH_SHORT).show();
					mDialog = new ProgressDialog(context);
					
					mDialog.setCancelable(false);
					

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
						out.flush();
						out.close();
						Toast.makeText(context, "存檔", Toast.LENGTH_SHORT).show();
						SingleMediaScanner test = new SingleMediaScanner(
								context, file);
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
				case 16:
					Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
					FileOutputStream fop;
					File file = null;
					try {
						file = new File(
								Environment.getExternalStorageDirectory(),
								"MJCamera");
						// 若目錄不存在則建立目錄
						if (!file.mkdirs()) {
							Log.e("LOG_TAG", "無法建立目錄");
						}
						long time = System.currentTimeMillis();
						file = new File(file, time / 1000 + ".jpg");
						fop = new FileOutputStream(file);
						// 實例化FileOutputStream，參數是生成路徑
						bv.getSignatureBitmap().compress(
								Bitmap.CompressFormat.JPEG, 90, fop); // 壓缩bitmap寫進outputStream
																		// 參數：輸出格式
																		// 輸出質量
																		// 目標OutputStream
						// 格式可以為jpg,png,jpg不能存儲透明
						fop.close();
						// 關閉流
					} catch (FileNotFoundException e) {

						e.printStackTrace();
						System.out.println("FileNotFoundException");

					} catch (IOException e) {

						e.printStackTrace();
						System.out.println("IOException");
					}

					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("image/PNG");
					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
					context.startActivity(Intent.createChooser(share,
							"Share Image"));
					break;
				case 17:
					Intent intent_camera = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					((Activity) context).startActivityForResult(intent_camera,
							2);
					break;
				case 18:
					Toast.makeText(context, "搜尋", Toast.LENGTH_SHORT).show();	
					Intent intent = new Intent(context, Showmore.class);
					((Activity) context).startActivity(intent);
					setting.controlpictureload=1;
					((Activity) context).finish();
					break;
				case 100:
					new MyAsyncTaskforputdata().execute();
					break;
				default:

					// 回傳的圖片可透過 it.getData() 取得圖片之 Uri
					break;

				}
				onnum = msg.what;
				super.handleMessage(msg);
			}

		};
		gallery.setAdapter(imageAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// Log.d("LOG","Item selected at position "+position +" in ");
				Message msg = new Message();
				msg.what = position;
				mHandler.sendMessage(msg);
			}
		});
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private class MyAsyncTaskforputdata extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			return postData();
		}

		protected void onPostExecute(String result) {
			// myDialog = ProgressDialog.show(DemoActivity.this, "", "wait",
			// true);
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData() {
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
			HttpPost httppost = new HttpPost(
					"http://summerimagenetapi.appspot.com/imagenetapi?act=5");
			setting.path = setting.path + setting.path;
			setting.path = setting.path + setting.path;
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("count", (Math
						.floor(setting.path.length() / 500) + 1) + ""));
				int s = (int) (Math.floor(setting.path.length() / 500) + 1);
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
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

}
