package com.gnox.memorygame;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainScoreBoardAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<String> data;

	static class ViewHolder {
		protected TextView text;
		protected TextView time;
	}

	public MainScoreBoardAdapter(Context context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case ScoreBoard.LIST_ITEM_TYPE_1:
				convertView = inflater.inflate(R.layout.scoreboard_row, null);
				holder.text = (TextView) convertView.findViewById(R.id.scoreName);
				break;
			case ScoreBoard.LIST_ITEM_TYPE_2:
				convertView = inflater.inflate(R.layout.scoreboard_edit_row, null);
				holder.text = (TextView) convertView.findViewById(R.id.scoreTime);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(data.get(position));
		return convertView;
	}

}
