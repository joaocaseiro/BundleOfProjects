package com.tutorial.gridview.dragging;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleBaseAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] names = new String[] { "Joao", "Ana", "Pedro", "Jorge", "Joana", "Claudia", "Alexandra", "Tiago", "Luis", "Ricardo", "Manel", "Armindo",
			"Jose", "Miguel", "Rita", "Ines", "Manuela", "Andreia" };

	public SimpleBaseAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return names[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
			viewHolder.text = (TextView) convertView.findViewById(R.id.list_item_text);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.list_item_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		updateView(viewHolder, position);

		convertView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				ViewHolder holder = (ViewHolder) v.getTag();
				String text = (String) holder.text.getText();
				ClipData.Item item = new ClipData.Item(text);

				ClipData dragData = new ClipData(text, new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN, ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
				dragData.addItem(new ClipData.Item(String.valueOf(holder.position)));

				// Instantiates the drag shadow builder.
				DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

				// Starts the drag
				v.startDrag(dragData, // the data to be dragged
						myShadow, // the drag shadow builder
						null, // no need to use local data
						0 // flags (not currently used, set to 0)
				);
				return true;
			}
		});

		convertView.setOnDragListener(new MyDragListener());

		return convertView;
	}

	private void updateView(ViewHolder viewHolder, int position) {
		viewHolder.image.setImageResource(R.drawable.ic_launcher);
		viewHolder.text.setText(names[position]);
		viewHolder.position = position;
	}

	private class ViewHolder {
		public int position;
		public ImageView image;
		public TextView text;
	}

	private class MyDragListener implements View.OnDragListener {

		private Drawable original;

		@Override
		public boolean onDrag(View v, DragEvent event) {

			final int action = event.getAction();

			switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:
				if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
					v.setBackgroundColor(Color.BLUE);
					original = v.getBackground();
					v.invalidate();

					return true;
				} else {
					return false;
				}

			case DragEvent.ACTION_DRAG_ENTERED:
				v.setBackgroundColor(Color.GREEN);
				v.invalidate();

				return (true);

			case DragEvent.ACTION_DRAG_LOCATION:
				return (true);

			case DragEvent.ACTION_DRAG_EXITED:

				// Re-sets the color tint to blue. Returns true; the return
				// value is ignored.
				v.setBackgroundColor(Color.BLUE);

				// Invalidate the view to force a redraw in the new tint
				v.invalidate();

				return (true);

			case DragEvent.ACTION_DROP:

				// Gets the item containing the dragged data
				ClipData.Item data = event.getClipData().getItemAt(0);

				// Gets the item containing the dragged data
				ClipData.Item position = event.getClipData().getItemAt(1);

				// Gets the text data from the item.
				String dragData = (String) data.getText();
				
				ViewHolder holder = (ViewHolder)v.getTag();
				
				String tmp = names[holder.position];
				names[holder.position] = (String) data.getText(); 
				names[Integer.valueOf((String) position.getText())] = tmp;
				notifyDataSetChanged();

				// Turns off any color tints
				v.setBackgroundDrawable(original);

				// Invalidates the view to force a redraw
				v.invalidate();
				

				// Returns true. DragEvent.getResult() will return true.
				return (true);

			case DragEvent.ACTION_DRAG_ENDED:

				// Turns off any color tints
				v.setBackgroundDrawable(original);

				// Invalidates the view to force a redraw
				v.invalidate();

				// Does a getResult(), and displays what happened.
				if (event.getResult()) {

				} else {

				}
				;

				// returns true; the value is ignored.
				return (true);

			// An unknown action type was received.
			default:
				LogHelper.log(getClass(), Log.VERBOSE, "Unknown action type received by OnDragListener.");

				return false;
			}
		}
	}

	private static class MyDragShadowBuilder extends View.DragShadowBuilder {

		// The drag shadow image, defined as a drawable thing
		private static Bitmap shadow;

		// Defines the constructor for myDragShadowBuilder
		public MyDragShadowBuilder(View v) {

			// Stores the View parameter passed to myDragShadowBuilder.
			super(v);
		}

		// Defines a callback that sends the drag shadow dimensions and touch
		// point back to the
		// system.
		@Override
		public void onProvideShadowMetrics(Point size, Point touch) {
			// Defines local variables
			int width, height;

			// Sets the width of the shadow to half the width of the original
			// View
			width = getView().getWidth();

			// Sets the height of the shadow to half the height of the original
			// View
			height = getView().getHeight();

			// The drag shadow is a ColorDrawable. This sets its dimensions to
			// be the same as the
			// Canvas that the system will provide. As a result, the drag shadow
			// will fill the
			// Canvas.
			shadow = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(shadow);
			getView().draw(canvas);

			// Sets the size parameter's width and height values. These get back
			// to the system
			// through the size parameter.
			size.set(width, height);

			// Sets the touch point's position to be in the middle of the drag
			// shadow
			touch.set(width / 2, height / 2);
		}

		// Defines a callback that draws the drag shadow in a Canvas that the
		// system constructs
		// from the dimensions passed in onProvideShadowMetrics().
		@Override
		public void onDrawShadow(Canvas canvas) {

			// Draws the ColorDrawable in the Canvas passed in from the system.
			canvas.drawBitmap(shadow, new Matrix(), new Paint());
		}
	}

}
