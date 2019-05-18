package com.example.retrofit_tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private JsonPLaceHolderApi jsonPLaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPLaceHolderApi = retrofit.create(JsonPLaceHolderApi.class);

        // getPosts();
        // getComments();
        //creategetPosts();
        //updatePost();
        deletePost();
    }



    private void getComments() {

        // Using @Path retrofit.
        //Call<List<Comment>> call = jsonPLaceHolderApi.getComments(3);

        // Using @Url retrofit.
        Call<List<Comment>> call = jsonPLaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code response " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";
                    textView.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }

    private void getPosts() {


        Map<String, String> paramaters = new HashMap<>();
        paramaters.put("userId", "1");
        paramaters.put("_sort", "id");
        paramaters.put("_order", "desc" + "");

        // Option 1. Using @Query
        Call<List<Post>> call = jsonPLaceHolderApi.getPosts(paramaters);

        // Option 2. Using Hashmap @QueryMap
        //Call<List<Post>> call = jsonPLaceHolderApi.getPosts(new Integer[]{2,3,6}, null, null);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code response " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textView.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }


    private void creategetPosts() {
        Post post = new Post(23, "New Title", "New Text");


        // Option 1. Using Serialize object (for nested json object)
        //Call <Post> call = jsonPLaceHolderApi.createPost(post);

        // Option 2. Using UrlEncoded form
        //Call <Post> call = jsonPLaceHolderApi.createPost(23,"New Title", "New Text");

        // Option 3. Using @FieldMap form
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Post> call = jsonPLaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code response " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textView.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12, null, "New Text");

        Call<Post> call = jsonPLaceHolderApi.patchPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code response " + response.code());
                    return;
                }

                Post putResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + putResponse.getId() + "\n";
                content += "User ID: " + putResponse.getUserId() + "\n";
                content += "Title: " + putResponse.getTitle() + "\n";
                content += "Text: " + putResponse.getText() + "\n\n";

                textView.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });

    }

    private void deletePost() {

        Call<Void> call = jsonPLaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textView.setText("Code response " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });


    }

}
