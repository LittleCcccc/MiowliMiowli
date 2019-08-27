package io.miowlimiowli.dialog;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import io.miowlimiowli.R;
public class ListActivityTypeButtonSheet  extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.list_activity_type_button_sheet, container, false);

        return v;
    }
}
