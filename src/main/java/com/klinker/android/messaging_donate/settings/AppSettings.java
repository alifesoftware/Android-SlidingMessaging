package com.klinker.android.messaging_donate.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.mms.util.DownloadManager;
import com.android.mms.util.RateController;
import com.klinker.android.messaging_donate.R;
import com.klinker.android.messaging_sliding.MenuArrayAdapter;
import com.klinker.android.messaging_sliding.MessageCursorAdapter;
import com.klinker.android.messaging_sliding.scheduled.scheduled.ScheduledDataSource;
import com.klinker.android.messaging_sliding.scheduled.scheduled.ScheduledMessage;

import java.io.*;
import java.util.ArrayList;

public class AppSettings {
    public static final int VIBRATE_ALWAYS = 0;
    public static final int VIBRATE_ONLY_MODE = 1;
    public static final int VIBRATE_NEVER = 2;

    public static final int DEFAULT_SEND_DELAY = 500;

    public boolean lightActionBar;
    public boolean limitConversationsAtStart;
    public boolean customFont;
    public boolean useTitleBar;
    public boolean alwaysShowContactInfo;
    public boolean titleContactImages;
    public boolean titleCaps;
    public boolean darkContactImage;
    public boolean showOriginalTimestamp;
    public boolean deliveryReports;
    public boolean stripUnicode;
    public boolean customTheme;
    public boolean emojiType;
    public boolean quickText;
    public boolean overrideLang;
    public boolean inAppNotifications;
    public boolean titleTextColor;
    public boolean enableDrafts;
    public boolean customBackground;
    public boolean messageDividerVisible;
    public boolean slideMessages;
    public boolean autoInsertDrafts;
    public boolean limitAttachmentSize;
    public boolean openContactMenu;
    public boolean cacheConversations;
    public boolean customBackground2;
    public boolean limitMessages;
    public boolean customVibratePattern;
    public boolean led;
    public boolean pageorMenu2;
    public boolean keyboardType;
    public boolean sendAsMMS;
    public boolean sendWithReturn;
    public boolean hideKeyboard;
    public boolean messageSounds;
    public boolean splitSMS;
    public boolean splitCounter;
    public boolean fullAppPopupClose;
    public boolean sendWithStock;
    public boolean emoji;
    public boolean mobileOnly;
    public boolean groupMessage;
    public boolean emojiKeyboard;
    public boolean voiceEnabled;
    public boolean closeHaloAfterSend;
    public boolean contactPictures2;
    public boolean ctDarkContactPics;
    public boolean hideMessageCounter;
    public boolean hideDate;
    public boolean smiliesType;
    public boolean hourFormat;
    public boolean contactPictures;
    public boolean tinyDate;
    public boolean alwaysUseVoice;
    public boolean boldNames;
    public boolean conversationListImages;
    public boolean systemEmojis;
    public String smilies;
    public String textSize;
    public String textSize2;
    public String runAs;
    public String signature;
    public String ringTone;
    public String deliveryOptions;
    public String customFontPath;
    public String pinType;
    public String securityOption;
    public String customBackgroundLocation;
    public String customBackground2Location;
    public String voiceThreads;
    public String vibratePattern;
    public String voiceAccount;
    public String sendingAnimation;
    public String receiveAnimation;
    public String themeName;
    public String sentTextColor;
    public String receivedTextColor;
    public String textAlignment;
    public int ctConversationListBackground;
    public int ctSentMessageBackground;
    public int ctMessageListBackground;
    public int ctConversationDividerColor;
    public int ctSendButtonColor;
    public int ctSendBarBackground;
    public int emojiButtonColor;
    public int draftTextColor;
    public int titleBarTextColor;
    public int titleBarColor;
    public int mmsAfter;
    public int ctNameTextColor;
    public int numOfCachedConversations;
    public int messageDividerColor;
    public int ctSummaryTextColor;
    public int ctMessageCounterColor;
    public int ctUnreadConversationColor;
    public int ctRecievedTextColor;
    public int ctSentTextColor;
    public int ctRecievedMessageBackground;
    public int animationSpeed;
    public int textOpacity;
    public int linkColor;
    public int mmsMaxWidth;
    public int mmsMaxHeight;
    public int vibrate;
    public int sendDelay;

    public static AppSettings init(final Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        DownloadManager.init(context);
        RateController.init(context);
        MessageCursorAdapter.dateFormatter = null;
        MenuArrayAdapter.dateFormatter = null;
        AppSettings settings = new AppSettings();

        if (sharedPrefs.getBoolean("override_speed", false)) {
            settings.limitConversationsAtStart = true;
            settings.customFont = false;
            settings.useTitleBar = true;
            settings.alwaysShowContactInfo = false;
            settings.titleContactImages = false;
            settings.titleCaps = false;
            settings.darkContactImage = false;
            settings.deliveryReports = false;
            settings.stripUnicode = false;
            settings.titleTextColor = false;
            settings.enableDrafts = false;
            settings.customBackground = false;
            settings.messageDividerVisible = false;
            settings.autoInsertDrafts = false;
            settings.cacheConversations = false;
            settings.customBackground2 = false;
            settings.limitMessages = true;
            settings.customVibratePattern = false;
            settings.customFontPath = null;
            settings.customBackgroundLocation = "";
            settings.customBackgroundLocation = "";
            settings.numOfCachedConversations = 5;
            settings.textSize = "14";
            settings.textSize2 = "14";
            settings.contactPictures = false;
            settings.conversationListImages = false;
            settings.systemEmojis = true;
        } else {
            settings.limitConversationsAtStart = sharedPrefs.getBoolean("limit_conversations_start", true);
            settings.customFont = sharedPrefs.getBoolean("custom_font", false);
            settings.useTitleBar = sharedPrefs.getBoolean("hide_title_bar", true);
            settings.alwaysShowContactInfo = sharedPrefs.getBoolean("always_show_contact_info", false);
            settings.titleContactImages = sharedPrefs.getBoolean("title_contact_image", false);
            settings.titleCaps = sharedPrefs.getBoolean("title_caps", true);
            settings.darkContactImage = sharedPrefs.getBoolean("ct_darkContactImage", false);
            settings.deliveryReports = sharedPrefs.getBoolean("delivery_reports", false);
            settings.stripUnicode = sharedPrefs.getBoolean("strip_unicode", false);
            settings.titleTextColor = sharedPrefs.getBoolean("title_text_color", false);
            settings.enableDrafts = sharedPrefs.getBoolean("enable_drafts", true);
            settings.customBackground = sharedPrefs.getBoolean("custom_background", false);
            settings.messageDividerVisible = sharedPrefs.getBoolean("ct_messageDividerVisibility", true);
            settings.autoInsertDrafts = sharedPrefs.getBoolean("auto_insert_draft", false);
            settings.cacheConversations = sharedPrefs.getBoolean("cache_conversations", false);
            settings.customBackground2 = sharedPrefs.getBoolean("custom_background2", false);
            settings.limitMessages = sharedPrefs.getBoolean("limit_messages", true);
            settings.customVibratePattern = sharedPrefs.getBoolean("custom_vibrate_pattern", false);
            settings.customFontPath = sharedPrefs.getString("custom_font_path", null);
            settings.customBackground2Location = sharedPrefs.getString("custom_background2_location", "");
            settings.customBackgroundLocation = sharedPrefs.getString("custom_background_location", "");
            settings.numOfCachedConversations = sharedPrefs.getInt("num_cache_conversations", 5);
            settings.textSize = sharedPrefs.getString("text_size", 14 + "");
            settings.textSize2 = sharedPrefs.getString("text_size2", 14 + "");
            settings.contactPictures = sharedPrefs.getBoolean("contact_pictures", true);
            settings.conversationListImages = sharedPrefs.getBoolean("conversation_list_images", true);
            settings.systemEmojis = sharedPrefs.getBoolean("use_system_emojis", context.getResources().getBoolean(R.bool.hasKitKat));
        }

        settings.lightActionBar = sharedPrefs.getBoolean("ct_light_action_bar", false);
        settings.runAs = sharedPrefs.getString("run_as", "sliding");
        settings.showOriginalTimestamp = sharedPrefs.getBoolean("show_original_timestamp", false);
        settings.customTheme = sharedPrefs.getBoolean("custom_theme", false);
        settings.emojiType = sharedPrefs.getBoolean("emoji_type", true);
        settings.quickText = sharedPrefs.getBoolean("quick_text", false);
        settings.overrideLang = sharedPrefs.getBoolean("override_lang", false);
        settings.inAppNotifications = sharedPrefs.getBoolean("in_app_notifications", true);
        settings.slideMessages = sharedPrefs.getBoolean("slide_messages", false);
        settings.limitAttachmentSize = sharedPrefs.getBoolean("limit_attachment_size", true);
        settings.openContactMenu = sharedPrefs.getBoolean("open_contact_menu", false);
        settings.vibrate = Integer.parseInt(sharedPrefs.getString("vibrate_mode", "0"));
        settings.led = sharedPrefs.getBoolean("led", true);
        settings.keyboardType = sharedPrefs.getBoolean("keyboard_type", true);
        settings.pageorMenu2 = sharedPrefs.getString("page_or_menu2", "2").equals("1");
        settings.sendAsMMS = sharedPrefs.getBoolean("send_as_mms", false);
        settings.sendWithReturn = sharedPrefs.getBoolean("send_with_return", false);
        settings.hideKeyboard = sharedPrefs.getBoolean("hide_keyboard", false);
        settings.messageSounds = sharedPrefs.getBoolean("message_sounds", false);
        settings.splitSMS = sharedPrefs.getBoolean("split_sms", false);
        settings.splitCounter = sharedPrefs.getBoolean("split_counter", false);
        settings.fullAppPopupClose = sharedPrefs.getBoolean("full_app_popup_close", false);
        settings.sendWithStock = sharedPrefs.getBoolean("send_with_stock", false);
        settings.emoji = sharedPrefs.getBoolean("emoji", false);
        settings.mobileOnly = sharedPrefs.getBoolean("mobile_only", false);
        settings.groupMessage = sharedPrefs.getBoolean("group_message", false);
        settings.boldNames = sharedPrefs.getBoolean("bold_conversations", false);
        settings.signature = sharedPrefs.getString("signature", "");
        settings.ringTone = sharedPrefs.getString("ringtone", "null");
        settings.deliveryOptions = sharedPrefs.getString("delivery_options", "2");
        settings.pinType = sharedPrefs.getString("pin_conversation_list", "1");
        settings.securityOption = sharedPrefs.getString("security_option", "none");
        settings.ctConversationListBackground = sharedPrefs.getInt("ct_conversationListBackground", context.getResources().getColor(R.color.light_silver));
        settings.ctSentMessageBackground = sharedPrefs.getInt("ct_sentMessageBackground", context.getResources().getColor(R.color.white));
        settings.ctMessageListBackground = sharedPrefs.getInt("ct_messageListBackground", context.getResources().getColor(R.color.light_silver));
        settings.ctConversationDividerColor = sharedPrefs.getInt("ct_conversationDividerColor", context.getResources().getColor(R.color.white));
        settings.ctSendButtonColor = sharedPrefs.getInt("ct_sendButtonColor", context.getResources().getColor(R.color.black));
        settings.ctSendBarBackground = sharedPrefs.getInt("ct_sendbarBackground", context.getResources().getColor(R.color.white));
        settings.emojiButtonColor = sharedPrefs.getInt("ct_emojiButtonColor", context.getResources().getColor(R.color.emoji_button));
        settings.draftTextColor = sharedPrefs.getInt("ct_draftTextColor", settings.ctSendButtonColor);
        settings.titleBarTextColor = sharedPrefs.getInt("ct_titleBarTextColor", context.getResources().getColor(R.color.white));
        settings.titleBarColor = sharedPrefs.getInt("ct_titleBarColor", context.getResources().getColor(R.color.holo_blue));
        settings.mmsAfter = sharedPrefs.getInt("mms_after", 4);
        settings.ctNameTextColor = sharedPrefs.getInt("ct_nameTextColor", context.getResources().getColor(R.color.black));
        settings.messageDividerColor = sharedPrefs.getInt("ct_messageDividerColor", context.getResources().getColor(R.color.light_silver));
        settings.emojiKeyboard = sharedPrefs.getBoolean("emoji_keyboard", true);
        settings.voiceAccount = sharedPrefs.getString("voice_account", null);
        settings.voiceEnabled = sharedPrefs.getBoolean("voice_enabled", false);
        settings.closeHaloAfterSend = sharedPrefs.getBoolean("close_halo_after_send", false);
        settings.voiceThreads = sharedPrefs.getString("voice_threads", "");
        settings.vibratePattern = sharedPrefs.getString("set_custom_vibrate_pattern", "0, 400, 100, 400");
        settings.ctSummaryTextColor = sharedPrefs.getInt("ct_summaryTextColor", context.getResources().getColor(R.color.black));
        settings.contactPictures2 = sharedPrefs.getBoolean("contact_pictures2", true);
        settings.ctDarkContactPics = sharedPrefs.getBoolean("ct_darkContactImage", false);
        settings.hideMessageCounter = sharedPrefs.getBoolean("hide_message_counter", false);
        settings.hideDate = sharedPrefs.getBoolean("hide_date_conversations", false);
        settings.ctMessageCounterColor = sharedPrefs.getInt("ct_messageCounterColor", context.getResources().getColor(R.color.messageCounterLight));
        settings.smilies = sharedPrefs.getString("smilies", "with");
        settings.smiliesType = sharedPrefs.getBoolean("smiliesType", true);
        settings.hourFormat = sharedPrefs.getBoolean("hour_format", false);
        settings.ctUnreadConversationColor = sharedPrefs.getInt("ct_unreadConversationColor", sharedPrefs.getInt("ct_receivedMessageBackground", context.getResources().getColor(R.color.white)));
        settings.tinyDate = sharedPrefs.getBoolean("tiny_date", false);
        settings.sendingAnimation = sharedPrefs.getString("send_animation", "left");
        settings.receiveAnimation = sharedPrefs.getString("receive_animation", "right");
        settings.themeName = sharedPrefs.getString("ct_theme_name", "Light Theme");
        settings.sentTextColor = sharedPrefs.getString("sent_text_color", "default");
        settings.receivedTextColor = sharedPrefs.getString("received_text_color", "default");
        settings.textAlignment = sharedPrefs.getString("text_alignment", "split");
        settings.ctRecievedTextColor = sharedPrefs.getInt("ct_receivedTextColor", context.getResources().getColor(R.color.black));
        settings.ctSentTextColor = sharedPrefs.getInt("ct_sentTextColor", context.getResources().getColor(R.color.black));
        settings.ctRecievedMessageBackground = sharedPrefs.getInt("ct_receivedMessageBackground", context.getResources().getColor(R.color.white));
        settings.animationSpeed = sharedPrefs.getInt("animation_speed", 300);
        settings.textOpacity = sharedPrefs.getInt("text_opacity", 100);
        settings.linkColor = sharedPrefs.getInt("hyper_link_color", context.getResources().getColor(R.color.holo_blue));
        settings.alwaysUseVoice = sharedPrefs.getBoolean("always_use_voice", false);
        settings.mmsMaxWidth = sharedPrefs.getInt("mms_max_width", 500);
        settings.mmsMaxHeight = sharedPrefs.getInt("mms_max_height", 500);
        settings.sendDelay = Integer.parseInt(sharedPrefs.getString("sms_send_delay", DEFAULT_SEND_DELAY + ""));

        if (settings.runAs.equals("card+")) {
            settings.customTheme = true;
            settings.ctRecievedMessageBackground = context.getResources().getColor(android.R.color.transparent);
        }

        if (sharedPrefs.getBoolean("redo_schedules", true)) {
            sharedPrefs.edit().putBoolean("redo_schedules", false).commit();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateScheduledDatabase(context);
                }
            }).start();
        }

        com.klinker.android.logger.Log.setDebug(true);
        com.klinker.android.logger.Log.setPath(context.getExternalCacheDir() + "/log.txt");

        return settings;
    }
    private static void updateScheduledDatabase(Context context) {
        ArrayList<String[]> messages = new ArrayList<String[]>();
        try {
            InputStream inputStream = context.openFileInput("scheduledSMS.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {

                    String[] details = new String[5];
                    details[0] = receiveString;

                    for (int i = 1; i < 5; i++)
                        details[i] = bufferedReader.readLine();

                    messages.add(details);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) { }

        ScheduledDataSource dataSource = new ScheduledDataSource(context);
        dataSource.open();

        for (String[] m : messages) {
            ScheduledMessage message = ScheduledMessage.fromOldStringArray(context, m);
            message = dataSource.addMessage(message);
            Log.v("SettingsUpdate", "added message to id " + message.id);
        }

        dataSource.close();
    }
}
