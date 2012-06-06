package com.fragment.dialog.data;

import com.fragment.dialog.data.EditNameDialog.EditNameDialogListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

public class DialogFragmentReturnsDataThroughInterfaceActivity extends Activity implements EditNameDialogListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		showEditDialog();
	}

	private void showEditDialog() {
		FragmentManager fm = getFragmentManager();
		EditNameDialog editNameDialog = new EditNameDialog();
		editNameDialog.show(fm, "fragment_edit_name");
	}

	@Override
	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
	}
}