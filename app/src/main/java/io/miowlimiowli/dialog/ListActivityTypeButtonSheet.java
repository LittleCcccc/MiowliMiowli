package io.miowlimiowli.dialog;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.core.view.animation.PathInterpolatorCompat;

import java.util.ArrayList;
import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.manager.Manager;

public class ListActivityTypeButtonSheet extends BottomSheetDialogFragment {
    private Button[] p;
    private boolean[] type;
    private TypeDialogFragment_Listener mListener;

    public interface TypeDialogFragment_Listener{
        void getDataFrom_TypeDialogFragment();
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
            startAnimation(p[i]);
        }
    });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.list_activity_type_button_sheet, container, false);


        p = new Button[12];
        type = new boolean[12];
        for(int i=1;i<=10;i++){
            if(Manager.getInstance().getCat_list().contains(Manager.getInstance().getCatAtPosition(i)))
                type[i]=true;
        }
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
                //startAnimation(p[11]);
                mListener.getDataFrom_TypeDialogFragment();
                refreshCatList();
                dismiss();
            }
        });
        return v;
    }

    public void refreshCatList()
    {
        List<String> cat = new ArrayList<>();
        for(int i=1;i<=10;i++){
            if(type[i])
                cat.add(Manager.getInstance().getCatAtPosition(i));
        }
        Manager.getInstance().setCat_list(cat);
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

    public void startAnimation(Button button){
        ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(button, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
        animator5.setDuration(1000);
        animator5.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(button, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.6f, 1f), Keyframe.ofFloat(1f, 1f)));
        animator6.setDuration(1000);
        animator6.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animator5, animator6);
        animatorSet3.setTarget(button);

        animatorSet3.start();
    }


}
