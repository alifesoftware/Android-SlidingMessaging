package com.klinker.android.messaging_donate.floating_notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import com.klinker.android.messaging_donate.MainActivity;
import com.klinker.android.messaging_donate.R;
import com.klinker.android.messaging_donate.utils.ContactUtil;
import com.klinker.android.messaging_donate.utils.SendUtil;
import com.klinker.android.messaging_sliding.quick_reply.QmMarkRead;
import robj.floating.notifications.Extension;

public class FNAction extends BroadcastReceiver {

    public SharedPreferences sharedPrefs;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final long id = intent.getLongExtra(Extension.ID, -1);
        int action = intent.getIntExtra(Extension.ACTION, -1);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (intent.getAction().equals(Extension.INTENT)) {
            switch (action) {

                case 0:
                    // start main activity popup
                    Intent popup = new Intent(context, com.klinker.android.messaging_sliding.MainActivityPopup.class);
                    popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(popup);
                    if (sharedPrefs.getBoolean("auto_clear_fn", true)) {
                        FNReceiver.messages.remove(id);
                        Extension.remove(id, context);
                    } else {
                        FNReceiver.messages.clear();
                        FNReceiver.updateExt();
                    }
                    break;

                case 1:
                    try {
                        // create new reply overlay
                        String editTextHint;
                        try {
                            editTextHint = context.getResources().getString(R.string.reply_to) + " " + ContactUtil.findContactName(FNReceiver.messages.get(id)[0], context);
                        } catch (Exception e) {
                            // uhhh not sure what has happened here, but catch it lol
                            editTextHint = "";
                        }
                        final String previousText = FNReceiver.messages.get(id)[1];
                        final Bitmap image = ContactUtil.getFacebookPhoto(FNReceiver.messages.get(id)[0], context);
                        final Extension.onClickListener imageOnClick = new Extension.onClickListener() {
                            @Override
                            public void onClick() {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("com.klinker.android.OPEN_THREAD", FNReceiver.messages.get(id)[0]);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                FNReceiver.messages.remove(id);
                                Extension.remove(id, context);
                            }
                        };

                        final Extension.onClickListener sendOnClick = new Extension.onClickListener() {
                            @Override
                            public void onClick(String str) {
                                SendUtil.sendMessage(context, FNReceiver.messages.get(id)[0], str);
                                if (sharedPrefs.getBoolean("auto_clear_fn", true)) {
                                    FNReceiver.messages.remove(id);
                                    Extension.remove(id, context);
                                } else {
                                    FNReceiver.messages.clear();
                                    FNReceiver.updateExt();
                                }
                            }
                        };

                        final Bitmap extraButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.emo_im_smirk);

                        Extension.onClickListener extraOnClick = new Extension.onClickListener() {
                            @Override
                            public void onClick(final String str) {
                                Intent emojiDialog = new Intent(context, EmojiDialogActivity.class);
                                emojiDialog.putExtra("id", id);
                                emojiDialog.putExtra("message", str);
                                emojiDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Log.v("Extension", "starting emoji dialog");
                                context.startActivity(emojiDialog);
                            }
                        };

                        Extension.replyOverlay(editTextHint, previousText, image, imageOnClick, sendOnClick, extraOnClick, true, extraButton, context, false, "");
                        Extension.hideAll(id, context);
                    } catch (Exception e) {
                        // something went wrong with the ids i think...
                    }
                    break;

                case 2:
                    // start call intent
                    String address = FNReceiver.messages.get(id)[0];
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + address));
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                    Extension.remove(id, context);
                    FNReceiver.messages.remove(id);
                    break;

                case 3:
                    // start mark read service
                    context.startService(new Intent(context, QmMarkRead.class));
                    Extension.remove(id, context);
                    FNReceiver.messages.remove(id);
                    break;

            }
        }
    }
}
