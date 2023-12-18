package com.example.health;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SymptomViewModel symptomViewModel;
    private EditText symptomInput;
    private TextView causesTextView;
    private TextView severityTextView;
    private TextView recommendationTextView;

    private final ActivityResultLauncher<Intent> speechRecognitionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // Handle the speech recognition results here
                            ArrayList<String> matches = result.getData()
                                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            if (matches != null && !matches.isEmpty()) {
                                String recognizedText = matches.get(0); // Get the recognized text

                                // Now you can trigger the symptom checking with the recognized text
                                // For example, call a method to check the symptom using recognizedText
                                checkSymptomWithRecognizedText(recognizedText);
                            }
                        }
                    });

    // Method to check symptom using the recognized text
    private void checkSymptomWithRecognizedText(String recognizedText) {
        String symptom = recognizedText.toLowerCase(); // Convert recognized text to lowercase
        symptomViewModel.checkSymptom(symptom);
        Log.d("MainActivity", "Symptom checked using voice input: " + symptom);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);


        // Initialize your ViewModel
        symptomViewModel = new ViewModelProvider(this).get(SymptomViewModel.class);

        // Connect UI elements
        symptomInput = findViewById(R.id.symptomInput);
        causesTextView = findViewById(R.id.causesTextView);
        severityTextView = findViewById(R.id.severityTextView);
        recommendationTextView = findViewById(R.id.recommendationTextView);
        VideoView videoView = findViewById(R.id.videoView);

        // Add the TextWatcher for changing text color
        symptomInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textColor = R.color.black; // Default text color resource

                int inputLength = s.length();

                // You can define your own logic to determine when to change the text color
                if (inputLength >= 10) {
                    textColor = R.color.black; // Change to your custom text color resource
                }

                // Set the text color based on the resource
                symptomInput.setTextColor(ContextCompat.getColor(MainActivity.this, textColor));
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Handle any actions you need after the text has changed
            }
        });


        // Observe ViewModel data and update UI
        symptomViewModel.getCauses().observe(this, causes -> causesTextView.setText(getString(R.string.causes_template, causes)));
        symptomViewModel.getSeverity().observe(this, severity -> severityTextView.setText(getString(R.string.severity_template, severity)));
        symptomViewModel.getRecommendation().observe(this, recommendation -> recommendationTextView.setText(getString(R.string.recommendation_template, recommendation)));

        // Load and play the video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video4; // Use your video file

        Log.d("VideoPath", "Video path: " + videoPath);
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e("VideoError", "Error while playing video - what: " + what + ", extra: " + extra);
            return false;
        });

        videoView.setOnPreparedListener(mp -> {
            // Enable looping
            mp.setLooping(true);
        });

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        Button checkButton = findViewById(R.id.checkButton);



        checkButton.setOnClickListener(v -> {
            String symptom = symptomInput.getText().toString().toLowerCase();
            symptomViewModel.checkSymptom(symptom);
            Log.d("MainActivity", "Button clicked: " + symptom);
        });

        // Find the microphone button and set a click listener
        ImageView microphoneButton = findViewById(R.id.microphoneButton);

        microphoneButton.setOnClickListener(v -> {
            // Start voice input here
            // You can use Android's Speech Recognition API or third-party voice input libraries

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            speechRecognitionLauncher.launch(intent);
        });

    }
}
