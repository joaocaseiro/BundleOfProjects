package my.pretty.project;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

public class FragmentRetainInstance extends Activity { }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // First time init, create the UI.
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().add(android.R.id.content,
//                    new UiFragment()).commit();
//        }
//    }
//
//    /**
//     * This is a fragment showing UI that will be updated from work done
//     * in the retained fragment.
//     */
//    public static class UiFragment extends Fragment {
//        RetainedFragment mWorkFragment;
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View v = inflater.inflate(R.layout.fragment_retain_instance, container, false);
//
//            // Watch for button clicks.
//            Button button = (Button)v.findViewById(R.id.restart);
//            button.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    mWorkFragment.restart();
//                }
//            });
//
//            return v;
//        }
//
//        @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//            super.onActivityCreated(savedInstanceState);
//
//            FragmentManager fm = getFragmentManager();
//
//            // Check to see if we have retained the worker fragment.
//            mWorkFragment = (RetainedFragment)fm.findFragmentByTag("work");
//
//            // If not retained (or first time running), we need to create it.
//            if (mWorkFragment == null) {
//                mWorkFragment = new RetainedFragment();
//                // Tell it who it is working with.
//                mWorkFragment.setTargetFragment(this, 0);
//                fm.beginTransaction().add(mWorkFragment, "work").commit();
//            }
//        }
//
//    }
//
//    /**
//     * This is the Fragment implementation that will be retained across
//     * activity instances.  It represents some ongoing work, here a thread
//     * we have that sits around incrementing a progress indicator.
//     */
//    
