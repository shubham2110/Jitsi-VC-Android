package com.example.ioc_vc_connect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText_server;
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.conferenceName);
        editText_server = findViewById(R.id.server_url);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    RelativeLayout container = findViewById(R.id.container1);
                    container.setVisibility(View.VISIBLE);
                } else {
              /*      RelativeLayout container = findViewById(R.id.container1);
                    container.setVisibility(View.GONE);*/
                }
            }
        });

        container = findViewById(R.id.top_container);
        container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    editText.clearFocus();
                }
            }
        });

        // Initialize default options for Jitsi Meet conferences.
        URL serverURL ;
        try {
           // serverURL = new URL("https://ioc.instantconnect.in");
            serverURL = new URL(editText_server.getText().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
    }

    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.conferenceName);
        String text = editText.getText().toString();

        if (text.length() > 0 && editText_server.getText().toString().length() > 0) {

            // Initialize default options for Jitsi Meet conferences.
            URL serverURL ;
            try {
                // serverURL = new URL("https://ioc.instantconnect.in");
                serverURL = new URL(editText_server.getText().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();

                throw new RuntimeException("Invalid server URL!");
            }
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setRoom(text)
                    .build();
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(this, options);
        }else{

            Snackbar snackbar = Snackbar
                    .make(container, "Enter valid meeting room name and server URL", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
