/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.dialog;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import io.miowlimiowli.R;


public class SettingsActivityVipButtonSheet extends BottomSheetDialogFragment {

	private TextView textView_y;
	private TextView textView_n;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.settings_activity_vip_button_sheet, container, false);
	}


}
