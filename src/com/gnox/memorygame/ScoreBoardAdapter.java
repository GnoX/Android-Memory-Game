package com.gnox.memorygame;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreBoardAdapter extends ArrayAdapter<Model> {

	private final List<Model> list;
	private final Activity context;

	public ScoreBoardAdapter(Activity context, List<Model> list) {
		super(context, R.layout.scoreboard_row, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView text;
		protected TextView time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.scoreboard_row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.scoreName);
			viewHolder.time = (TextView) view.findViewById(R.id.scoreTime);

			view.setTag(viewHolder);
			viewHolder.time.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName());
		holder.time.setText(list.get(position).getTime());
		return view;
	}

}
