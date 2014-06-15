package com.gnox.memorygame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

public class ScoreBoard extends ListActivity {

	public static final int LIST_ITEM_TYPE_1 = 1;
	public static final int LIST_ITEM_TYPE_2 = 2;
	public static final int LIST_ITEM_TYPE_COUNT = 2;

	private Integer newTime;
	private String name = "Unknown Player";
	private String SCOREFILE = "scores";

	private EditText nameInputEditText;

	ArrayAdapter<String> adapter;
	ArrayList<String> list;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_score_board);
		Intent i = getIntent();
		Button deleteButton = (Button) findViewById(R.id.deleteButton);

		if (i.hasExtra(Game.TIME_EXTRA)) {
			newTime = i.getIntExtra(Game.TIME_EXTRA, 0);

			Button button = (Button) findViewById(R.id.addBtn);
			nameInputEditText = (EditText) findViewById(R.id.textField);
			button.setVisibility(View.VISIBLE);
			button.setEnabled(true);
			nameInputEditText.setVisibility(View.VISIBLE);
			nameInputEditText.setEnabled(true);

			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					nameInputEditText.setVisibility(View.INVISIBLE);
					nameInputEditText.setEnabled(false);
					v.setVisibility(View.INVISIBLE);
					v.setEnabled(false);
					if (nameInputEditText.getText().toString().length() > 0)
						name = nameInputEditText.getText().toString();

					saveScoreToFile();
					restart(true);
				}
			});

			deleteButton.setVisibility(View.INVISIBLE);
			deleteButton.setEnabled(false);
		} else {
			deleteButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					deleteFile(SCOREFILE);
					restart(false);
				}
			});
		}

		Button newGameButton = (Button) findViewById(R.id.scoreBoardNewGame);
		newGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveScores(true);
				finish();
				startActivity(new Intent(ScoreBoard.this, Game.class));
			}
		});

		ScoreBoardAdapter adapter = new ScoreBoardAdapter(this, getModel());
		setListAdapter(adapter);
	}

	private List<ScoreHolder> getModel() {
		String scoreboard = loadScoreFromFile();
		List<ScoreHolder> list = new ArrayList<ScoreHolder>();

		if (scoreboard != "") {

			String[] lines = scoreboard.split("\\r?\\n");

			for (String s : lines) {
				String[] parts = s.split("-");
				if (parts.length > 1)
					list.add(get(parts[0], parts[1]));
			}

			Collections.sort(list, new Comparator<ScoreHolder>() {

				@Override
				public int compare(ScoreHolder lhs, ScoreHolder rhs) {
					return lhs.getTime().compareTo(rhs.getTime());
				}
			});

		}

		return list;
	}

	private ScoreHolder get(String name, String time) {
		return new ScoreHolder(name, time);
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

	private void restart(boolean saved) {
		Intent i = getIntent();
		if (i.hasExtra(Game.TIME_EXTRA))
			i.removeExtra(Game.TIME_EXTRA);
		saveScores(saved);

		finish();
		startActivity(i);
	}

	private final Button createAddButton() {
		Button button = new Button(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (nameInputEditText.getText().toString() != "")
					name = nameInputEditText.getText().toString();

				saveScoreToFile();
				restart(true);
			}
		});
		return button;
	}

	private final EditText createEditText() {
		EditText editText = new EditText(this);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		editText.setLayoutParams(params);
		editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		return editText;
	}

	private void saveScores(boolean save) {
		if (!save && newTime != null)
			saveScoreToFile();
	}
}
