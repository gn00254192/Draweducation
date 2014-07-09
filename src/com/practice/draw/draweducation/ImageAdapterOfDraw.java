package com.practice.draw.draweducation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapterOfDraw extends BaseAdapter {
	private Context mContext;
	private Integer width;
	private Integer height;
	private Integer[] mImageIds;

	public ImageAdapterOfDraw(Context c) {
		mContext = c;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		// �]�w�Ϥ��ӷ�
		imageView.setImageResource(mImageIds[position]);
		// �]�w�Ϥ����e�B��
		imageView.setLayoutParams(new Gallery.LayoutParams(width, height));
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imageView;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer[] getmImageIds() {
		return mImageIds;
	}

	public void setmImageIds(Integer[] mImageIds) {
		this.mImageIds = mImageIds;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
}