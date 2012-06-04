package com.tutorial.listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleBaseAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] names = new String[] { "Joao", "Ana", "Pedro", "Jorge", "Joana", "Claudia", "Alexandra", "Tiago", "Luis", "Ricardo" };

	public SimpleBaseAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return names[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
			viewHolder.text = (TextView) convertView.findViewById(R.id.list_item_text);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.list_item_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		updateView(viewHolder, position);

		return convertView;
	}

	private void updateView(ViewHolder viewHolder, int position) {
		viewHolder.image.setImageResource(R.drawable.ic_launcher);
		viewHolder.text.setText(names[position]);
	}

	private class ViewHolder {
		public ImageView image;
		public TextView text;
	}

}
