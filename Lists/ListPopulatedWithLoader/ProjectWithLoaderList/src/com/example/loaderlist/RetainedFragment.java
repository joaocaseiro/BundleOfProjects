package com.example.loaderlist;

import junit.framework.Assert;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.ProgressBar;

public class RetainedFragment extends Fragment {
	private static final String SHOW_DIALOG = "should_show_a_dialog_or_not";
	private static final String THREAD_TO_EXECUTE = "thread_that_will_be_executed_in_the_fragment";

	/**
	 * tag that should be used when adding the RetainedFragment to the fragment manager
	 */
	public static final String RETAINED_FRAGMENT_MANAGER_TAG = "retained_fragment_for_the_manager_tag";


	private static RetainedFragment fragment;
	private static Object syncObject = new Object();
	
	private boolean showDialog = false;
	private Executor executor = null;
	private Thread mThread;
	
    ProgressBar mProgressBar;
    int mPosition;
    boolean mReady = false;
    boolean mQuiting = false;
	private Handler handler;

    public RetainedFragment() {
    }
    
    public static RetainedFragment getInstance(boolean showDialog, Executor runner, Activity activity) {
    	if(fragment == null) {						//fugly
	    	synchronized (syncObject) {				//slow
	    		if(fragment == null) {
	    			if(activity != null) {
	    				fragment = (RetainedFragment) activity.getFragmentManager().findFragmentByTag(RETAINED_FRAGMENT_MANAGER_TAG);
	    				if(fragment == null) {
	    					fragment = createNewFragment();
	    				}
	    			} else {
	    				fragment = createNewFragment();	    				
	    			}
	    		}
			}	// end synchronized
    	}
    	
    	Bundle config = new Bundle();
    	config.putBoolean(SHOW_DIALOG, showDialog);
    	config.putParcelable(THREAD_TO_EXECUTE, runner);
    	fragment.setArguments(config);
    	return fragment;
    }
    
    private static RetainedFragment createNewFragment() {
		return new RetainedFragment();
	}

	/**
     * Fragment initialization.  We want to be retained and
     * start our thread.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setRetainInstance(true);
    	
    	Bundle arguments = getArguments();
    	if(arguments != null) {
    		showDialog = arguments.getBoolean(SHOW_DIALOG);
    		executor = (Executor) arguments.getParcelable(THREAD_TO_EXECUTE);
    	}
    	
    	Assert.assertNotNull(executor);
    	
    	handler = new Handler();
    	
    	executor.prepareRun();
    	
    	// Start up the worker thread.
    	mThread = new Thread(new Runnable() {

			@Override
			public void run() {
				executor.run();
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						executor.afterRun();
					}
				});
			}
    		
    	});
    	mThread.start();
    }

    /**
     * This is called when the Fragment's Activity is ready to go, after
     * its content view has been installed; it is called both after
     * the initial fragment creation and after the fragment is re-attached
     * to a new activity.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We are ready for our thread to go.
        synchronized (mThread) {
            mReady = true;
            executor.updateNewActivityReferences(getActivity());
            mThread.notify();
        }
    }

    /**
     * This is called when the fragment is going away.  It is NOT called
     * when the fragment is being propagated between activity instances.
     */
    @Override
    public void onDestroy() {
        // Make the thread go away.
        synchronized (mThread) {
            mReady = false;
            mQuiting = true;
            mThread.notify();
        }

        super.onDestroy();
    }

    /**
     * This is called right before the fragment is detached from its
     * current activity instance.
     */
    @Override
    public void onDetach() {
        // This fragment is being detached from its activity.  We need
        // to make sure its thread is not going to touch any activity
        // state after returning from this function.
        synchronized (mThread) {
            mProgressBar = null;
            mReady = false;
            executor.invalidateOldActivityReferences();
            mThread.notify();
        }

        super.onDetach();
    }

    /**
     * API for our UI to restart the progress thread.
     */
    public void restart() {
        synchronized (mThread) {
            mPosition = 0;
            mThread.notify();
        }
    }
    
    public interface Executor extends Runnable, Parcelable {
    	public void invalidateOldActivityReferences();
    	public void updateNewActivityReferences(Activity newActivity);
    	public void prepareRun();
    	//public void run();	//from Runnable
    	public void afterRun();
    }
}
