package my.pretty.project.tests;

import junit.framework.Assert;
import my.pretty.project.MyPrettyProjectActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;

/**
 * See object instrumentation in the android manifest. it is outside the application object. must also have the uses-library android.test.runner 
 * @author jcaseiro
 *
 */
public class TaskTests extends ActivityInstrumentationTestCase2<MyPrettyProjectActivity> {

	public TaskTests() {
		super("my.pretty.project", MyPrettyProjectActivity.class);
	}

	//private MyPrettyProjectActivity activity;
	//private Instrumentation instrument;

	protected void setUp() throws Exception {
		super.setUp();

		//activity = getActivity();
		//intrument = getInstrumentation();
	}

	//EACH TEST MUST BE PUBLIC
	public void testSimple() {
		Assert.assertEquals(0, 1 - 1);
		//TouchUtils.dragViewBy(test, v, gravity, deltaX, deltaY)
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		//activity = null;
		//instrument = null;
	}
}