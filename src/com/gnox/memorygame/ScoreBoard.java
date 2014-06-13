package com.gnox.memorygame;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ScoreBoard extends ListActivity {

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// create an array of Strings, that will be put to our ListActivity
		ArrayAdapter<Model> adapter = new ScoreBoardAdapter(this, getModel());
		setListAdapter(adapter);
	}

	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("Linux", "125"));
		list.add(get("Windows7", "13"));
		list.add(get("Suse", "12"));
		return list;
	}

	private Model get(String s, String st) {
		return new Model(s, st);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
