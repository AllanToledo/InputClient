package com.allantoledo.remoteinputclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FullscreenActivity extends AppCompatActivity {

    InputController controller;
    private final String KEY = "ipRemotePreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        controller = new InputController();

        Button connectButton = findViewById(R.id.connect_button);
        EditText inputAddress = findViewById(R.id.input_address);
        View touchpad = findViewById(R.id.touchpad);
        Button leftClick = findViewById(R.id.left_mouse);
        Button rightClick = findViewById(R.id.right_mouse);
        View scrollbar = findViewById(R.id.scrollbar);


        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        inputAddress.setText(preferences.getString(KEY, ""));

        rightClick.setOnClickListener(v -> controller.setRightClick());
        leftClick.setOnClickListener(v -> controller.setLeftClick());

        connectButton.setOnClickListener(v -> {
            inputAddress.clearFocus();
            try {
                String ip = inputAddress.getText().toString();
                controller.setAddress(InetAddress.getByName(ip), 3000);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY, ip);
                editor.apply();
                Toast.makeText(FullscreenActivity.this.getApplicationContext(), ip, Toast.LENGTH_SHORT).show();
            } catch (UnknownHostException e) {
                Toast.makeText(FullscreenActivity.this.getApplicationContext(), "Invalid ip", Toast.LENGTH_SHORT).show();
            }
        });

        scrollbar.setOnTouchListener((v, event) -> {
            String DEBUG_TAG = "MOTIONEVENT";
            int action = event.getAction();
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    Log.v(DEBUG_TAG, "Action was DOWN");
                    controller.setFirstScrollTouch((int) event.getY());
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Log.v(DEBUG_TAG, "Action was MOVE");
                    controller.moveScroll((int) event.getY());
                    return true;
                case (MotionEvent.ACTION_UP):
                    Log.v(DEBUG_TAG, "Action was UP");
                    controller.setLastScrollTouch();
                    v.performClick();
                    return true;
                case (MotionEvent.ACTION_CANCEL):
                    Log.v(DEBUG_TAG, "Action was CANCEL");
                    controller.setLastScrollTouch();
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    Log.v(DEBUG_TAG, "Movement occurred outside bounds " +
                            "of current screen element");
                    controller.setLastScrollTouch();
                    return true;
                default:
                    controller.setLastScrollTouch();
                    return true;
            }
        });
        touchpad.setOnTouchListener((v, event) -> {
            String DEBUG_TAG = "MOTIONEVENT";
            int action = event.getAction();
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    Log.v(DEBUG_TAG, "Action was DOWN");
                    controller.setFirstTouch((int) event.getX(), (int) event.getY());
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Log.v(DEBUG_TAG, "Action was MOVE");
                    controller.moveTouch((int) event.getX(), (int) event.getY());
                    return true;
                case (MotionEvent.ACTION_UP):
                    Log.v(DEBUG_TAG, "Action was UP");
                    controller.setLastTouch((int) event.getX(), (int) event.getY());
                    v.performClick();
                    return true;
                case (MotionEvent.ACTION_CANCEL):
                    Log.v(DEBUG_TAG, "Action was CANCEL");
                    controller.setLastTouch((int) event.getX(), (int) event.getY());
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    Log.v(DEBUG_TAG, "Movement occurred outside bounds " +
                            "of current screen element");
                    controller.setLastTouch((int) event.getX(), (int) event.getY());
                    return true;
                default:
                    return true;
            }
        });

        mapKeys();
    }

    private void mapKeys() {
        Button a_button = findViewById(R.id.a_button);
        Button b_button = findViewById(R.id.b_button);
        Button c_button = findViewById(R.id.c_button);
        Button d_button = findViewById(R.id.d_button);
        Button e_button = findViewById(R.id.e_button);
        Button f_button = findViewById(R.id.f_button);
        Button g_button = findViewById(R.id.g_button);
        Button h_button = findViewById(R.id.h_button);
        Button i_button = findViewById(R.id.i_button);
        Button j_button = findViewById(R.id.j_button);
        Button k_button = findViewById(R.id.k_button);
        Button l_button = findViewById(R.id.l_button);
        Button n_button = findViewById(R.id.n_button);
        Button o_button = findViewById(R.id.o_button);
        Button m_button = findViewById(R.id.m_button);
        Button p_button = findViewById(R.id.p_button);
        Button q_button = findViewById(R.id.q_button);
        Button r_button = findViewById(R.id.r_button);
        Button s_button = findViewById(R.id.s_button);
        Button t_button = findViewById(R.id.t_button);
        Button u_button = findViewById(R.id.u_button);
        Button v_button = findViewById(R.id.v_button);
        Button w_button = findViewById(R.id.w_button);
        Button x_button = findViewById(R.id.x_button);
        Button y_button = findViewById(R.id.y_button);
        Button z_button = findViewById(R.id.z_button);
        Button n1_button = findViewById(R.id.n1_button);
        Button n2_button = findViewById(R.id.n2_button);
        Button n3_button = findViewById(R.id.n3_button);
        Button n4_button = findViewById(R.id.n4_button);
        Button n5_button = findViewById(R.id.n5_button);
        Button n6_button = findViewById(R.id.n6_button);
        Button n7_button = findViewById(R.id.n7_button);
        Button n8_button = findViewById(R.id.n8_button);
        Button n9_button = findViewById(R.id.n9_button);
        Button n0_button = findViewById(R.id.n0_button);
        n1_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x31));
        n2_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x32));
        n3_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x33));
        n4_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x34));
        n5_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x35));
        n6_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x36));
        n7_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x37));
        n8_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x38));
        n9_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x39));
        n0_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x30));
        a_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x41));
        b_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x42));
        c_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x43));
        d_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x44));
        e_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x45));
        f_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x46));
        g_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x47));
        h_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x48));
        i_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x49));
        j_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4a));
        k_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4b));
        l_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4c));
        m_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4d));
        n_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4e));
        o_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x4f));
        p_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x50));
        q_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x51));
        r_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x52));
        s_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x53));
        t_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x54));
        u_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x55));
        v_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x56));
        w_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x57));
        x_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x58));
        y_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x59));
        z_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x5A));

        Button windows_button = findViewById(R.id.windows_button);
        windows_button.setOnClickListener(v -> controller.setKeyPressed((byte) -1));

        Button volume_up = findViewById(R.id.volume_up);
        volume_up.setOnClickListener(v -> controller.setKeyPressed((byte) -3));

        Button volume_down = findViewById(R.id.volume_down);
        volume_down.setOnClickListener(v -> controller.setKeyPressed((byte) -4));

        Button space_button = findViewById(R.id.space_button);
        space_button.setOnClickListener(v -> controller.setKeyPressed((byte) 0x20));

        Button enter_button = findViewById(R.id.enter_button);
        enter_button.setOnClickListener(v -> controller.setKeyPressed((byte) '\n'));

        Button backspace_button = findViewById(R.id.back_button);
        backspace_button.setOnClickListener(v -> controller.setKeyPressed((byte) '\b'));
    }

}