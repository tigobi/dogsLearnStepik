package com.example.dogs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private static final String TAG = "MainActivity";
    private ImageView imageViewDog;
    private ProgressBar progressBar;
    private Button buttonLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getIsCannotLoad().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean errorLoading) {
                if (errorLoading) {
                    Toast.makeText(
                            MainActivity.this,
                            R.string.error_loading_image,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {

                }
            }
        });
        viewModel.getDogImage().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageViewDog);
            }
        });
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initViews() {
        imageViewDog = findViewById(R.id.imageViewDog);
        progressBar = findViewById(R.id.progressBar);
        buttonLoadImage = findViewById(R.id.buttonLoadImage);

    }
}