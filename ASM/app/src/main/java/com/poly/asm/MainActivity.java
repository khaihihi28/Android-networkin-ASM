package com.poly.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.asm.model.ImageData;
import com.poly.asm.network.ApiService;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editTextDate;
    private Button buttonGetImage;
    private ImageView imageView;
    private TextView textViewTitle, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDate = findViewById(R.id.editTextDate);
        buttonGetImage = findViewById(R.id.buttonGetImage);
        imageView = findViewById(R.id.imageView);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);

        buttonGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editTextDate.getText().toString();
                getImageDataFromApi(date);
            }
        });
    }
    private void getImageDataFromApi(String date) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ImageData> call = apiService.getImageData(date);
        call.enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(Call<ImageData> call, Response<ImageData> response) {
                if (response.isSuccessful()) {
                    ImageData imageData = response.body();
                    if (imageData != null) {
                        showImageData(imageData);
                    }
                } else {
                    // Xử lý lỗi khi nhận phản hồi không thành công
                    // ...
                }
            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu không thành công
                // ...
            }
        });
    }

    private void showImageData(ImageData imageData) {
        // Hiển thị ảnh lên ImageView sử dụng thư viện Glide
        Glide.with(this)
                .load(imageData.getUrl())
                .into(imageView);

        // Hiển thị thông tin tiêu đề và mô tả lên TextView
        textViewTitle.setText(imageData.getTitle());
        textViewDescription.setText(imageData.getExplanation());
    }


}