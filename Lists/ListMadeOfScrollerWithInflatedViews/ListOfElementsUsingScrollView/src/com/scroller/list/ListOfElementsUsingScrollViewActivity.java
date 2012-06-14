package com.scroller.list;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scroller.list.ObservableScrollView.ScrollViewListener;

public class ListOfElementsUsingScrollViewActivity extends Activity implements ScrollViewListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ObservableScrollView scroll = (ObservableScrollView) findViewById(R.id.list_main_content_scroll);
		scroll.setScrollViewListener(this);

		LinearLayout listdetails = (LinearLayout) this.findViewById(R.id.linearLayout2);

		List<String> list = new ArrayList<String>();
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");
		list.add("Ola");
		list.add("mundo");
		list.add("estas");
		list.add("bom");
		list.add("?");

		for (int i = 0; i < list.size(); i++) {
			LayoutInflater inflater = LayoutInflater.from(this);

			View row = inflater.inflate(R.layout.list_item, null);

			row.setClickable(true);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
			lp.setMargins(0, 10, 0, 1);

			TextView row_text = (TextView) row.findViewById(R.id.textView1);
			row_text.setText(list.get(i));

			listdetails.addView(row, lp);
		}
	}

	public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
		View view_s = (View) (scrollView.getChildAt(scrollView.getChildCount() - 1));

		int diff = (view_s.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

		if (diff == 0) {
			LogHelper.log(getClass(), Log.DEBUG, "Cheguei ao fim");
		}
	}
}