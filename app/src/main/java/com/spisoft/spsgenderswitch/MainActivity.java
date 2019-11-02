package com.spisoft.spsgenderswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.spisoft.spsswitch.SpGenderSwitch;

public class MainActivity extends AppCompatActivity {

    int gn = -1;
    private String KEY_TEXT_VALUE = "mKeyInt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            int savedText = savedInstanceState.getInt(KEY_TEXT_VALUE);
            gn = savedText;
        }

        final SpGenderSwitch Met = findViewById(R.id.met);
        Met.SetConfirm(MainActivity.this,"TT","QQ","OK","Cancel");
        Met.SetValue(gn);
        Met.setChangeValueListener(new SpGenderSwitch.OnChangeValueListener() {
            public void onEvent() {
                //do whatever you want to do when the event is performed.
                gn = Met.GetValue();
                Toast.makeText(MainActivity.this, Met.GetText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TEXT_VALUE, gn);
    }
}
