package com.example.smartpetrolcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MaterialToolbar toolbar = findViewById(R.id.toolbarAbout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvGithub = findViewById(R.id.tvGithub);
        tvGithub.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/AimanArif26/SmartPetrolCalculator"));
            startActivity(browserIntent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}