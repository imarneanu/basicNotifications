package com.example.android.basicnotifications;

import android.app.Activity;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by imarneanu on 8/10/16.
 */
public class ReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle remoteReply = RemoteInput.getResultsFromIntent(intent);
            if (remoteReply != null) {
                CharSequence reply = remoteReply.getCharSequence(MainActivity.EXTRA_VOICE_REPLY);
                Toast.makeText(getApplicationContext(), "REPLIED:\n" + reply, Toast.LENGTH_LONG).show();
            }
        }
    }
}
