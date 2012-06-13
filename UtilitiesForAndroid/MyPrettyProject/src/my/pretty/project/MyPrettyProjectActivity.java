package my.pretty.project;

import android.app.Activity;
import android.os.Bundle;

public class MyPrettyProjectActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int i = 0, j = 5;

        for( ; i < 3 && j++ < 8; i++) {
        	System.out.println(i + "_" + j);
        }
        //0_6
        //1_7
        //2_8
    }
}