package com.gnox.memorygame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ScoreBoard extends ListActivity {

	private int newTime = 0;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		saveScoreToFile();
		Intent i = getIntent();
		if (i.hasExtra(Game.TIME_EXTRA))
			newTime = i.getIntExtra(Game.TIME_EXTRA, 0);
		// create an array of Strings, that will be put to our ListActivity
		ArrayAdapter<ScoreHolder> adapter = new ScoreBoardAdapter(this, getModel());
		setListAdapter(adapter);
	}

	private List<ScoreHolder> getModel() {
		List<ScoreHolder> list = new ArrayList<ScoreHolder>();
		list.add(get("Linux", "125"));
		list.add(get("Windows7", "13"));
		list.add(get("Suse", "12"));
		return list;
	}

	private ScoreHolder get(String s, String st) {
		return new ScoreHolder(s, st);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	private void saveScoreToFile() {
		String FILENAME = "hello_file";
		String string = "hello world!";

		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		deleteFile(FILENAME);

	}

}
