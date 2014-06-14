package com.gnox.memorygame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class ScoreBoard extends ListActivity {

	public static final int LIST_ITEM_TYPE_1 = 1;
	public static final int LIST_ITEM_TYPE_2 = 2;
	public static final int LIST_ITEM_TYPE_COUNT = 2;

	private int newTime = 0;
	private String name = "Unknown Player";
	private String SCOREFILE = "scores";

	ArrayAdapter<String> adapter;
	ArrayList<String> list;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_score_board);
		Intent i = getIntent();
		if (i.hasExtra(Game.TIME_EXTRA)) {
			newTime = i.getIntExtra(Game.TIME_EXTRA, 0);
			final Button button = (Button) findViewById(R.id.addBtn);
			final EditText editText = (EditText) findViewById(R.id.textField);
			button.setVisibility(View.VISIBLE);
			button.setEnabled(true);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					editText.setEnabled(false);
					editText.setVisibility(View.INVISIBLE);
					button.setEnabled(false);
					button.setVisibility(View.INVISIBLE);

					if (editText.getText().toString() != "") {
						name = editText.getText().toString();
					}
					saveScoreToFile();
				}
			});
			editText.setVisibility(View.VISIBLE);
			editText.setEnabled(true);
		}

		Button deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteFile(SCOREFILE);
				Intent i = getIntent();
				finish();
				startActivity(i);
			}
		});

		ScoreBoardAdapter adapter = new ScoreBoardAdapter(this, getModel());
		setListAdapter(adapter);
	}

	private List<ScoreHolder> getModel() {
		List<ScoreHolder> list = new ArrayList<ScoreHolder>();
		String scoreboard = loadScoreFromFile();

		String[] lines = scoreboard.split("\\r?\\n");

		for (String s : lines) {
			String[] parts = s.split("-");
			list.add(get(parts[0], parts[1]));
		}

		return list;
	}

	private ScoreHolder get(String s, String st) {
		return new ScoreHolder(s, st);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void saveScoreToFile() {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(SCOREFILE,
					Context.MODE_APPEND));
			BufferedWriter bw = new BufferedWriter(outputStreamWriter);
			bw.write(name + "-" + newTime);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			Log.e("GNOX", "File write failed: " + e.toString());
		}
	}

	private String loadScoreFromFile() {
		String ret = "";

		try {
			InputStream inputStream = openFileInput(SCOREFILE);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString + System.getProperty("line.separator"));
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("GNOX", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("GNOX", "Can not read file: " + e.toString());
		}

		return ret;

	}

	private void wipeScoreboard() {
		deleteFile(SCOREFILE);
	}

}
