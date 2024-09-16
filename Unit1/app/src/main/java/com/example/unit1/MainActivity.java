package com.example.unit1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnLesson01 = findViewById(R.id.btn_lesson_1_3);
        btnLesson01.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Lesson1Activity.class);
            intent.putExtra("TITLE_STORY", "Alibaba và 40 tên cướp");
            startActivity(intent);
        });

        Button btnLesson12 = findViewById(R.id.btn_lesson_1_2);
        btnLesson12.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ToastActivity.class);
            startActivity(intent);
        });
    }
}