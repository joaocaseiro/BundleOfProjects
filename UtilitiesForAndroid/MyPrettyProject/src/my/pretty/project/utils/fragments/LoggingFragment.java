package my.pretty.project.utils.fragments;

import my.pretty.project.utils.LogHelper;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoggingFragment extends Fragment {
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogHelper.log(getClass(), Log.VERBOSE, "onAttach(activity = "+activity+")");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogHelper.log(getClass(), Log.VERBOSE, "onCreate(savedInstance = "+savedInstanceState+")");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		LogHelper.log(getClass(), Log.VERBOSE, "onCreateView(inflater = "+inflater+", container = "+container+", savedInstanceState = "+savedInstanceState+", view = "+view+")");
		return view;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogHelper.log(getClass(), Log.VERBOSE, "onActivityCreated(savedInstance = "+savedInstanceState+")");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		LogHelper.log(getClass(), Log.VERBOSE, "onStart()");
	}
	
	@Override
	public void onResume() {
		super.onPause();
		LogHelper.log(getClass(), Log.VERBOSE, "onResume()");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		LogHelper.log(getClass(), Log.VERBOSE, "onPause()");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		LogHelper.log(getClass(), Log.VERBOSE, "onStop()");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogHelper.log(getClass(), Log.VERBOSE, "onDestroyView()");
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LogHelper.log(getClass(), Log.VERBOSE, "onSaveInstanceState(outState = "+outState+")");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogHelper.log(getClass(), Log.VERBOSE, "onDestroy()");
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		LogHelper.log(getClass(), Log.VERBOSE, "onDetach()");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogHelper.log(getClass(), Log.VERBOSE, "onActivityResult(requestCode = "+requestCode+", resultCode = "+resultCode+", data = "+data+")");
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
}
