package com.klinker.android.messaging_card.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.klinker.android.messaging_donate.R;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class CustomPopupActivity extends PreferenceActivity {
	public SharedPreferences sharedPrefs;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.custom_theme_settings);
		
		sharedPrefs  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		if (sharedPrefs.getBoolean("override_lang", false))
		{
			String languageToLoad  = "en";
		    Locale locale = new Locale(languageToLoad); 
		    Locale.setDefault(locale);
		    Configuration config = new Configuration();
		    config.locale = locale;
		    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
		} else
		{
			String languageToLoad = Resources.getSystem().getConfiguration().locale.getLanguage();
		    Locale locale = new Locale(languageToLoad); 
		    Locale.setDefault(locale);
		    Configuration config = new Configuration();
		    config.locale = locale;
		    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
		}
		
		final Context context = this;
		
		Preference saveTheme = (Preference) findPreference("save_theme");
		saveTheme.setOnPreferenceClickListener(new OnPreferenceClickListener() {
		             public boolean onPreferenceClick(Preference preference) {
		            	 String data = "";
		            	 data += sharedPrefs.getString("ct_theme_name", "Light Theme") + "\n";
		            	 data += sharedPrefs.getInt("ct_titleBarColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_titleBarTextColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_messageListBackground", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_sendbarBackground", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_sentMessageBackground", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_receivedMessageBackground", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_sentTextColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_receivedTextColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_conversationListBackground", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_nameTextColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_summaryTextColor", -1) + "\n";
		            	 data += sharedPrefs.getBoolean("ct_messageDividerVisibility", true) + "\n";
		            	 data += sharedPrefs.getInt("ct_messageDividerColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_sendButtonColor", -1) + "\n";
		            	 data += sharedPrefs.getBoolean("ct_darkContactImage", false) + "\n";
		            	 data += sharedPrefs.getInt("ct_messageCounterColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_draftTextColor", -1) + "\n";
		            	 data += sharedPrefs.getInt("ct_emojiButtonColor", -1) + "\n";
		        	   	 data += sharedPrefs.getInt("ct_conversationDividerColor", -1) + "\n";
                         data += sharedPrefs.getInt("ct_unreadConversationColor", -1);
		            	 
		            	 writeToFile(data, context, sharedPrefs.getString("ct_theme_name", "Light Theme").replace(" ", "") + ".theme");
		            	 
		            	 SharedPreferences.Editor editor = sharedPrefs.edit();
		 				 editor.putBoolean("custom_theme", true);
		 				 editor.commit();
		 				 
		 				 Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_theme_saved), Toast.LENGTH_LONG).show();
		            	 finish();
		                 return true;
		             }
		         });
		
		ColorPickerPreference sentBack = (ColorPickerPreference) findPreference("ct_sentMessageBackground");
		sentBack.setAlphaSliderEnabled(true);
		
		ColorPickerPreference receiveBack = (ColorPickerPreference) findPreference("ct_receivedMessageBackground");
		receiveBack.setAlphaSliderEnabled(true);
	}
	
	@Override
	public void onBackPressed() {
		 String data = "";
   	 	 data += sharedPrefs.getString("ct_theme_name", "Light Theme") + "\n";
	   	 data += sharedPrefs.getInt("ct_titleBarColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_titleBarTextColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_messageListBackground", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_sendbarBackground", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_sentMessageBackground", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_receivedMessageBackground", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_sentTextColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_receivedTextColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_conversationListBackground", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_nameTextColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_summaryTextColor", -1) + "\n";
	   	 data += sharedPrefs.getBoolean("ct_messageDividerVisibility", true) + "\n";
	   	 data += sharedPrefs.getInt("ct_messageDividerColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_sendButtonColor", -1) + "\n";
	   	 data += sharedPrefs.getBoolean("ct_darkContactImage", false) + "\n";
	   	 data += sharedPrefs.getInt("ct_messageCounterColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_draftTextColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_emojiButtonColor", -1) + "\n";
	   	 data += sharedPrefs.getInt("ct_conversationDividerColor", -1) + "\n";
         data += sharedPrefs.getInt("ct_unreadConversationColor", -1);
	   	 
	   	 writeToFile(data, this, sharedPrefs.getString("ct_theme_name", "Light Theme").replace(" ", "") + ".theme");
	   	 
	   	 SharedPreferences.Editor editor = sharedPrefs.edit();
		 editor.putBoolean("custom_theme", true);
		 editor.commit();
		 
		 Toast.makeText(getBaseContext(), "Theme Saved", Toast.LENGTH_LONG).show();
		 
		super.onBackPressed();
	}
	
	private void writeToFile(String data, Context context, String name) {
		String[] data2 = data.split("\n");
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SlidingMessaging", name);
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
}
