package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewHistoryAdapter;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.TanahService;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class HistoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener{
    private boolean sortDesc = true;
    private int posisiSpinner = 0;
    RecycleViewHistoryAdapter historyAdapter;
    TanahService tanahService;
    ImageView btn_back, btn_sort;
    TextView toolbarName;
    SearchView searchView;
    private ArrayList<Tanah> tanahArrayListData;
    String[] spinnerMenu = {"Ph Tanah",
                            "Suhu Tanah",
                            "Kelembaban Tanah",
                            "Suhu Udara",
                            "Kelembaban Udara",
                            "Waktu Sensing"};

    String[] spinnerMenuId = {"ph_tanah",
            "suhu_tanah",
            "kelembaban_tanah",
            "suhu_udara",
            "kelembaban_udara",
            "waktu_sensing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        AppAPI.getRetrofit();

        this.btn_back = findViewById(R.id.btn_back);
        this.btn_sort = findViewById(R.id.btn_sort);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Sensing History");
        tanahService = AppAPI.getRetrofit().create(TanahService.class);

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortDesc) {
                    sortDesc= false;
                    String stringSortDesc = Boolean.toString(sortDesc);
                    btn_sort.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    historyAdapter.clearRecycleView();
                    callTanahService(spinnerMenuId[posisiSpinner], stringSortDesc);
                }
                else {
                    sortDesc = true;
                    String stringSortDesc = Boolean.toString(sortDesc);
                    btn_sort.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    historyAdapter.clearRecycleView();
                    callTanahService(spinnerMenuId[posisiSpinner], stringSortDesc);
                }
            }
        });

        searchView = this.findViewById(R.id.sc_textSearch);
        searchView.setOnQueryTextListener(this);

        Spinner spinner = findViewById(R.id.history_spinner);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                                posisiSpinner = position;
                                if (historyAdapter!=null) historyAdapter.clearRecycleView();
                                String isiSpinner = spinnerMenuId[posisiSpinner];
                                String sortBy = Boolean.toString(sortDesc);
                                callTanahService(isiSpinner, sortBy);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_list,spinnerMenu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        String isiSV = searchView.getQuery().toString();
        String isiSpinner = spinnerMenuId[posisiSpinner];
        String isiSort = Boolean.toString(this.sortDesc);

        //TODO
        callTanahService(isiSpinner, isiSort);

    }

    private void setDataInRecycleView() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new RecycleViewHistoryAdapter(this, tanahArrayListData);
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Tanah> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Tanah item : tanahArrayListData) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getNama_node().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        historyAdapter.filteredList(filteredlist);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void callTanahService(String isiSpinner, String sortBy) {
        final ProgressDialog progressDialog = new ProgressDialog(HistoryActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        tanahService.getNodeSensor(isiSpinner, sortBy).enqueue(new Callback<ArrayList<Tanah>>() {
            @Override
            public void onResponse(Call<ArrayList<Tanah>> call, Response<ArrayList<Tanah>> response) {
                progressDialog.dismiss(); //dismiss progress dialog
                tanahArrayListData = response.body();
                for (int i = 0; i < tanahArrayListData.size(); i++) {
                    tanahArrayListData.get(i).setNama_node("Node " + tanahArrayListData.get(i).getKode_petak());
                }
                setDataInRecycleView();
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
            }

            @Override
            public void onFailure(Call<ArrayList<Tanah>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }
}
