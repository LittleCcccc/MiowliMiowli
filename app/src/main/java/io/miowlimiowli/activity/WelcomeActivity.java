/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import io.miowlimiowli.R;
import android.animation.*;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import androidx.core.view.animation.PathInterpolatorCompat;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;


public class WelcomeActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, WelcomeActivity.class);
	}
	
	private ImageView logoImageView;
	private TextView titlechineseTextView;
	private TextView miowlimiowliNewsAppTextView;
	private LinearLayout signUpButton;
	private LinearLayout logInButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome_activity);
		this.init();
		
		this.startAnimationOne();
	}
	
	private void init() {
	
		// Configure logo component
		logoImageView = this.findViewById(R.id.logo_image_view);
		logoImageView.setOnClickListener((view) -> {
			this.onLogoPressed();});
		// Configure TitleChinese component
		titlechineseTextView = this.findViewById(R.id.titlechinese_text_view);
		
		// Configure Miowlimiowli News app component
		miowlimiowliNewsAppTextView = this.findViewById(R.id.miowlimiowli_news_app_text_view);
		
		// Configure Sign Up component
		signUpButton = this.findViewById(R.id.sign_up_button);
		signUpButton.setOnClickListener((view) -> {
	this.onSignUpPressed();
});
		
		// Configure Log In component
		logInButton = this.findViewById(R.id.log_in_button);
		logInButton.setOnClickListener((view) -> {
	this.onLogInPressed();
});
	}

	private void onLogoPressed() {
		startAnimationTwo();
	}

	public void onSignUpPressed() {
	
		this.startSignupActivity();
	}
	
	public void onLogInPressed() {
	
		this.startLoginActivity();
	}
	
	private void startLoginActivity() {
	
		this.startActivity(LoginActivity.newIntent(this));
	}
	
	private void startSignupActivity() {
	
		this.startActivity(SignupActivity.newIntent(this));
	}

	public void startAnimationTwo(){
		ObjectAnimator animator10 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.5f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
		animator10.setDuration(1000);
		animator10.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

		ObjectAnimator animator11 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.6f, 1f), Keyframe.ofFloat(1f, 1f)));
		animator11.setDuration(1000);
		animator11.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));


		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.2f, -375f),Keyframe.ofFloat(0.25f, -400f),Keyframe.ofFloat(0.3f, -350f), Keyframe.ofFloat(0.4f, -335f), Keyframe.ofFloat(0.6f, -363f),Keyframe.ofFloat(0.8f, -360f)));
		animator3.setDuration(1000);
		animator3.setInterpolator(PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f));

		AnimatorSet animatorSet1 = new AnimatorSet();
		animatorSet1.playTogether(animator10,animator11, animator3);
		animatorSet1.setTarget(logoImageView);

		animatorSet1.start();
	}

	public void startAnimationOne() {
	
		ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 300f, 0f));
		animator1.setDuration(850);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.29f, 0.87f, 1f, 1f));
		
		ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator2.setDuration(350);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f));
		
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(logoImageView, PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0f, -45f), Keyframe.ofFloat(0.7f, -45f), Keyframe.ofFloat(1f, 0f)));
		animator3.setDuration(1000);
		animator3.setInterpolator(PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f));
		
		AnimatorSet animatorSet1 = new AnimatorSet();
		animatorSet1.playTogether(animator1, animator2, animator3);
		animatorSet1.setTarget(logoImageView);
		
		ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(signUpButton, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator4.setDuration(1000);
		animator4.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(signUpButton, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator5.setDuration(1000);
		animator5.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet2 = new AnimatorSet();
		animatorSet2.playTogether(animator4, animator5);
		animatorSet2.setTarget(signUpButton);
		
		ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(logInButton, PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f));
		animator6.setDuration(1000);
		animator6.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		ObjectAnimator animator7 = ObjectAnimator.ofPropertyValuesHolder(logInButton, PropertyValuesHolder.ofFloat(View.SCALE_X, 0.3f, 1f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.3f, 1f));
		animator7.setDuration(1000);
		animator7.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));
		
		AnimatorSet animatorSet3 = new AnimatorSet();
		animatorSet3.playTogether(animator6, animator7);
		animatorSet3.setTarget(logInButton);
		
		ObjectAnimator animator8 = ObjectAnimator.ofPropertyValuesHolder(titlechineseTextView, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
		animator8.setDuration(1000);
		animator8.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		ObjectAnimator animator9 = ObjectAnimator.ofPropertyValuesHolder(titlechineseTextView, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.6f, 1f), Keyframe.ofFloat(1f, 1f)));
		animator9.setDuration(1000);
		animator9.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet animatorSet4 = new AnimatorSet();
		animatorSet4.playTogether(animator8, animator9);
		animatorSet4.setTarget(titlechineseTextView);
		
		ObjectAnimator animator10 = ObjectAnimator.ofPropertyValuesHolder(miowlimiowliNewsAppTextView, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
		animator10.setDuration(1000);
		animator10.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		ObjectAnimator animator11 = ObjectAnimator.ofPropertyValuesHolder(miowlimiowliNewsAppTextView, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.6f, 1f), Keyframe.ofFloat(1f, 1f)));
		animator11.setDuration(1000);
		animator11.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet animatorSet5 = new AnimatorSet();
		animatorSet5.playTogether(animator10, animator11);
		animatorSet5.setTarget(miowlimiowliNewsAppTextView);
		
		AnimatorSet animatorSet6 = new AnimatorSet();
		animatorSet6.playTogether(animatorSet1, animatorSet2, animatorSet3, animatorSet4, animatorSet5);
		animatorSet6.start();
	}
}
