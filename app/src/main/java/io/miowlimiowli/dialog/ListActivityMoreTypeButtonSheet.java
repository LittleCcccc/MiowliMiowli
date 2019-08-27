package io.miowlimiowli.dialog;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import io.miowlimiowli.R;

public class ListActivityMoreTypeButtonSheet extends BottomSheetDialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.settings_activity_vip_button_sheet, container, false);
    }

}
