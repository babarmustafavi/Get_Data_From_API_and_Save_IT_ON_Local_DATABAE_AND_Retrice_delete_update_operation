package com.example.multipletask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.multipletask.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<RetriveDataModel> arrayList = new ArrayList<>();
    private String[] arrayydemo;
    private String[] arrtitle;
    private boolean firsttime = true;
    androidx.appcompat.app.AlertDialog ad;
    AdapterMainactivity adapter;
    SqliteDatabase sp;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = new SqliteDatabase(this);
        //show button after data is save in local
        binding.shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList = sp.readdata();
                adapter = new AdapterMainactivity(MainActivity.this,arrayList);
                binding.recyclerView.setAdapter(adapter);
                binding.shows.setVisibility(View.GONE);
            }
        });
        //insilaize and set layout to recyclerview
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = sp.readdata();
        //Toast.makeText(this, "arrayList="+arrayList.size(), Toast.LENGTH_SHORT).show();
        //chek arrylist if null or o item
        if (arrayList.size()!=0) {
            adapter = new AdapterMainactivity(this,arrayList);
            binding.recyclerView.setAdapter(adapter);
        } else {
            JsonWork jsonWork = new JsonWork();
            if (firsttime) {
                jsonWork.execute();
            }
        }


    }

    private class JsonWork extends AsyncTask<String, String, List<JsonModel>> {


        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String FullJsonData;

        @Override
        protected List<JsonModel> doInBackground(String... strings) {


            try {
                // feach data from server and save into arrylist
                URL url = new URL("https://www.omdbapi.com/?apikey=83b533f9&s=movie&page=1");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);

                }
                FullJsonData = stringBuffer.toString();
                List<JsonModel> jsonModelList = new ArrayList<>();

                JSONObject jsonStartingObject = new JSONObject(FullJsonData);


                JSONArray jsonStudentArray = jsonStartingObject.getJSONArray("Search");

                Log.i("jsonStudentArray", "jsonStudentArray=" + jsonStudentArray.length());

                for (int i = 0; i < jsonStudentArray.length(); i++) {

                    JSONObject jsonUnderArrayObject = jsonStudentArray.getJSONObject(i);

                    JsonModel jsonModel = new JsonModel();
                    jsonModel.setName(jsonUnderArrayObject.getString("Title"));
                    jsonModel.setYear(jsonUnderArrayObject.getString("Year"));
                    // jsonModel.setSection(jsonUnderArrayObject.getString("section"));
                    jsonModelList.add(jsonModel);
                }

                return jsonModelList;


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {

                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }

        @Override
        protected void onPostExecute(List<JsonModel> jsonModels) {
            super.onPostExecute(jsonModels);
            //first time download data from server and save into local database for latter use  thsi is first time

            if (firsttime) {
                arrayydemo = new String[jsonModels.size()];
                arrtitle = new String[jsonModels.size()];
                for (int i = 0; i < jsonModels.size(); i++) {
                    arrayydemo[i] = jsonModels.get(i).getName().toString();
                    arrtitle[i] = jsonModels.get(i).getYear().toString();
                    sp.inserData(arrayydemo[i],arrtitle[i]);
                    // Log.i("onPostExee", "onPostExecute: done");
                    firsttime = false;
                }
                adapter = new AdapterMainactivity(MainActivity.this,arrayList);
                binding.recyclerView.setAdapter(adapter);
                binding.shows.setVisibility(View.VISIBLE);
            }

            // Log.i("ssizesize", "size="+jsonModels.size());


        }
    }

}