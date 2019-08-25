/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.dialog;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import io.miowlimiowli.R;


public class SettingsActivityVipButtonSheet extends BottomSheetDialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.settings_activity_vip_button_sheet, container, false);
	}
}
