package com.klinker.android.messaging_card.theme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import com.klinker.android.messaging_donate.R;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class PopupChooserActivity extends Activity {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	public SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link android.support.v4.view.ViewPager} that will host the section contents.
	 */
	public ViewPager mViewPager;
	public SharedPreferences sharedPrefs;
	public static ArrayList<CustomPopup> themes;
	public static int NUMBER_DEFAULT_THEMES = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		setContentView(R.layout.activity_theme);

		if (sharedPrefs.getBoolean("override_lang", false))
		{
			String languageToLoad  = "en";
		    Locale locale = new Locale(languageToLoad);
		    Locale.setDefault(locale);
		    Configuration config = new Configuration();
		    config.locale = locale;
		    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
		}
	}

	public void refreshThemes()
	{
		themes = new ArrayList<CustomPopup>();

		themes.add(new CustomPopup("White", this));
		themes.add(new CustomPopup("Dark", this));

		String root = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File(root + "/SlidingMessaging/Popup");
		file.mkdir();

		File list[] = file.listFiles();
		ArrayList<File> files = new ArrayList<File>();

		if (list != null)
		{
			for (int i = 0; i < list.length; i++)
			{
				if (list[i].toString().endsWith(".theme"))
				{
					files.add(list[i]);
				}
			}
		}

		for (int i = 0; i < files.size(); i++)
		{
			String data = readFromFile(this, files.get(i).getName());
			themes.add(CustomPopup.themeFromString(data));
		}

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		for (int i = 0; i < themes.size(); i++)
		{
			if (sharedPrefs.getString("cp_theme_name", "Light Theme").equals(themes.get(i).name))
			{
				mViewPager.setCurrentItem(i, true);
				break;
			}
		}

		Button select = (Button) findViewById(R.id.selectButton);

		final Context context = this;

		select.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(mViewPager.getCurrentItem() <= 1)
				{
					saveSettings(true);
					finish();
				} else if (mViewPager.getCurrentItem() >= 2)
				{
					try
	            	 {
	            		 PackageManager pm = context.getPackageManager();
	            		 pm.getPackageInfo("com.klinker.android.messaging_donate", PackageManager.GET_ACTIVITIES);
	            		 saveSettings(true);
	            		 finish();
	            	 } catch (PackageManager.NameNotFoundException e)
	            	 {
	            		 try
		            	 {
		            		 PackageManager pm = context.getPackageManager();
		            		 pm.getPackageInfo("com.klinker.android.messaging_theme", PackageManager.GET_ACTIVITIES);
		            		 saveSettings(true);
		            		 finish();
		            	 } catch (PackageManager.NameNotFoundException f)
		            	 {
		            		 Intent intent = new Intent(Intent.ACTION_VIEW);
		            		 intent.setData(Uri.parse("market://details?id=com.klinker.android.messaging_theme"));
			            	 startActivity(intent);
		            	 }
	            	 }
				} else
				{
					try
	            	 {
	            		 PackageManager pm = context.getPackageManager();
	            		 pm.getPackageInfo("com.klinker.android.messaging_theme", PackageManager.GET_ACTIVITIES);
	            		 saveSettings(true);
	            		 finish();
	            	 } catch (PackageManager.NameNotFoundException e)
	            	 {
	            		 Intent intent = new Intent(Intent.ACTION_VIEW);
	            		 intent.setData(Uri.parse("market://details?id=com.klinker.android.messaging_theme"));
	            		 startActivity(intent);
	            	 }
				}
			}
		});
	}

	public void saveSettings(boolean toast)
	{
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("custom_theme", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).custom);
		editor.putString("ct_theme_name", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).name);
		editor.putInt("ct_titleBarColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).titleBarColor);
		editor.putInt("ct_titleBarTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).titleBarTextColor);
		editor.putInt("ct_messageListBackground", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).messageListBackground);
		editor.putInt("ct_sendbarBackground", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).sendbarBackground);
		editor.putInt("ct_sentMessageBackground", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).sentMessageBackground);
		editor.putInt("ct_receivedMessageBackground", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).receivedMessageBackground);
		editor.putInt("ct_sentTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).sentTextColor);
		editor.putInt("ct_receivedTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).receivedTextColor);
		editor.putInt("ct_conversationListBackground", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).conversationListBackground);
		editor.putInt("ct_nameTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).nameTextColor);
		editor.putInt("ct_summaryTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).summaryTextColor);
		editor.putBoolean("ct_messageDividerVisibility", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).messageDividerVisibility);
		editor.putInt("ct_messageDividerColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).messageDividerColor);
		editor.putInt("ct_sendButtonColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).sendButtonColor);
		editor.putBoolean("ct_darkContactImage", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).darkContactImage);
		editor.putInt("ct_messageCounterColor", CustomPopup.convertToColorInt(PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).messageCounterColor));
		editor.putInt("ct_draftTextColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).draftTextColor);
		editor.putInt("ct_emojiButtonColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).emojiButtonColor);
		editor.putInt("ct_conversationDividerColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).conversationDividerColor);
        editor.putInt("ct_unreadConversationColor", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).unreadConversationColor);
		editor.commit();

		if (toast)
			Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_theme_saved), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		refreshThemes();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_popup_theme, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_add_theme:
	    	try
	       	 {
	       		 PackageManager pm = this.getPackageManager();
	       		 pm.getPackageInfo("com.klinker.android.messaging_theme", PackageManager.GET_ACTIVITIES);

	       		SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.putString("cp_theme_name", "My Custom Popup Theme");
				editor.commit();

		    	Intent intent = new Intent(this, CustomPopupActivity.class);
		    	startActivity(intent);
	       	 } catch (PackageManager.NameNotFoundException e)
	       	 {
	       		 Intent intent = new Intent(Intent.ACTION_VIEW);
	       		 intent.setData(Uri.parse("market://details?id=com.klinker.android.messaging_theme"));
	       		 startActivity(intent);
	       	 }

	        return true;
	    case R.id.menu_edit_theme:

		    	try
		       	 {
		       		 PackageManager pm = this.getPackageManager();
		       		 pm.getPackageInfo("com.klinker.android.messaging_theme", PackageManager.GET_ACTIVITIES);

		       		 saveSettings(false);

		       		 if (mViewPager.getCurrentItem() < NUMBER_DEFAULT_THEMES)
		       		 {
			       		SharedPreferences.Editor editor = sharedPrefs.edit();
			    		editor.putString("cp_theme_name", PopupChooserActivity.themes.get(mViewPager.getCurrentItem()).name + " 2");
			    		editor.commit();
		       		 }

			    	 Intent intent2 = new Intent(this, CustomPopupActivity.class);
			    	 startActivity(intent2);
		       	 } catch (PackageManager.NameNotFoundException e)
		       	 {
		       		 Intent intent = new Intent(Intent.ACTION_VIEW);
		       		 intent.setData(Uri.parse("market://details?id=com.klinker.android.messaging_theme"));
		       		 startActivity(intent);
		       	 }

	    	return true;
	    case R.id.menu_delete_theme:
	    	if (mViewPager.getCurrentItem() < NUMBER_DEFAULT_THEMES)
	    	{
	    		Toast.makeText(this, getResources().getString(R.string.cannot_delete), Toast.LENGTH_SHORT).show();
	    	} else
	    	{
	    		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SlidingMessaging/" + themes.get(mViewPager.getCurrentItem()).name.replace(" ", "") + ".theme");
	    		file.delete();

	    		int position = mViewPager.getCurrentItem();
	    		refreshThemes();

	    		try
	    		{
	    			mViewPager.setCurrentItem(position);
	    		} catch (Exception e)
	    		{
	    			mViewPager.setCurrentItem(position - 1);
	    		}
	    	}

			return true;
	    case R.id.menu_get_more_themes:
	    	 Intent intent = new Intent(Intent.ACTION_VIEW);
      		 intent.setData(Uri.parse("http://forum.xda-developers.com/showthread.php?p=39533859#post39533859"));
      		 startActivity(intent);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	private String readFromFile(Context context, String fileName) {

	      String ret = "";

	      try {
	    	  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SlidingMessaging/Popup", fileName);
	          @SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));

	          String s = "";

	          while ((s = reader.readLine()) != null)
	          {
	        	  ret += s + "\n";
	          }

	      }
	      catch (FileNotFoundException e) {

			} catch (IOException e) {

			}

	      return ret;
		}

	@SuppressWarnings("unused")
	private void writeToFile(String data, Context context, String name) {
		String[] data2 = data.split("\n");
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SlidingMessaging/Popup", name);
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            for (int i = 0; i < data2.length; i++)
            {
            	pw.println(data2[i]);
            }

            pw.flush();
            pw.close();
            f.close();
        }
        catch (Exception e) {

        }

	}

	/**
	 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {

		public SectionsPagerAdapter(android.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public DummySectionFragment getItem(int position) {
			DummySectionFragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt("position", position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return PopupChooserActivity.themes.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return PopupChooserActivity.themes.get(position).name;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends android.app.Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public int position;
		private View view;
		public SharedPreferences sharedPrefs;

		public DummySectionFragment() {

		}

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			Bundle args = getArguments();

			this.position = args.getInt("position");

			view = inflater.inflate(R.layout.theme_preview, container, false);
			sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

			return refreshTheme();
		}

		public View refreshTheme()
		{
			TextView receivedMessageText = (TextView) view.findViewById(R.id.receivedMessage);
			TextView sentMessageText = (TextView) view.findViewById(R.id.sentMessage);
			ImageButton emojiButton = (ImageButton) view.findViewById(R.id.display_emoji);
			EditText messageEntry = (EditText) view.findViewById(R.id.messageEntry);
			TextView titleBar = (TextView) view.findViewById(R.id.contactNamePreview);
			View headerPadding = view.findViewById(R.id.headerPadding);
			View footerPadding = view.findViewById(R.id.footerPadding);
			View sendbar = view.findViewById(R.id.view1);
			View sentBackground = view.findViewById(R.id.sentBackground);
			View receivedBackground = view.findViewById(R.id.receiveBackground);
			View sentBackgroundBack = view.findViewById(R.id.sentBackgroundBack);
			View receivedBackgroundBack = view.findViewById(R.id.receiveBackgroundBack);
			TextView sentDate = (TextView) view.findViewById(R.id.sentDate);
			TextView receiveDate = (TextView) view.findViewById(R.id.receivedDate);
			View messageDivider = view.findViewById(R.id.messageDivider);
			ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendButton);
			ImageView sentContactPicture = (ImageView) view.findViewById(R.id.sentContactPicture1);
			ImageView receiveContactPicture = (ImageView) view.findViewById(R.id.receivedContactPicture1);

			receivedMessageText.setTextSize(Integer.parseInt(sharedPrefs.getString("text_size", "14").substring(0,2)));
			sentMessageText.setTextSize(Integer.parseInt(sharedPrefs.getString("text_size", "14").substring(0,2)));

			if (!sharedPrefs.getBoolean("emoji", false))
			{
				emojiButton.setVisibility(View.GONE);
				LayoutParams params = (LayoutParams) messageEntry.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				messageEntry.setLayoutParams(params);
			}
			
			titleBar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			titleBar.setBackgroundColor(PopupChooserActivity.themes.get(position).titleBarColor);
			titleBar.setTextColor(PopupChooserActivity.themes.get(position).titleBarTextColor);
			headerPadding.setBackgroundColor(PopupChooserActivity.themes.get(position).messageListBackground);
			footerPadding.setBackgroundColor(PopupChooserActivity.themes.get(position).messageListBackground);
			sendbar.setBackgroundColor(PopupChooserActivity.themes.get(position).sendbarBackground);
			sentBackground.setBackgroundColor(PopupChooserActivity.themes.get(position).sentMessageBackground);
			receivedBackground.setBackgroundColor(PopupChooserActivity.themes.get(position).receivedMessageBackground);
			sentBackgroundBack.setBackgroundColor(PopupChooserActivity.themes.get(position).messageListBackground);
			receivedBackgroundBack.setBackgroundColor(PopupChooserActivity.themes.get(position).messageListBackground);
			sentMessageText.setTextColor(PopupChooserActivity.themes.get(position).sentTextColor);
			sentDate.setTextColor(PopupChooserActivity.themes.get(position).sentTextColor);
			receivedMessageText.setTextColor(PopupChooserActivity.themes.get(position).receivedTextColor);
			receiveDate.setTextColor(PopupChooserActivity.themes.get(position).receivedTextColor);
			sendButton.setColorFilter(PopupChooserActivity.themes.get(position).sendButtonColor);
			emojiButton.setColorFilter(PopupChooserActivity.themes.get(position).emojiButtonColor);
			
			if (PopupChooserActivity.themes.get(position).messageDividerVisibility)
			{
				messageDivider.setBackgroundColor(PopupChooserActivity.themes.get(position).messageDividerColor);
			} else
			{
				messageDivider.setBackgroundColor(PopupChooserActivity.themes.get(position).sentMessageBackground);
			}
			
			if (PopupChooserActivity.themes.get(position).darkContactImage)
			{
				sentContactPicture.setImageResource(R.drawable.ic_contact_dark);
				receiveContactPicture.setImageResource(R.drawable.ic_contact_dark);
			}
			
			if (!PopupChooserActivity.themes.get(position).custom)
			{
				String titleColor = sharedPrefs.getString("title_color", "blue");
				String color = sharedPrefs.getString("sent_text_color", "default");
				
				if (titleColor.equals("blue"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.holo_blue));
				} else if (titleColor.equals("orange"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.holo_orange));
				} else if (titleColor.equals("red"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.holo_red));
				} else if (titleColor.equals("green"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.holo_green));
				} else if (titleColor.equals("purple"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.holo_purple));
				} else if (titleColor.equals("grey"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.grey));
				} else if (titleColor.equals("black"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.pitch_black));
				} else if (titleColor.equals("darkgrey"))
				{
					titleBar.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				}
				  
				  if (color.equals("blue"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.holo_blue));
					  sentDate.setTextColor(getResources().getColor(R.color.holo_blue));
				  } else if (color.equals("white"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.white));
					  sentDate.setTextColor(getResources().getColor(R.color.white));
				  } else if (color.equals("green"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.holo_green));
					  sentDate.setTextColor(getResources().getColor(R.color.holo_green));
				  } else if (color.equals("orange"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.holo_orange));
					  sentDate.setTextColor(getResources().getColor(R.color.holo_orange));
				  } else if (color.equals("red"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.holo_red));
					  sentDate.setTextColor(getResources().getColor(R.color.holo_red));
				  } else if (color.equals("purple"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.holo_purple));
					  sentDate.setTextColor(getResources().getColor(R.color.holo_purple));
				  } else if (color.equals("black"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.pitch_black));
					  sentDate.setTextColor(getResources().getColor(R.color.pitch_black));
				  } else if (color.equals("grey"))
				  {
					  sentMessageText.setTextColor(getResources().getColor(R.color.grey));
					  sentDate.setTextColor(getResources().getColor(R.color.grey));
				  }
				  
				  color = sharedPrefs.getString("received_text_color", "default");
				  
				  if (color.equals("blue"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.holo_blue));
					  receiveDate.setTextColor(getResources().getColor(R.color.holo_blue));
				  } else if (color.equals("white"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.white));
					  receiveDate.setTextColor(getResources().getColor(R.color.white));
				  } else if (color.equals("green"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.holo_green));
					  receiveDate.setTextColor(getResources().getColor(R.color.holo_green));
				  } else if (color.equals("orange"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.holo_orange));
					  receiveDate.setTextColor(getResources().getColor(R.color.holo_orange));
				  } else if (color.equals("red"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.holo_red));
					  receiveDate.setTextColor(getResources().getColor(R.color.holo_red));
				  } else if (color.equals("purple"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.holo_purple));
					  receiveDate.setTextColor(getResources().getColor(R.color.holo_purple));
				  } else if (color.equals("black"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.pitch_black));
					  receiveDate.setTextColor(getResources().getColor(R.color.pitch_black));
				  } else if (color.equals("grey"))
				  {
					  receivedMessageText.setTextColor(getResources().getColor(R.color.grey));
					  receiveDate.setTextColor(getResources().getColor(R.color.grey));
				  }
				  
				  if (sharedPrefs.getBoolean("title_text_color", false))
				  {
						titleBar.setTextColor(getActivity().getResources().getColor(R.color.black));
				  }
			}
			
			if (!sharedPrefs.getBoolean("hide_title_bar", true))
			{
				titleBar.setVisibility(View.GONE);
			}
			
			if (sharedPrefs.getBoolean("title_caps", true))
			{
				titleBar.setText(getActivity().getResources().getString(R.string.ct_contact_name).toUpperCase());
				titleBar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			}
			
			if (sharedPrefs.getBoolean("custom_font", false))
			{
				Typeface font = Typeface.createFromFile(sharedPrefs.getString("custom_font_path", ""));
				
				sentMessageText.setTypeface(font);
				receivedMessageText.setTypeface(font);
				sentDate.setTypeface(font);
				receiveDate.setTypeface(font);
				messageEntry.setTypeface(font);
			}
			
			return view;
		}
	}
}
