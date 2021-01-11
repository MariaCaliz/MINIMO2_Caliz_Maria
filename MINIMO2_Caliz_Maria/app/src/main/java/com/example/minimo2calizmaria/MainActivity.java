package com.example.minimo2calizmaria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minimo2calizmaria.models.*;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private APIGitHub APIgit;
    private ProgressBar progressBar;
    private String user;
    private TextView followers;
    private TextView following;
    private ImageView imageProfile;
    private static Retrofit retrofit;
    private List<Repositories> reposList= new ArrayList<Repositories>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.repositories);
        imageProfile = findViewById(R.id.imageProfile);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");

        startRetrofit();

        APIgit = retrofit.create(APIGitHub.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        cargarInfo();


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

    public void cargarInfo(){
        Log.d("user", user);
        Call<User> call = APIgit.informacionUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                Log.d("following y followers ", String.valueOf(user.getFollowing()) + ", " +String.valueOf(user.getFollowers()));
                following.setText(String.valueOf(user.getFollowing()));
                followers.setText(String.valueOf(user.getFollowers()));
                Picasso.with(getApplicationContext()).load(user.getAvatar_url()).into(imageProfile);
                showProgress(false);

                cargarRepositorios();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });
    }

    public void cargarRepositorios(){
        showProgress(true);
        APIgit = retrofit.create(APIGitHub.class);
        Call<List<Repositories>> call = APIgit.listaRepos(user);
        call.enqueue(new Callback<List<Repositories>>() {
            @Override
            public void onResponse(Call<List<Repositories>> call, Response<List<Repositories>> response) {

                if(response.isSuccessful()){
                    Log.d("onResponse", "lsita");
                    List<Repositories> listaRepos = response.body();
                    recyclerView = findViewById(R.id.repositories);
                    // https://developer.android.com/guide/topics/ui/layout/recyclerview#java + video

                    RecyclerViewAdapt Adapter = new RecyclerViewAdapt(getApplicationContext(), listaRepos);
                    recyclerView.setAdapter(Adapter);
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<List<Repositories>> call, Throwable t) {

            }
        });
    }

    public void showProgress (boolean visible){
        //Sets the visibility/invisibility of progressBar
        if(visible)
            this.progressBar.setVisibility(View.VISIBLE);
        else
            this.progressBar.setVisibility(View.GONE);
    }


}