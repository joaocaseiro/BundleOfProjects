package com.service.simple;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public abstract class AsyncTaskDialogFragment<Params, Progress, Result> extends DialogFragment {
	private static long sTaskIdCounter = 1;

	private Handler mHandler;
	private FragmentTask mAsyncTask;

	private boolean mVerbose = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogHelper.log(getClass(), Log.VERBOSE, "onCreate()", mVerbose);
		super.onCreate(savedInstanceState);

		/* this allows to survive configuration changes */
		setRetainInstance(true);

		mHandler = new Handler();
		mAsyncTask = new FragmentTask();
	}

	@Override
	public void onDestroy() {
		LogHelper.log(getClass(), Log.VERBOSE, "onDestroy()", mVerbose);
		super.onDestroy();
		mAsyncTask.cancel(true);
	}

	/********************************/
	/** Helpers **/
	/********************************/

	protected void runOnUiThread(Runnable runnable) {
		mHandler.post(runnable);
	}

	protected Handler getHandler() {
		return mHandler;
	}

	/********************************/
	/** AsyncTask callbacks **/
	/********************************/

	protected void onPreExecute() {
	}

	protected void onProgressUpdate(Progress... values) {
	};

	protected abstract Result doInBackground(Params... params);

	protected void onPostExecute(Result result) {
	};

	protected void onCancelled() {
	}

	protected void publishProgress(Progress... progress) {
		mAsyncTask.publishProgressHelper(progress);
	}

	/*********************************/
	/** AsyncTask functions **/
	/*********************************/

	public void execute(Params... params) {
		LogHelper.log(getClass(), Log.VERBOSE, "execute() - params: " + params.length, mVerbose);
		mAsyncTask.execute(params);
	}

	public Result get() throws CancellationException, InterruptedException, ExecutionException {
		LogHelper.log(getClass(), Log.VERBOSE, "get()", mVerbose);
		return mAsyncTask.get();
	}

	public Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		LogHelper.log(getClass(), Log.VERBOSE, "get(timeout, unit)", mVerbose);
		return mAsyncTask.get(timeout, unit);
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		LogHelper.log(getClass(), Log.VERBOSE, "cancel()", mVerbose);
		return mAsyncTask.cancel(mayInterruptIfRunning);
	}

	public boolean isCancelled() {
		return mAsyncTask.isCancelled();
	}

	public AsyncTask.Status getStatus() {
		return mAsyncTask.getStatus();
	}

	public AsyncTask<Params, Progress, Result> getAsyncTask() {
		return mAsyncTask;
	}

	public void setVerbose(boolean verbose) {
		mVerbose = verbose;
	}

	public class FragmentTask extends AsyncTask<Params, Progress, Result> {
		private final long mId;

		public FragmentTask() {
			synchronized (AsyncTaskDialogFragment.class) {
				mId = sTaskIdCounter++;
			}
		}

		@Override
		protected void onPreExecute() {
			LogHelper.log(getClass(), Log.VERBOSE, mId + ": onPreExecute()", mVerbose);
			AsyncTaskDialogFragment.this.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Progress... values) {
			LogHelper.log(getClass(), Log.VERBOSE, mId + ": onProgressUpdate()", mVerbose);
			AsyncTaskDialogFragment.this.onProgressUpdate(values);
		};

		@Override
		protected Result doInBackground(Params... params) {
			LogHelper.log(getClass(), Log.VERBOSE, mId + ": doInBackground()", mVerbose);
			return AsyncTaskDialogFragment.this.doInBackground(params);
		};

		@Override
		protected void onPostExecute(Result result) {
			LogHelper.log(getClass(), Log.VERBOSE, mId + ": onPostExecute(): " + result, mVerbose);
			AsyncTaskDialogFragment.this.onPostExecute(result);
		};

		@Override
		protected void onCancelled() {
			LogHelper.log(getClass(), Log.VERBOSE, mId + ": onCancelled()", mVerbose);
			AsyncTaskDialogFragment.this.onCancelled();
		}

		protected void publishProgressHelper(Progress... progress) {
			super.publishProgress(progress);
		}
	}
	
	@Override
	public void onDestroyView() {		
		Dialog dialog = null;
		if ((dialog = getDialog()) != null) {	//bug fix
			dialog.setDismissMessage(null);
		}
		super.onDestroyView();
	}
}