/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.Switch;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import io.miowlimiowli.R;
import android.widget.TextView;
import android.widget.ImageButton;
import android.os.Bundle;
import android.view.MenuItem;
import io.miowlimiowli.dialog.SettingsActivityVipButtonSheet;
import android.widget.ScrollView;
import android.view.MenuInflater;


public class SettingsActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, SettingsActivity.class);
	}
	
	private Toolbar toolbar;
	private Button avatarButton;
	private TextView detailsTextView;
	private ConstraintLayout nicknameConstraintLayout;
	private Button myNameButton;
	private ImageButton modifyNameButton;
	private ConstraintLayout mailConstraintLayout;
	private TextView mailTextView;
	private Button mymailButton;
	private ImageButton modifymailButton;
	private ConstraintLayout informationConstraintLayout;
	private Button clearcacheButton;
	private Button cacheButton;
	private TextView nopictureTextView;
	private Switch nopictureSwitch;
	private TextView darkmodeTextView;
	private Switch darkmodeSwitch;
	private Button vipButton;
	private TextView loggednameTextView;
	private Button logoutButton;
	private TextView informationTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.settings_activity);
		this.init();
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
	
		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);
		
		// Configure Avatar component
		avatarButton = this.findViewById(R.id.avatar_button);
		avatarButton.setOnClickListener((view) -> {
	this.onAvatarPressed();
});
		
		// Configure Details component
		detailsTextView = this.findViewById(R.id.details_text_view);
		
		// Configure Nickname component
		nicknameConstraintLayout = this.findViewById(R.id.nickname_constraint_layout);
		
		// Configure Myname component
		myNameButton = this.findViewById(R.id.my_name_button);
		myNameButton.setOnClickListener((view) -> {
	this.onMynamePressed();
});
		
		// Configure Modifyname component
		modifyNameButton = this.findViewById(R.id.modify_name_button);
		modifyNameButton.setOnClickListener((view) -> {
	this.onModifynamePressed();
});
		
		// Configure Mail component
		mailConstraintLayout = this.findViewById(R.id.mail_constraint_layout);
		
		// Configure Mail component
		mailTextView = this.findViewById(R.id.mail_text_view);
		
		// Configure Mymail component
		mymailButton = this.findViewById(R.id.mymail_button);
		mymailButton.setOnClickListener((view) -> {
	this.onMymailPressed();
});
		
		// Configure Modifymail component
		modifymailButton = this.findViewById(R.id.modifymail_button);
		modifymailButton.setOnClickListener((view) -> {
	this.onModifymailPressed();
});
		
		// Configure Information component
		informationConstraintLayout = this.findViewById(R.id.information_constraint_layout);
		
		// Configure Clear component
		clearcacheButton = this.findViewById(R.id.clearcache_button);
		clearcacheButton.setOnClickListener((view) -> {
	this.onClearPressed();
});
		
		// Configure Cache component
		cacheButton = this.findViewById(R.id.cache_button);
		cacheButton.setOnClickListener((view) -> {
	this.onCachePressed();
});
		
		// Configure Label component
		nopictureTextView = this.findViewById(R.id.nopicture_text_view);
		
		// Configure Slide component
		nopictureSwitch = this.findViewById(R.id.nopicture_switch);
		nopictureSwitch.setOnClickListener((view) -> {
	this.onNoPictureSlideValueChanged();
});
		
		// Configure Label component
		darkmodeTextView = this.findViewById(R.id.darkmode_text_view);
		
		// Configure Slide component
		darkmodeSwitch = this.findViewById(R.id.darkmode_switch);
		darkmodeSwitch.setOnClickListener((view) -> {
	this.onDarkSlideValueChanged();
});
		
		// Configure GetVip component
		vipButton = this.findViewById(R.id.vip_button);
		vipButton.setOnClickListener((view) -> {
	this.onVipButtonPressed();
});
		
		// Configure LoggedName component
		loggednameTextView = this.findViewById(R.id.loggedname_text_view);
		
		// Configure Logout component
		logoutButton = this.findViewById(R.id.logout_button);
		logoutButton.setOnClickListener((view) -> {
	this.onLogoutPressed();
});
		
		// Configure Information component
		informationTextView = this.findViewById(R.id.information_text_view);
		
		this.setupToolbar();
	}
	
	public void setupToolbar() {
	
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Additional Toolbar setup code can go here.
	}
	
	public void onAvatarPressed() {
	
	}
	
	public void onMynamePressed() {
	
	}
	
	public void onModifynamePressed() {
	
	}
	
	public void onMymailPressed() {
	
	}
	
	public void onModifymailPressed() {
	
	}
	
	public void onClearPressed() {
	
	}
	
	public void onCachePressed() {
	
	}
	
	public void onNoPictureSlideValueChanged() {
	
	}
	
	public void onDarkSlideValueChanged() {
	
	}
	
	public void onVipButtonPressed() {
	
		new SettingsActivityVipButtonSheet().show(this.getSupportFragmentManager(), "SettingsActivityVipButtonSheet");
	}
	
	public void onLogoutPressed() {
	
		this.startWelcomeActivity();
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}
	
	private void startWelcomeActivity() {
	
		this.startActivity(WelcomeActivity.newIntent(this));
	}
}
