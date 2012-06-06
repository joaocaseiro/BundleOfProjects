package com.throttle.loader;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Demonstration of bottom to top implementation of a content provider holding
 * structured data through displaying it in the UI, using throttling to reduce
 * the number of queries done when its data changes.
 */
public class LoaderThrottle extends Activity {
	// Debugging.
	static final String TAG = "LoaderThrottle";

	/**
	 * The authority we use to get to our sample provider.
	 */
	public static final String AUTHORITY = "com.example.android.apis.appi.LoaderThrottle";

	/**
	 * Definition of the contract for the main table of our provider.
	 */
	public static final class MainTable implements BaseColumns {

		// This class cannot be instantiated
		private MainTable() {
		}

		/**
		 * The table name offered by this provider
		 */
		public static final String TABLE_NAME = "main";

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/main");

		/**
		 * The content URI base for a single row of data. Callers must append a
		 * numeric row id to this Uri to retrieve a row
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse("content://" + AUTHORITY + "/main/");

		/**
		 * The MIME type of {@link #CONTENT_URI}.
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.api-demos-throttle";

		/**
		 * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
		 * row.
		 */
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.api-demos-throttle";
		/**
		 * The default sort order for this table
		 */
		public static final String DEFAULT_SORT_ORDER = "data COLLATE LOCALIZED ASC";

		/**
		 * Column name for the single column holding our data.
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME_DATA = "data";
	}

	/**
	 * This class helps open, create, and upgrade the database file.
	 */
	static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "loader_throttle.db";
		private static final int DATABASE_VERSION = 2;

		DatabaseHelper(Context context) {

			// calls the super constructor, requesting the default cursor
			// factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * 
		 * Creates the underlying database with table name and column names
		 * taken from the NotePad class.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + MainTable.TABLE_NAME + " (" + MainTable._ID + " INTEGER PRIMARY KEY," + MainTable.COLUMN_NAME_DATA + " TEXT" + ");");
		}

		/**
		 * 
		 * Demonstrates that the provider must consider what happens when the
		 * underlying datastore is changed. In this sample, the database is
		 * upgraded the database by destroying the existing data. A real
		 * application should upgrade the database in place.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// Logs that the database is being upgraded
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

			// Kills the table and existing data
			db.execSQL("DROP TABLE IF EXISTS notes");

			// Recreates the database with a new version
			onCreate(db);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getFragmentManager();

		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			ThrottledLoaderListFragment list = new ThrottledLoaderListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class ThrottledLoaderListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

		// Menu identifiers
		static final int POPULATE_ID = Menu.FIRST;
		static final int CLEAR_ID = Menu.FIRST + 1;

		// This is the Adapter being used to display the list's data.
		SimpleCursorAdapter mAdapter;

		// If non-null, this is the current filter the user has provided.
		String mCurFilter;

		// Task we have running to populate the database.
		AsyncTask<Void, Void, Void> mPopulatingTask;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setEmptyText("No data.  Select 'Populate' to fill with data from Z to A at a rate of 4 per second.");
			setHasOptionsMenu(true);

			// Create an empty adapter we will use to display the loaded data.
			mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, new String[] { MainTable.COLUMN_NAME_DATA },
					new int[] { android.R.id.text1 }, 0);
			setListAdapter(mAdapter);

			// Start out with a progress indicator.
			setListShown(false);

			// Prepare the loader. Either re-connect with an existing one,
			// or start a new one.
			getLoaderManager().initLoader(0, null, this);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			menu.add(Menu.NONE, POPULATE_ID, 0, "Populate").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(Menu.NONE, CLEAR_ID, 0, "Clear").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			final ContentResolver cr = getActivity().getContentResolver();

			switch (item.getItemId()) {
			case POPULATE_ID:
				if (mPopulatingTask != null) {
					mPopulatingTask.cancel(false);
				}
				mPopulatingTask = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						for (char c = 'Z'; c >= 'A'; c--) {
							if (isCancelled()) {
								break;
							}
							StringBuilder builder = new StringBuilder("Data ");
							builder.append(c);
							ContentValues values = new ContentValues();
							values.put(MainTable.COLUMN_NAME_DATA, builder.toString());
							cr.insert(MainTable.CONTENT_URI, values);
							// Wait a bit between each insert.
							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
							}
						}
						return null;
					}
				};
				mPopulatingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				return true;

			case CLEAR_ID:
				if (mPopulatingTask != null) {
					mPopulatingTask.cancel(false);
					mPopulatingTask = null;
				}
				AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						cr.delete(MainTable.CONTENT_URI, null, null);
						return null;
					}
				};
				task.execute((Void[]) null);
				return true;

			default:
				return super.onOptionsItemSelected(item);
			}
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// Insert desired behavior here.
			Log.i(TAG, "Item clicked: " + id);
		}

		// These are the rows that we will retrieve.
		static final String[] PROJECTION = new String[] { MainTable._ID, MainTable.COLUMN_NAME_DATA, };

		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			CursorLoader cl = new CursorLoader(getActivity(), MainTable.CONTENT_URI, PROJECTION, null, null, null);
			cl.setUpdateThrottle(2000); // update at most every 2 seconds.
			return cl;
		}

		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			mAdapter.swapCursor(data);

			// The list should now be shown.
			if (isResumed()) {
				setListShown(true);
			} else {
				setListShownNoAnimation(true);
			}
		}

		public void onLoaderReset(Loader<Cursor> loader) {
			mAdapter.swapCursor(null);
		}
	}
}