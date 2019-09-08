/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import io.miowlimiowli.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import io.miowlimiowli.activity.*;
import io.miowlimiowli.manager.Manager;

import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Consumer;

import static android.app.Activity.RESULT_OK;


public class ProfileActivity extends Fragment {
	
	public static ProfileActivity newInstance() {
	
		ProfileActivity fragment = new ProfileActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}

	private FloatingActionButton catButton;

	private ImageView avatarImageView;
	private TextView myNicknameTextView;
	private TextView myShortTextView;
	private TextView myLongTextView;
	private TextView collectSumTextView;
	private Button collectionButton;
	private TextView commentSumTextView;
	private Button commentButton;
	private TextView brouseSumTextView;
	private Button brouserButton;
	private ImageButton settingsButton;


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.profile_activity, container, false);
	}

	private View.OnClickListener getListener(String name, TextView s1, Consumer<String> s2){
		return new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this.getActivity(), R.style.MyDialogTheme);
				builder.setTitle(name);
				EditText input = new EditText(ProfileActivity.this.getContext());
				input.setText(s1.getText());
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				input.requestFocus();

				builder.setView(input);
				builder.setPositiveButton("确定", (dialog, id) -> {
					s1.setText(input.getText());
					s2.accept(input.getText().toString());
				});
				builder.setNegativeButton("取消", (dialog, id) -> {
					dialog.cancel();
				});
				AlertDialog dialog = builder.create();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				dialog.show();
			}
		};
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		init();
		this.myNicknameTextView.setOnClickListener(
				getListener("设置昵称", myNicknameTextView, Manager.getInstance().getUser()::setNickname)
		);
		this.myShortTextView.setOnClickListener(
				getListener("设置短介绍", myShortTextView, Manager.getInstance().getUser()::setShort_description)
		);
		this.myLongTextView.setOnClickListener(
				getListener("设置长介绍", myLongTextView, Manager.getInstance().getUser()::setLong_description)
		);
	}

	@Override
	public void onResume() {
		super.onResume();
		Manager manager = Manager.getInstance();
		manager.fetch_comment_list().subscribe((list)->{
			this.commentSumTextView.setText(String.valueOf(list.size()));
		});
		manager.fetch_favorite_list().subscribe((list)->{
			this.collectSumTextView.setText(String.valueOf(list.size()));
		});
		manager.fetch_read_list().subscribe((list)->{
			this.brouseSumTextView.setText(String.valueOf(list.size()));
		});
		this.myNicknameTextView.setText( manager.getUser().nickname);
		this.myShortTextView.setText(manager.getUser().short_description);
		this.myLongTextView.setText(manager.getUser().long_description);
		Glide.with(this).load(manager.getUser().avator).into(avatarImageView);
	}

    public static final int PICK_IMAGE = 1;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE) {
				Uri uri = data.getData();
				Uri desturi = Uri.fromFile(new File(getContext().getCacheDir(), "IMG_" + System.currentTimeMillis()));
				UCrop.of(uri, desturi)
						.withAspectRatio(1, 1)
						.start(getContext(), this);
			}
		}
		System.out.println(resultCode);
		System.out.println(requestCode);
		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			final Uri uri = UCrop.getOutput(data);
			InputStream inputStream = null;
			try {
				inputStream = getContext().getContentResolver().openInputStream(uri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Drawable drawable = Drawable.createFromStream(inputStream, uri.toString() );
			avatarImageView.setImageDrawable(drawable);
			Manager.getInstance().getUser().setAvator(getContext(), drawable);
		} else if (resultCode == UCrop.RESULT_ERROR) {
			final Throwable cropError = UCrop.getError(data);
		}
	}

	public void onAvatarPressed() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),PICK_IMAGE);
	}
	
	public void onColletctionPressed() {
	
		this.startCommentActivity("0");
	}

	public void onCommentPressed() {
	
		this.startCommentActivity("1");
	}
	
	public void onBrouserPressed() {
	
		this.startCommentActivity("2");
	}
	
	public void onSettingsButtonPressed() {
	
		this.startSettingsActivity();
	}
	
	public void init() {

		// Configure Avatar component
		avatarImageView = this.getView().findViewById(R.id.avatar_image_view);
		avatarImageView.setOnClickListener((view) -> {
	this.onAvatarPressed();
});
		
		// Configure MyNickname component
		myNicknameTextView = this.getView().findViewById(R.id.my_nickname_text_view);
		
		// Configure MyTitle component
		myShortTextView = this.getView().findViewById(R.id.my_short_text_view);
		
		// Configure MyIntroduction component
		myLongTextView = this.getView().findViewById(R.id.my_long_text_view);
		
		// Configure 365 component
		collectSumTextView = this.getView().findViewById(R.id.collect_sum_text_view);
		
		// Configure Collection component
		collectionButton = this.getView().findViewById(R.id.clooection_button);
		collectionButton.setOnClickListener((view) -> {
	this.onColletctionPressed();
});
		
		// Configure 58k component
		commentSumTextView = this.getView().findViewById(R.id.comment_sum_text_view);
		
		// Configure DisplayableComment component
		commentButton = this.getView().findViewById(R.id.comment_button);
		commentButton.setOnClickListener((view) -> {
	this.onCommentPressed();
});
		
		// Configure 326 component
		brouseSumTextView = this.getView().findViewById(R.id.brouse_sum_text_view);
		
		// Configure Brouse component
		brouserButton = this.getView().findViewById(R.id.brouser_button);
		brouserButton.setOnClickListener((view) -> {
	this.onBrouserPressed();
});
		
		// Configure Setting component
		settingsButton = this.getView().findViewById(R.id.settings_button);
		settingsButton.setOnClickListener((view) -> {
	this.onSettingsButtonPressed();
});

	}

	private void startSettingsActivity() {
		this.getActivity().startActivity(SettingsActivity.newIntent(this.getContext()));
	}
	
	private void startCommentActivity(String pos) {

		//this.getActivity().startActivity(CollectionActivity.newIntent(this.getContext()));
		Intent intent = new Intent(this.getContext(),CollectionActivity.class);
		intent.putExtra(CollectionActivity.position,pos);
		this.getActivity().startActivity(intent);
	}
}
