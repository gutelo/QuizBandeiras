package com.example.quizbandeiras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTitle;
    private ImageView flagImage;
    private RadioButton option1, option2, option3, option4;
    private Button submitButton;
    private RadioGroup optionsGroup;

    private int[] flags = {R.drawable.brasil, R.drawable.bulgaria, R.drawable.canada, R.drawable.china,
            R.drawable.coreia_sul, R.drawable.cuba, R.drawable.guatemala, R.drawable.hungria, R.drawable.italia, R.drawable.usa};
    private String[][] options = {
            {"Brasil", "Argentina", "Chile", "Uruguai"},
            {"Ucrânia", "Finlandia", "Bulgaria", "Russia"},
            {"Chile", "Dinamarca", "Suiça", "Canada"},
            {"China", "Japão", "Coreia do Sul", "Malasia"},
            {"Taiwan", "Coreia do Sul", "Filipinas", "Sérvia"},
            {"Cuba", "Porto Rico", "México", "Equador"},
            {"Bélgica", "Noruega", "San Marino", "Guatemala"},
            {"Nigeria", "Lituania", "Hungria", "Bolivia"},
            {"Portugal", "Italia", "Espanha", "França"},
            {"Suecia", "India", "Síria", "USA"}
    };
    private String[] correctAnswers = {"Brasil", "Bulgaria", "Canada",
            "China", "Coreia do Sul", "Cuba",
            "Guatemala", "Hungria", "Italia",
            "USA"};

    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTitle = findViewById(R.id.questionTitle);
        flagImage = findViewById(R.id.flagImage);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);
        optionsGroup = findViewById(R.id.optionsGroup);

        // Recebe o nome do usuário da Intent
        userName = getIntent().getStringExtra("USER_NAME");

        loadQuestion();

        // Desabilita o botão "RESPONDER" inicialmente
        submitButton.setEnabled(false);

        // Configura o Listener para o RadioGroup
        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Habilita o botão "RESPONDER" quando uma opção é selecionada
                submitButton.setEnabled(true);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedOption = findViewById(selectedId);
                    String answer = selectedOption.getText().toString();
                    checkAnswer(answer);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < flags.length) {
                        loadQuestion();
                    } else {
                        finishQuiz(); // Finaliza o quiz após a última pergunta
                    }
                    // Desabilita o botão "RESPONDER" após clicar
                    submitButton.setEnabled(false);
                } else {
                    Toast.makeText(QuizActivity.this, "Selecione uma opção", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadQuestion() {
        flagImage.setImageResource(flags[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);
        optionsGroup.clearCheck();
    }

    private void checkAnswer(String answer) {
        if (answer.equals(correctAnswers[currentQuestionIndex])) {
            score++;
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, RankingActivity.class);
        intent.putExtra("USER_NAME", userName); // Passa o nome do usuário para a RankingActivity
        intent.putExtra("SCORE", score); // Envia a pontuação calculada
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Cria um Intent para retornar à tela principal
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        // Limpa a pilha de atividades para que a tela principal seja a única na pilha
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        // Encerra a atividade atual
        finish();

        // Chama a implementação padrão de onBackPressed (opcional, se necessário)
        super.onBackPressed();
    }
}