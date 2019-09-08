/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import io.miowlimiowli.R;
import io.miowlimiowli.exceptions.UsernameAlreadExistError;
import io.miowlimiowli.exceptions.UsernameEmptyError;
import io.miowlimiowli.manager.Manager;

import android.service.autofill.RegexValidator;
import android.view.MenuItem;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.animation.*;
import android.content.Intent;
import android.widget.Switch;
import android.content.Context;
import android.widget.EditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, SignupActivity.class);
	}
	
	private Toolbar toolbar;
	private TextView signUpTextView;
	private TextView welcomeTextView;
	private EditText signupUsernameEditText;
	private EditText signupMailEditText;
	private EditText signupPasswordEditText;
	private Switch agreeSwitch;
	private LinearLayout signUpButton;
	private Button logInButton;
	private TextView alert;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.signup_activity);
		this.init();
		
		this.startAnimationOne();
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

		alert = this.findViewById(R.id.alert);
		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);
		
		// Configure Sign up component
		signUpTextView = this.findViewById(R.id.sign_up_text_view);
		
		// Configure Welcome component
		welcomeTextView = this.findViewById(R.id.welcome_text_view);
		
		// Configure Your username component
		signupUsernameEditText = this.findViewById(R.id.signup_username_edit_text);
		
		// Configure Your spacemail component
		signupMailEditText = this.findViewById(R.id.signup_mail_edit_text);
		
		// Configure Password component
		signupPasswordEditText = this.findViewById(R.id.signup_password_edit_text);
		
		// Configure switch component
		agreeSwitch = this.findViewById(R.id.agree_switch);
		agreeSwitch.setOnClickListener((view) -> {
	this.onAgreeSwitchValueChanged();
});

		
		// Configure Sign Up component
		signUpButton = this.findViewById(R.id.sign_up_button);
		signUpButton.setOnClickListener((view) -> {
	this.onSignUpPressed();
});
		
		// Configure Log In! component
		logInButton = this.findViewById(R.id.log_in_button);
		logInButton.setOnClickListener((view) -> {
	this.onLogInPressed();
});


		// default user
		signupUsernameEditText.setText("littlec");
		signupMailEditText.setText("tyc16@qq.com");
		signupPasswordEditText.setText("qqqqqq");
		agreeSwitch.setChecked(true);

		this.setupToolbar();
	}
	
	public void setupToolbar() {
	
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Additional Toolbar setup code can go here.
	}
	
	public void onAgreeSwitchValueChanged() {
	}
	
	public void onSignUpPressed() {
		if(!agreeSwitch.isChecked()){
			alert.setText("请同意条款");
			return;
		}
		if(signupPasswordEditText.length() < 6){
			alert.setText("密码长度过短");
			return;
		}

		Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		if(!pattern.matcher(signupMailEditText.getText()).matches()){
			alert.setText("邮箱格式错误");
			return;
		}
		String name = signupUsernameEditText.getText().toString();
		String mail = signupMailEditText.getText().toString();
		String pw = signupPasswordEditText.getText().toString();
		try{
			Manager.getInstance().register(name,mail, pw);
		}catch(UsernameAlreadExistError e)
		{
			alert.setText("用户名已经存在");
			return;
		}catch (UsernameEmptyError e){
			alert.setText("username empty");
			return;
		}
		try {
			Manager.getInstance().login(name, pw);
		}catch(Exception e)
		{

		}
		Intent intent = new Intent(this, TwoActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
	
	public void onLogInPressed() {

		this.startLoginActivity();
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}

	
	private void startLoginActivity() {
	
		this.startActivity(LoginActivity.newIntent(this));
	}
	
	public void startAnimationOne() {
	
		ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(signUpTextView, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(signUpTextView, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet1 = new AnimatorSet();
		animatorSet1.playTogether(animator1, animator2);
		animatorSet1.setTarget(signUpTextView);
		
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(welcomeTextView, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator3.setDuration(1000);
		animator3.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(welcomeTextView, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator4.setDuration(1000);
		animator4.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet2 = new AnimatorSet();
		animatorSet2.playTogether(animator3, animator4);
		animatorSet2.setTarget(welcomeTextView);
		
		ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(signUpButton, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator5.setDuration(1000);
		animator5.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(signUpButton, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator6.setDuration(1000);
		animator6.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet3 = new AnimatorSet();
		animatorSet3.playTogether(animator5, animator6);
		animatorSet3.setTarget(signUpButton);
		
		AnimatorSet animatorSet4 = new AnimatorSet();
		animatorSet4.playTogether(animatorSet1, animatorSet2, animatorSet3);
		animatorSet4.start();
	}
}
