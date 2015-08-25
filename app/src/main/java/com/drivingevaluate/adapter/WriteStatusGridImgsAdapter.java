package com.drivingevaluate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.drivingevaluate.R;
import com.drivingevaluate.util.ImageUtils.ImageItem;

import java.util.List;


public class WriteStatusGridImgsAdapter extends BaseAdapter {

	private Context context;
	private List<ImageItem> datas;
	private GridView gv;

	public WriteStatusGridImgsAdapter(Context context, List<ImageItem> datas, GridView gv) {
		this.context = context;
		this.datas = datas;
		this.gv = gv;
	}

	@Override
	public int getCount() {
		return datas.size() > 0 ? datas.size() + 1 : 0;
	}

	@Override
	public ImageItem getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_gv_img, null);
			holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
			holder.iv_delete_image = (ImageView) convertView.findViewById(R.id.iv_delete_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		int horizontalSpacing = gv.getHorizontalSpacing();
		int width = (gv.getWidth() - horizontalSpacing * 3
				- gv.getPaddingLeft() - gv.getPaddingRight()) / 4;
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
		holder.iv_image.setLayoutParams(params);
		
		if(position < getCount() - 1) {
			// set data
			ImageItem item = getItem(position);
			holder.iv_image.setImageBitmap(item.getBitmap());
			holder.iv_delete_image.setVisibility(View.VISIBLE);
			holder.iv_delete_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					datas.remove(position);
					notifyDataSetChanged();
				}
			});
		} else {
			holder.iv_image.setImageResource(R.mipmap.ic_addpic);
			holder.iv_delete_image.setVisibility(View.GONE);
		}

		return convertView;
	}

	public static class ViewHolder {
		public ImageView iv_image;
		public ImageView iv_delete_image;
	}

}
