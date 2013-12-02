package edu.hsbremen.mobile.viergewinnt;

import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.Token;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MatchFragment extends Fragment implements OnClickListener, GameLogic.Listener {

	public interface Listener {
		void onWinnerFound(String msg);
	}

	private ImageView[][] tokenList = null;
	private GameLogic logic;
	

	private Button[] buttons = null;
	private Listener listener;
	
	public MatchFragment()
	{
		logic = new GameLogicImpl();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_match, container,false);
		return v;
	}
	
	@Override
	public void onClick(View v) {
		//	handle possible button clicks, that are not generated
	}
	
	@Override
	public void onStart() {
		super.onStart();
		init();
	}
	
	private void init() {
		logic.startGame();
		tokenList = new ImageView[GameLogicImpl.WIDTH][GameLogicImpl.HEIGHT];
		buttons = new Button[GameLogicImpl.WIDTH];
		generateField();
		update();
	}

	protected GameLogic provideGameLogic() {
		return new GameLogicImpl();
	}

	private void update() {
		switch (logic.getWinner()) {
		case Blue:
			this.listener.onWinnerFound("Blue player won.");
			break;
		case Red:
			this.listener.onWinnerFound("Red player won.");
			break;
		default:
			updateField();
		}
		
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
		//set empty, red or blue token image
		switch(token) {
		case Blue:
			tokenList[x][y].setImageResource(R.drawable.blue);
			break;
		case Red:
			tokenList[x][y].setImageResource(R.drawable.red);
			break;
		case None:
			tokenList[x][y].setImageResource(R.drawable.none);
		}
	}
	
	public void generateField() {
		TableLayout layout = (TableLayout)getView().findViewById(R.id.content);
		createRows(layout);
		layout.setStretchAllColumns(true);
	}
	
	public void createRows(TableLayout tableLayout) {
		TableRow row = new TableRow(getActivity());
		
		//create all buttons to put tokens with
		for(int i = 0; i < GameLogicImpl.WIDTH;i++){
			final int k = i;
			Button button = new Button(getActivity());
			button.setText(R.string.put);
			button.setOnClickListener(new View.OnClickListener() {
				private int buttonNumber = k;
				@Override
				public void onClick(View v) {
					logic.placeToken(buttonNumber);
				}
			});
			row.addView(button);
		}
		tableLayout.addView(row);
		
		//create all token placeholder
		for(int y = GameLogicImpl.HEIGHT; y > 0; y--) {
			row = new TableRow(getActivity());
			for(int x = 0; x < GameLogicImpl.WIDTH; x++) {
				this.tokenList[x][y-1] = new ImageView(getActivity());
				row.addView(this.tokenList[x][y-1]);
			}
			tableLayout.addView(row);
		}
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public GameLogic getLogic() {
		return logic;
	}

	public void setLogic(GameLogic logic) {
		this.logic = logic;
		logic.registerListener(this);
	}

	@Override
	public void onGamefieldUpdated() {
		this.update();
	}

}
