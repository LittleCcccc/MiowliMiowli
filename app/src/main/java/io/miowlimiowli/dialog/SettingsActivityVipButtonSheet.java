/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.dialog;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import io.miowlimiowli.R;


public class SettingsActivityVipButtonSheet extends BottomSheetDialogFragment {

	private TextView textView_y;
	private TextView textView_n;

	private BottomSheetListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v =  inflater.inflate(R.layout.settings_activity_vip_button_sheet, container, false);
		textView_y = v.findViewById(R.id.tv1);
		textView_n = v.findViewById(R.id.tv2);
		textView_y.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onButtonClicked("Yes");
				dismiss();
			}
		});
		textView_n.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onButtonClicked("No");
				dismiss();
			}
		});
		return v;
	}

	public interface BottomSheetListener{
		void onButtonClicked(String text);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		try{
		mListener = (BottomSheetListener) context;
		}catch(ClassCastException e)
		{
			throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
		}
	}
}

