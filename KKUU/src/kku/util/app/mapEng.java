package kku.util.app;



import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class mapEng extends Activity implements OnTouchListener {
	private static final String TAG = "Touch";

	private static final float MIN_ZOOM = 1.0f;
	private static final float MAX_ZOOM = 5.0f;

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.mapeng);
	   ImageView view = (ImageView) findViewById(R.id.mapeng);
	   //view.setLayoutParams(new GridView.LayoutParams(85, 85));
	   view.setScaleType(ImageView.ScaleType.FIT_CENTER);
	   view.setOnTouchListener(this); 
	}

	public boolean onTouch(View v, MotionEvent event) {
		//Cancel pich to zoom text
		TextView tv = (TextView) findViewById(R.id.pinchtozoom);
		tv.setText("");
	   ImageView view = (ImageView) v;
	   view.setScaleType(ImageView.ScaleType.MATRIX);
	   float scale;

	   // Dump touch event to log
	   dumpEvent(event);

	   // Handle touch events here...
	   switch (event.getAction() & MotionEvent.ACTION_MASK) {

	   case MotionEvent.ACTION_DOWN: //first finger down only
	      savedMatrix.set(matrix);
	      start.set(event.getX(), event.getY());
	      Log.d(TAG, "mode=DRAG" );
	      mode = DRAG;
	      break;
	   case MotionEvent.ACTION_UP: //first finger lifted
	   case MotionEvent.ACTION_POINTER_UP: //second finger lifted
	      mode = NONE;
	      Log.d(TAG, "mode=NONE" );
	      break;
	   case MotionEvent.ACTION_POINTER_DOWN: //second finger down
	      oldDist = spacing(event);
	      Log.d(TAG, "oldDist=" + oldDist);
	      if (oldDist > 5f) {
	         savedMatrix.set(matrix);
	         midPoint(mid, event);
	         mode = ZOOM;
	         Log.d(TAG, "mode=ZOOM" );
	      }
	      break;

	   case MotionEvent.ACTION_MOVE: 
	      if (mode == DRAG) { //movement of first finger
	         matrix.set(savedMatrix);
	         if (view.getLeft() >= -392){
	            matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
	         }
	      }
	      else if (mode == ZOOM) { //pinch zooming
	         float newDist = spacing(event);
	         Log.d(TAG, "newDist=" + newDist);
	         if (newDist > 5f) {
	            matrix.set(savedMatrix);
	            scale = newDist / oldDist; //thinking i need to play around with this value to limit it**
	            matrix.postScale(scale, scale, mid.x, mid.y);
	         }
	      }
	      break;
	   }

	   // Perform the transformation
	   view.setImageMatrix(matrix);

	   return true; // indicate event was handled
	}

	private float spacing(MotionEvent event) {
	   float x = event.getX(0) - event.getX(1);
	   float y = event.getY(0) - event.getY(1);
	   return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
	   float x = event.getX(0) + event.getX(1);
	   float y = event.getY(0) + event.getY(1);
	   point.set(x / 2, y / 2);
	}

	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
	   String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
	      "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
	   StringBuilder sb = new StringBuilder();
	   int action = event.getAction();
	   int actionCode = action & MotionEvent.ACTION_MASK;
	   sb.append("event ACTION_" ).append(names[actionCode]);
	   if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	         || actionCode == MotionEvent.ACTION_POINTER_UP) {
	      sb.append("(pid " ).append(
	      action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	      sb.append(")" );
	   }
	   sb.append("[" );
	   for (int i = 0; i < event.getPointerCount(); i++) {
	      sb.append("#" ).append(i);
	      sb.append("(pid " ).append(event.getPointerId(i));
	      sb.append(")=" ).append((int) event.getX(i));
	      sb.append("," ).append((int) event.getY(i));
	      if (i + 1 < event.getPointerCount())
	         sb.append(";" );
	   }
	   sb.append("]" );
	   Log.d(TAG, sb.toString());
	}
	
}