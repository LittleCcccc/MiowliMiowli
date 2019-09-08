/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import io.miowlimiowli.R;

import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.view.MenuItem;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Consumer;

import io.miowlimiowli.dialog.SettingsActivityVipButtonSheet;
import io.miowlimiowli.manager.Manager;


public class SettingsActivity extends AppCompatActivity implements SettingsActivityVipButtonSheet.BottomSheetListener {
	private Button cache_button;

	@Override
	public void onButtonClicked(String text) {
		if(text.equals("Yes"))
		{
			vipTextView.setText("已办理");
			vip=true;
			Manager.getInstance().getUser().setIs_vip(true);
		}
	}

	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, SettingsActivity.class);
	}

	private Toolbar toolbar;
	private ImageView avatarImageView;
	private TextView vipTextView;
	private TextView loggednameTextView;
	private Button logoutButton;
	private TextView mynameTextView;
	private TextView mymailTextView;
	private Switch nightmodeSwitch;
	private Switch nopicSwitch;
	private boolean vip;
	private boolean isNightMode = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.settings_activity);
		this.init();
		ConstraintLayout layout = this.findViewById(R.id.nickname_constraint_layout);

		layout.setOnClickListener(getListener("设置昵称",mynameTextView, Manager.getInstance().getUser()::setNickname));

		layout = this.findViewById(R.id.mail_constraint_layout);

		layout.setOnClickListener(getListener("设置邮箱",mymailTextView,Manager.getInstance().getUser()::setMail_address));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	
		switch (menuItem.getItemId()) {
			case android.R.id.home: 
				this.onGroupPressed();
				return true;
			default:
				return super.onOptionsItemSelected(menuItem);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.vip = Manager.getInstance().getUser().is_vip;
		avatarImageView.setImageDrawable(Manager.getInstance().getUser().avator);
		mynameTextView.setText(Manager.getInstance().getUser().nickname);
		mymailTextView.setText(Manager.getInstance().getUser().mail_address);
		loggednameTextView.setText(Manager.getInstance().getUser().username);
	}
	private View.OnClickListener getListener(String name, TextView s1, Consumer<String> s2){
		return new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this, R.style.MyDialogTheme);
				builder.setTitle(name);
				EditText input = new EditText(SettingsActivity.this);
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
	private void init() {



		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);
		
		// Configure Avatar component
		avatarImageView = this.findViewById(R.id.avatar_image_view);
		avatarImageView.setOnClickListener((view) -> {
	this.onAvatarPressed();
});

		
		// Configure GetVip component
		vipTextView = this.findViewById(R.id.vip_button);
		vipTextView.setOnClickListener((view) -> {
	this.onVipButtonPressed();
});
		
		// Configure LoggedName component
		loggednameTextView = this.findViewById(R.id.loggedname_text_view);
		
		// Configure Logout component
		logoutButton = this.findViewById(R.id.logout_button);
		logoutButton.setOnClickListener((view) -> {
	this.onLogoutPressed();
});
		mymailTextView = this.findViewById(R.id.mail_text_view);
		mynameTextView = this.findViewById(R.id.name_text_view);


		nopicSwitch = this.findViewById(R.id.nopicture_switch);
		if(Manager.getInstance().nopic)
			nopicSwitch.setChecked(true);
		else
			nopicSwitch.setChecked(false);
		nopicSwitch.setOnClickListener((view)->{
			this.onNopicChanged();
		});

		nightmodeSwitch = this.findViewById(R.id.nightmode_switch);
		int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
		if(currentMode==Configuration.UI_MODE_NIGHT_YES)
			nightmodeSwitch.setChecked(true);
		else
			nightmodeSwitch.setChecked(false);
		nightmodeSwitch.setOnClickListener((view)->{
			this.onNightModeChanged();
		});


		this.setupToolbar();
	}

	public void onNopicChanged(){
		boolean nopic = nopicSwitch.isChecked();
		Manager.getInstance().setNopic(nopic);
	}

	public boolean isNightMode(){
		int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
		if(currentMode==Configuration.UI_MODE_NIGHT_YES)
			return true;
		else
			return false;
	}


	public void onNightModeChanged(){
		UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
		if (!isNightMode()) {
			//uiManager.enableCarMode(0);
			uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
		} else {
			//uiManager.disableCarMode(0);
			uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
		}
	}


	public void setupToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		// Additional Toolbar setup code can go here.
	}

	public static final int PICK_IMAGE = 1;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_IMAGE) {
				Uri uri = data.getData();
				Uri desturi = Uri.fromFile(new File(getCacheDir(), "IMG_" + System.currentTimeMillis()));
				UCrop.of(uri, desturi)
						.withAspectRatio(1, 1)
						.start(this);
			}
		}
		System.out.println(resultCode);
		System.out.println(requestCode);
		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			final Uri uri = UCrop.getOutput(data);
			InputStream inputStream = null;
			try {
				inputStream = getContentResolver().openInputStream(uri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Drawable drawable = Drawable.createFromStream(inputStream, uri.toString() );
			avatarImageView.setImageDrawable(drawable);
			Manager.getInstance().getUser().setAvator(this,drawable);
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

	
	public void onVipButtonPressed() {
		if (!vip) {
			SettingsActivityVipButtonSheet settingsActivityVipButtonSheet = new SettingsActivityVipButtonSheet();
			settingsActivityVipButtonSheet.show(this.getSupportFragmentManager(), "SettingsActivityVipButtonSheet");

		}
	}

	
	public void onLogoutPressed() {
		Manager.getInstance().logout();
		this.startWelcomeActivity();
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}
	
	private void startWelcomeActivity() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
