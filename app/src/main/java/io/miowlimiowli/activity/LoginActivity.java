/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.animation.*;
import android.graphics.Color;
import android.widget.EditText;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.core.view.animation.PathInterpolatorCompat;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import io.miowlimiowli.R;

import io.miowlimiowli.exceptions.UsernameorPasswordError;
import io.miowlimiowli.manager.Manager;

import android.content.Context;


public class LoginActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, LoginActivity.class);
	}
	
	private Toolbar toolbar;
	private TextView logInTextView;
	private TextView welcomeBackTextView;
	private EditText inputNicknameEditText;
	private EditText inputPasswordEditText;
	private LinearLayout loginButton;
	private TextView LoginErrorTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login_activity);
		this.init();
		
		this.startAnimationTwo();
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
	
	private void init() {
		LoginErrorTextView=this.findViewById(R.id.forgot_your_password_button);
		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);
		
		// Configure Log in component
		logInTextView = this.findViewById(R.id.log_in_text_view);
		
		// Configure Welcome back component
		welcomeBackTextView = this.findViewById(R.id.welcome_back_text_view);
		
		// Configure Your nickname component
		inputNicknameEditText = this.findViewById(R.id.input_nickname_edit_text);
		
		// Configure Your password component
		inputPasswordEditText = this.findViewById(R.id.input_password_edit_text);
		
		// Configure Login component
		loginButton = this.findViewById(R.id.login_button);
		loginButton.setOnClickListener((view) -> {
	this.onLoginPressed();
});

		inputNicknameEditText.setText("littlec");
		inputPasswordEditText.setText("qqqqqq");

		this.setupToolbar();
	}
	
	public void setupToolbar() {
	
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Additional Toolbar setup code can go here.
	}
	
	public void onLoginPressed() {

		String name = inputNicknameEditText.getText().toString();
		String password = inputPasswordEditText.getText().toString();
		try{
			Manager.getInstance().login(name,password);
			Intent intent = new Intent(this, TwoActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);

		}catch(UsernameorPasswordError e){
			LoginErrorTextView.setTextColor(Color.parseColor("#FF6D6B"));
		}
	}

	
	public void onGroupPressed() {
	
		this.finish();
	}

	
	public void startAnimationTwo() {
	
		ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(logInTextView, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator1.setDuration(500);
		animator1.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(logInTextView, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator2.setDuration(500);
		animator2.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet1 = new AnimatorSet();
		animatorSet1.playTogether(animator1, animator2);
		animatorSet1.setTarget(logInTextView);
		
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(welcomeBackTextView, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator3.setDuration(500);
		animator3.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(welcomeBackTextView, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator4.setDuration(500);
		animator4.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet2 = new AnimatorSet();
		animatorSet2.playTogether(animator3, animator4);
		animatorSet2.setTarget(welcomeBackTextView);
		
		ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(loginButton, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
		animator5.setDuration(1000);
		animator5.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(loginButton, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.6f, 1f), Keyframe.ofFloat(1f, 1f)));
		animator6.setDuration(1000);
		animator6.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet animatorSet3 = new AnimatorSet();
		animatorSet3.playTogether(animator5, animator6);
		animatorSet3.setTarget(loginButton);
		
		AnimatorSet animatorSet4 = new AnimatorSet();
		animatorSet4.playTogether(animatorSet1, animatorSet2, animatorSet3);
		animatorSet4.start();
	}
}
