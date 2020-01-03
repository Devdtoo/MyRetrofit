package soft.gen.myretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;
import soft.gen.myretrofit.Adapter.CustomAdapter;
import soft.gen.myretrofit.Utility.Commonhelper;

public class MainActivity extends AppCompatActivity {

    private Commonhelper commonhelper;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    String url = "https://api.androidhive.info/json/shimmer/menu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Learn HTTP ok");
        commonhelper = new Commonhelper(this);

        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        commonhelper.ShowMesseage("Hello Alice");

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progress_bar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getData();

    }

    private void getData() {

        if (commonhelper.isNetworkConnected()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.setTimeout(50000);
            asyncHttpClient.post(url, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
//                progressBar.setVisibility(View.VISIBLE);
                    commonhelper.ShowLoader();
                    super.onStart();

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                progressBar.setVisibility(View.INVISIBLE);
                    commonhelper.HideLoader();
                    super.onSuccess(statusCode, headers, response);
                    fetchData(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    //super.onProgress(bytesWritten, totalSize);
                    long progress = (bytesWritten / totalSize) * 100;
                }
            });
        } else {
//            commonhelper.ShowMesseage("Please check Internet connection");
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchData(JSONArray response) {
        CustomAdapter customAdapter = new CustomAdapter(response, this);
        recyclerView.setAdapter(customAdapter);
    }
}

