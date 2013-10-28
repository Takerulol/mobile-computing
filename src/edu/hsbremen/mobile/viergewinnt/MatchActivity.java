package edu.hsbremen.mobile.viergewinnt;

import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.Token;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class MatchActivity extends Activity {

	ImageView[][] tokenList = null;
	GameLogic logic;
	private boolean init = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match);
		// Show the Up button in the action bar.
		setupActionBar();
		
		if(init ) init();
	}

	private void init() {
		init = false;
		logic = new GameLogicImpl();
		logic.startGame();
		tokenList = new ImageView[GameLogicImpl.WIDTH][GameLogicImpl.HEIGHT];
		generateField();
		updateField();
	}

	private void updateField() {
		Token[][] tokens = logic.getGamefield();
		for(int x = 0; x < tokens.length; x++) {
			for(int y = 0; y < tokens[0].length; y++) {
				setImage(x,y,tokens[x][y]);
			}
		}
		
	}

	private void setImage(int x, int y, Token token) {
		
		switch(token) {
		case Blue:
			
			break;
		case Red:
			break;
		case None:
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void generateField() {
		TableLayout layout = new TableLayout(this);
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 
				ViewGroup.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		layout.setStretchAllColumns(true);
		layout.setBackgroundColor(getResources().getColor(R.color.black_overlay));
		
		for(int y = 0; y < GameLogicImpl.HEIGHT; y++) {
			
		}
	}
	
	public void addRow(TableLayout layout, int rowNum) {
		for(int x = 0; x < GameLogicImpl.WIDTH; x++) {
			
		}
	}
	
	public void buttonClick(View view) {
		
	}
}