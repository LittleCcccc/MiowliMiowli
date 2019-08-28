package io.miowlimiowli.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import io.miowlimiowli.R;

public class NewsObjectFragment extends Fragment {
    public static final String ARG_OBJECT="object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.news_collection_object, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        Bundle args = getArguments();
       // ((TextView)view.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
    }
}
