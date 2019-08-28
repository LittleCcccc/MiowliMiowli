package io.miowlimiowli.dialog;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;

import io.miowlimiowli.R;
public class ListActivityTypeButtonSheet extends BottomSheetDialogFragment {
    private Button[] p;
    private boolean[] type;
    private TypeDialogFragment_Listener mListener;


    public interface TypeDialogFragment_Listener{
        void getDataFrom_TypeDialogFragment(boolean[] type);
    }


    public void gettype(int i)
    {
        if(type[i])
        {
            p[i].setBackgroundResource(R.drawable.type_button_ripple);
        }
        else{
            p[i].setBackgroundResource(R.drawable.white_type_button_ripple);
        }

        p[i].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            type[i]=!type[i];
            if(type[i])
            {
                p[i].setBackgroundResource(R.drawable.type_button_ripple);
            }
            else{
                p[i].setBackgroundResource(R.drawable.white_type_button_ripple);
            }
        }
    });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.list_activity_type_button_sheet, container, false);


        p = new Button[12];
        type = new boolean[12];
        p[1] = v.findViewById(R.id.button);
        p[2] = v.findViewById(R.id.button2);
        p[3] = v.findViewById(R.id.button3);
        p[4] = v.findViewById(R.id.button4);
        p[5] = v.findViewById(R.id.button5);
        p[6] = v.findViewById(R.id.button6);
        p[7] = v.findViewById(R.id.button7);
        p[8] = v.findViewById(R.id.button8);
        p[9] = v.findViewById(R.id.button9);
        p[10] = v.findViewById(R.id.button10);
        p[11] = v.findViewById(R.id.button11);

        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            for(int i =1;i<=10;i++)
            {
                type[i] = bundle.getBoolean("type"+i);
            }
        }
        for(int i=1;i<=10;i++)
            gettype(i);
        p[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getDataFrom_TypeDialogFragment(type);
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (TypeDialogFragment_Listener)getTargetFragment();
        }catch(ClassCastException e)
        {
             throw new ClassCastException(context.toString()+"must implement BottomSheetListener");
        }
    }
}
