package com.gnox.memorygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreBoardAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public ScoreBoardAdapter(Context context, String[] values) {
		super(context, R.layout.scoreboard_row, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.scoreboard_row, parent, false);
		TextView scoreName = (TextView) rowView.findViewById(R.id.scoreName);
		TextView scoreTime = (TextView) rowView.findViewById(R.id.scoreTime);
		scoreName.setText(values[position]);
		// Change the icon for Windows and iPhone
		String s = values[position];
		if (s.startsWith("Windows7") || s.startsWith("iPhone") || s.startsWith("Solaris")) {
			scoreTime.setText("OK!");
		} else {
			scoreTime.setText("NOPE!");
		}

		return rowView;
	}
}
