package com.example.aplikasiwsn.activities;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasiwsn.R;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCropActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMyCropBinding binding;
    ArrayList<Petak> petakArrayList = new ArrayList<>();
    ArrayList<NodeLokasi> nodeLokasiArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyCropBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ProgressDialog progressDialog = new ProgressDialog(MyCropActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        PetakService pService = AppAPI.getRetrofit().create(PetakService.class);
        pService.getPetak().enqueue(new Callback<ArrayList<Petak>>() {
            @Override
            public void onResponse(Call<ArrayList<Petak>> call, Response<ArrayList<Petak>> response) {
                petakArrayList = response.body();

                NodeLokasiService nodeLokasiService = AppAPI.getRetrofit().create(NodeLokasiService.class);
                nodeLokasiService.getNodeLokasi().enqueue(new Callback<ArrayList<NodeLokasi>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NodeLokasi>> call, Response<ArrayList<NodeLokasi>> responseLokasi) {
                        progressDialog.dismiss();
                        nodeLokasiArrayList = responseLokasi.body();
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MyCropActivity.this);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NodeLokasi>> call, Throwable t) {
                        Toast.makeText(MyCropActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Petak>> call, Throwable t) {
                Toast.makeText(MyCropActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
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
        PolygonOptions polygonOptions = new PolygonOptions();
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for (int i = 0; i < petakArrayList.size(); i++) {
            LatLng latLng = new LatLng(Double.parseDouble(petakArrayList.get(i).getLintang()), Double.parseDouble(petakArrayList.get(i).getBujur()));
            polygonOptions.add(latLng);
        }
        polygonOptions.fillColor(getResources().getColor(R.color.crop_area_1));

        Polygon polygon = mMap.addPolygon(polygonOptions);

        for (int i = 0; i < nodeLokasiArrayList.size(); i++) {
            LatLng temp = new LatLng(Double.parseDouble(nodeLokasiArrayList.get(i).getLintang()),  Double.parseDouble(nodeLokasiArrayList.get(i).getBujur()));
            mMap.addMarker(new MarkerOptions().position(temp).title(nodeLokasiArrayList.get(i).getNama_node()));
        }

        float zoomLevel = 16.0f;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(petakArrayList.get(0).getLintang()), Double.parseDouble(petakArrayList.get(0).getBujur())), zoomLevel));
    }
}