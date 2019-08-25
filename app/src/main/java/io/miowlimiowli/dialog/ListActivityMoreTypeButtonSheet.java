package io.miowlimiowli.dialog;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import io.miowlimiowli.R;

import android.support.design.widget.BottomSheetDialogFragment;

public class ListActivityMoreTypeButtonSheet extends BottomSheetDialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.settings_activity_vip_button_sheet, container, false);
    }

}
