package com.example.aplikasiwsn.activities;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.databinding.ActivityMyCropBinding;
import com.example.aplikasiwsn.models.NodeLokasi;
import com.example.aplikasiwsn.models.Petak;
import com.example.aplikasiwsn.services.NodeLokasiService;
import com.example.aplikasiwsn.services.PetakService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCropActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView btn_back;
    TextView toolbarName;
    private ActivityMyCropBinding binding;
    ProgressDialog progressDialog = null;
    ArrayList<NodeLokasi> nodeLokasiArrayList = new ArrayList<>();
    HashMap<Integer, ArrayList<Petak>> hpPetak = new HashMap<Integer, ArrayList<Petak>>();
    CredentialSharedPreferences cd;
    int jumlahPetak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyCropBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = new CredentialSharedPreferences(this);
        this.jumlahPetak = Integer.parseInt(cd.loadJumlahKodePetak());
        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("My Crop");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        for (int i = 1; i <= jumlahPetak; i++) {
            new MyTask().execute(i);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 1; i <= hpPetak.keySet().size(); i++) {
            PolygonOptions polygonOptions = new PolygonOptions();
            for (int j = 0; j < hpPetak.get(i).size(); j++) {
                Double lintang = Double.parseDouble(hpPetak.get(i).get(j).getLintang());
                Double bujur = Double.parseDouble(hpPetak.get(i).get(j).getBujur());
                LatLng latLng = new LatLng(lintang, bujur);
                polygonOptions.add(latLng);
            }
            Random rnd = new Random();
            int color = Color.argb(133, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            polygonOptions.fillColor(color);
            Polygon polygon = mMap.addPolygon(polygonOptions);
        }

        for (int i = 0; i < nodeLokasiArrayList.size(); i++) {
            LatLng temp = new LatLng(Double.parseDouble(nodeLokasiArrayList.get(i).getLintang()),  Double.parseDouble(nodeLokasiArrayList.get(i).getBujur()));
            if(!nodeLokasiArrayList.get(i).getNama_node().toLowerCase().contains("node")) {
                mMap.addMarker(new MarkerOptions().position(temp).title(nodeLokasiArrayList.get(i).getNama_node()).icon(BitmapDescriptorFactory.fromResource(R.drawable.raspberry_icon)));
            }
            else {
                mMap.addMarker(new MarkerOptions().position(temp).title(nodeLokasiArrayList.get(i).getNama_node()).icon(BitmapDescriptorFactory.fromResource(R.drawable.arduino_icon)));
            }
        }

        float zoomLevel = 16.0f;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(nodeLokasiArrayList.get(0).getLintang()), Double.parseDouble(nodeLokasiArrayList.get(0).getBujur())), zoomLevel));
        progressDialog.dismiss();
    }

    private class MyTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            PetakService pService = AppAPI.getRetrofit().create(PetakService.class);
            try {
                hpPetak.put(integers[0], pService.getPetak(integers[0]).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MyCropActivity.this, "AsyncTask failed", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (hpPetak.size()==jumlahPetak) {
                NodeLokasiService nodeLokasiService = AppAPI.getRetrofit().create(NodeLokasiService.class);
                nodeLokasiService.getNodeLokasi().enqueue(new Callback<ArrayList<NodeLokasi>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NodeLokasi>> call, Response<ArrayList<NodeLokasi>> responseLokasi) {
                        nodeLokasiArrayList = responseLokasi.body();

                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MyCropActivity.this);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NodeLokasi>> call, Throwable t) {
                        Toast.makeText(MyCropActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}