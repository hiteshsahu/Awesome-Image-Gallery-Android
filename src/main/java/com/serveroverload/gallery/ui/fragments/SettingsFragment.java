/**
 * 
 */
package com.serveroverload.gallery.ui.fragments;

import com.example.test.R;
import com.serveroverload.gallery.util.PreferenceHelper;
import com.serveroverload.gallery.util.UtilFunctions.AnimationType;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author 663918
 *
 */
public class SettingsFragment extends Fragment {

	public static Fragment newInstance() {
		return new SettingsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

		CheckBox submitCrashLogs = ((CheckBox) rootView.findViewById(R.id.checkbox_submit_logs));

		submitCrashLogs.setChecked(PreferenceHelper.getPrefernceHelperInstace(getActivity())
				.getBoolean(PreferenceHelper.SUBMIT_LOGS, true));

		submitCrashLogs.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				PreferenceHelper.getPrefernceHelperInstace(getActivity()).setBoolean(PreferenceHelper.SUBMIT_LOGS,
						isChecked);

			}
		});

		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

//					UtilFunctions.switchContent(R.id.frag_root, UtilFunctions.HOME_FRAGMENT_TAG,
//							(SampleActivity) getActivity(), AnimationType.SLIDE_RIGHT);

				}
				return true;
			}
		});

		return rootView;
	}

}
