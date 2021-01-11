package com.example.minimo2calizmaria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import com.example.minimo2calizmaria.models.*;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicialActivity extends AppCompatActivity {


    private EditText user;
    private Button findButton;
    private APIGitHub APIgit;
    private static Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        user = findViewById(R.id.userEditText);
        findButton = findViewById(R.id.buttonFind);

        startRetrofit();
        cargarUsuario();

    }

    private static void startRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.github.com/") //Local host on windows 10.0.2.2 and ip our machine 147.83.7.203
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public void cargarUsuario(){

        APIgit = retrofit.create(APIGitHub.class);
        findButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("click", "ok");

                Call<User> call = APIgit.informacionUser(user.getText().toString());
                Log.d("User",user.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("Info","respuesta");
                        if(response.isSuccessful()){
                            Log.d("onResponse", "user");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("user", user.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            Log.d("Info:", "usuario no encontrado");
                            Toast.makeText(getApplicationContext(),"Usuario no encontrado " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error : " + t.getMessage(), Toast.LENGTH_LONG);
                    }

                });
            }
        });
    }
}