package my.pretty.project.utils.activities;

import my.pretty.project.utils.LogHelper;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class LoggingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogHelper.log(getClass(), Log.VERBOSE, "onCreate(savedInstanceState = "+savedInstanceState+")");
	}
	
	//protected void onRestart()
	
	@Override
	protected void onStart() {
		super.onStart();
		LogHelper.log(getClass(), Log.VERBOSE, "onStart()");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		LogHelper.log(getClass(), Log.VERBOSE, "onRestoreInstanceState(savedInstanceState = "+savedInstanceState+")");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LogHelper.log(getClass(), Log.VERBOSE, "onResume()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		LogHelper.log(getClass(), Log.VERBOSE, "onPause()");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LogHelper.log(getClass(), Log.VERBOSE, "onSaveInstanceState(outState = "+outState+")");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		LogHelper.log(getClass(), Log.VERBOSE, "onStop()");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		LogHelper.log(getClass(), Log.VERBOSE, "onRestart()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogHelper.log(getClass(), Log.VERBOSE, "onDestroy()");
	}
	
	@Override
	public void finish() {
		super.finish();
		LogHelper.log(getClass(), Log.VERBOSE, "finish()");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogHelper.log(getClass(), Log.VERBOSE, "onActivityResult(requestCode = "+requestCode+", resultCode = "+resultCode+", data = "+data+")");
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		LogHelper.log(getClass(), Log.VERBOSE, "onAttachFragment(fragment = "+fragment+")");
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		LogHelper.log(getClass(), Log.VERBOSE, "onAttachFragment(id = "+id+", args = "+args+")");
		return super.onCreateDialog(id, args);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
		LogHelper.log(getClass(), Log.VERBOSE, "onAttachFragment(id = "+id+", dialog = "+dialog+", args = "+args+")");
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		String orientation = "";
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			orientation = "Landscape";
		} else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			orientation = "Portrait";
		} else if(newConfig.orientation == Configuration.ORIENTATION_SQUARE) {
			orientation = "Square";
		} else if(newConfig.orientation == Configuration.ORIENTATION_UNDEFINED) {
			orientation = "Undefined";
		}
		
		LogHelper.log(getClass(), Log.VERBOSE, "onConfigurationChanged(orientation = "+orientation+")");
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		LogHelper.log(getClass(), Log.VERBOSE, "onBackPressed()");
	}
}
