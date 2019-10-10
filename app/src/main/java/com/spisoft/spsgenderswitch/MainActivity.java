package com.spisoft.spsgenderswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.spisoft.spsswitch.SpGenderSwitch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SpGenderSwitch Met = findViewById(R.id.met);
        Met.setChangeValueListener(new SpGenderSwitch.OnChangeValueListener() {
            public void onEvent() {
                //do whatever you want to do when the event is performed.
                Toast.makeText(MainActivity.this, Met.GetText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
