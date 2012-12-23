package bpuzzle.pack;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ButtonPuzzleActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	static final int SIDE_NUMBER = 4;
	static final int BUTTON_NUMBER = SIDE_NUMBER * SIDE_NUMBER;
	ImageView[] imv = new ImageView[BUTTON_NUMBER];
	Rect[] imRect = new Rect[BUTTON_NUMBER];
	Paint paint;
	Resources res;
	Bitmap[] bm = new Bitmap[2];
	Button startButton;	
	boolean gameFlag;
	int point;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	// TODO Auto-generated method stub
    	super.onWindowFocusChanged(hasFocus);

    	init();
    }

	private void init() {
		// TODO Auto-generated method stub
		paint = new Paint();
		res = this.getResources();
		bm[0] = BitmapFactory.decodeResource(res, R.drawable.off_button); //0:off
		bm[1] = BitmapFactory.decodeResource(res, R.drawable.on_button); //1:on
		imv[0] = (ImageView)findViewById(R.id.Button0);
		imv[1] = (ImageView)findViewById(R.id.Button1);
		imv[2] = (ImageView)findViewById(R.id.Button2);
		imv[3] = (ImageView)findViewById(R.id.Button3);
		imv[4] = (ImageView)findViewById(R.id.Button4);
		imv[5] = (ImageView)findViewById(R.id.Button5);
		imv[6] = (ImageView)findViewById(R.id.Button6);
		imv[7] = (ImageView)findViewById(R.id.Button7);
		imv[8] = (ImageView)findViewById(R.id.Button8);
		imv[9] = (ImageView)findViewById(R.id.Button9);
		imv[10] = (ImageView)findViewById(R.id.Button10);
		imv[11] = (ImageView)findViewById(R.id.Button11);
		imv[12] = (ImageView)findViewById(R.id.Button12);
		imv[13] = (ImageView)findViewById(R.id.Button13);
		imv[14] = (ImageView)findViewById(R.id.Button14);
		imv[15] = (ImageView)findViewById(R.id.Button15);
		int[] location = new int[2];
		int w = imv[0].getWidth();
		int h = imv[0].getHeight();
		for(int i = 0; i < BUTTON_NUMBER; i++){
			imv[i].getLocationOnScreen(location);
			int l = location[0];
			int t = location[1];
			int r = l + w;
			int b = t + h;
//			int l = imv[i].getLeft();
//			int t = imv[i].getTop();
//			int r = imv[i].getRight();
//			int b = imv[i].getBottom();
			imRect[i] = new Rect(l, t, r, b);
			
			imv[i].setTag("off");
		}
		
		startButton = (Button)findViewById(R.id.StartButton);
		startButton.setOnClickListener(this);
		
		point = 0;
		gameFlag = false;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		gameFlag = true;
		v.setVisibility(View.GONE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() != MotionEvent.ACTION_DOWN){
			return true;
		}
		
		int tx = (int)event.getX();
		int ty = (int)event.getY();
		if(gameFlag){
			int pushedButton = hitButtonCheck(tx, ty);
			if(pushedButton >= 0){
				point += push(pushedButton);
			}
			if(point >= BUTTON_NUMBER){
				TextView message = (TextView)findViewById(R.id.Message);
				message.setVisibility(View.VISIBLE);
				gameFlag = false;
			}
		}

		return true;
	}

	private int push(int num) {
		// TODO Auto-generated method stub
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(num);
		if(num % SIDE_NUMBER > 0){ //Not Left Edge
			l.add(num - 1);
		}
		if(num > SIDE_NUMBER - 1){ //Not Top Edge
			l.add(num - SIDE_NUMBER);
		}
		if(num % SIDE_NUMBER < SIDE_NUMBER - 1){ //Not Right Edge
			l.add(num + 1);
		}
		if(num < BUTTON_NUMBER - SIDE_NUMBER){ //Not Bottom Edge
			l.add(num + SIDE_NUMBER);
		}
		
		int pm = 0;
		for(int n : l){
			if("on".equals(imv[n].getTag())){
				imv[n].setImageBitmap(bm[0]);
				imv[n].setTag("off");
				pm--;
			} else if("off".equals(imv[n].getTag())){
				imv[n].setImageBitmap(bm[1]);
				imv[n].setTag("on");
				pm++;
			}
		}
		
		return pm;
	}

	private int hitButtonCheck(int tx, int ty) {
		// TODO Auto-generated method stub
		for(int i = 0; i < BUTTON_NUMBER; i++){
			if(hitRectCheck(imRect[i], tx, ty)){
				return i;
			}
		}
		return -1;
	}

	private boolean hitRectCheck(Rect rect, int x, int y) {
		// TODO Auto-generated method stub
		if(rect.left <= x && rect.right >= x){
		if(rect.top <= y && rect.bottom >= y){
			return true;
		}
		}
		return false;
	}
}