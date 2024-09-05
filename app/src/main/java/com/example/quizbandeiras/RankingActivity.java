package com.example.quizbandeiras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView scoreTextView;
    private Button retryButton;
    private Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        userNameTextView = findViewById(R.id.userNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        retryButton = findViewById(R.id.retryButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);

        // Recebe o nome do usuário e a pontuação da Intent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        int score = intent.getIntExtra("SCORE", 0);

        userNameTextView.setText("Nome: " + userName);
        scoreTextView.setText("Pontuação: " + score);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volta para a tela do quiz com o nome do usuário para reiniciar o quiz
                Intent retryIntent = new Intent(RankingActivity.this, QuizActivity.class);
                retryIntent.putExtra("USER_NAME", userName); // Passa o nome do usuário para a QuizActivity
                startActivity(retryIntent);
                finish(); // Fecha a tela de ranking
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volta para a tela inicial
                Intent mainMenuIntent = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
                finish(); // Fecha a tela de ranking
            }
        });
    }
}