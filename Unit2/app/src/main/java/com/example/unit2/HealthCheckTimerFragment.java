package com.example.unit2;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
import android.widget.TextView;

public class HealthCheckTimerFragment extends Fragment {

    private Button startStopButton, resetButton;
    private ProgressBar progressBar;
    private TextView timerTextView; // Add this for displaying the remaining time
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 90000; // 90 seconds
    private long startTimeInMillis = 90000; // 90 seconds

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_check_timer, container, false);

        startStopButton = view.findViewById(R.id.startStopButton);
        resetButton = view.findViewById(R.id.resetButton);
        progressBar = view.findViewById(R.id.progressBar);
        timerTextView = view.findViewById(R.id.timerTextView);

        startStopButton.setOnClickListener(v -> {
            if (isRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        resetButton.setOnClickListener(v -> resetTimer());

        progressBar.setMax((int) startTimeInMillis / 1000); // 90 seconds in seconds
        updateTimerText(); // Update the TextView to show initial time

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Updates every second
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int timeElapsedInSeconds = (int) (startTimeInMillis - timeLeftInMillis) / 1000;
                progressBar.setProgress(timeElapsedInSeconds);

                // Update remaining time in the TextView
                updateTimerText();

                // Change progress bar color based on the time elapsed
                if (timeElapsedInSeconds < 20) {
                    changeProgressBarColor(R.color.colorWeak); // Red for Weak
                } else if (timeElapsedInSeconds < 40) {
                    changeProgressBarColor(R.color.colorAverage); // Yellow for Average
                } else if (timeElapsedInSeconds < 60) {
                    changeProgressBarColor(R.color.colorGood); // Green for Good
                } else {
                    changeProgressBarColor(R.color.colorVeryGood); // Blue for Very good
                }
            }

            @Override
            public void onFinish() {
                isRunning = false;
                startStopButton.setText("Bắt đầu");
                showHealthStatus();
            }
        }.start();

        isRunning = true;
        startStopButton.setText("Dừng");
    }

    private void stopTimer() {
        countDownTimer.cancel();
        isRunning = false;
        startStopButton.setText("Bắt đầu");
        showHealthStatus();
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        timeLeftInMillis = startTimeInMillis;
        progressBar.setProgress(0); // Reset progress bar
        changeProgressBarColor(R.color.colorWeak); // Reset color to Weak
        startStopButton.setText("Bắt đầu");
        updateTimerText(); // Reset the timer display
    }

    private void showHealthStatus() {
        int progress = progressBar.getProgress();
        String status;

        if (progress < 20) {
            status = "Yếu"; // Weak
        } else if (progress < 40) {
            status = "Trung bình"; // Average
        } else if (progress < 60) {
            status = "Tốt"; // Good
        } else {
            status = "Rất tốt"; // Very good
        }

        Toast.makeText(getContext(), "Tình trạng sức khỏe: " + status, Toast.LENGTH_SHORT).show();
    }

    private void changeProgressBarColor(int colorResId) {
        // Sử dụng ColorStateList để đổi màu ProgressBar
        progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), colorResId)));
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted); // Update the TextView with the remaining time
    }
}
