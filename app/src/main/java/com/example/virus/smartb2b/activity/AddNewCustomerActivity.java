package com.example.virus.smartb2b.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virus.smartb2b.R;
import com.example.virus.smartb2b.model.Customer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewCustomerActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.shopName)
    EditText shopName;
    @BindView(R.id.showOwnerName)
    EditText shopOwnerName;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;


    Button addCustomer;

    GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double shopLatitude;
    double shopLongitude;
    Marker marker;
    LatLng shopLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addCustomer = findViewById(R.id.button_add);
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =  shopName.getText().toString().trim();
                String ownerName = shopOwnerName.getText().toString();
                String number = phoneNumber.getText().toString().trim();
                 if (isValid(name,ownerName,number) && marker != null){
                     FirebaseFirestore db = FirebaseFirestore.getInstance();
                     CollectionReference dbCustomer = db.collection("Customers");

                     Customer customer = new Customer();
                     customer.setShopName(name);
                     customer.setShopOwnerName(ownerName);
                     customer.setPhoneNumber(number);

                     customer.setLatitude(shopLatitude);
                     customer.setLongitude(shopLongitude);

                     customer.setGeoPoint(new GeoPoint(shopLatitude,shopLongitude));

                     dbCustomer.add(customer)
                             .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                         @Override
                         public void onSuccess(DocumentReference documentReference) {
                             Toast.makeText(getApplicationContext(), "Customer Added Successfullly" +
                                     "", Toast
                                     .LENGTH_SHORT).show();
                             startActivity(new Intent(AddNewCustomerActivity.this,CustomersActivity.class));
                             finish();

                             AddNewCustomerActivity.this.recreate();
                         }
                     })
                             .addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     e.getMessage();
                                     Toast.makeText(AddNewCustomerActivity.this, e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                 }
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                shopLatlng = latLng;
                if (marker != null){
                    marker.remove();
                }
                shopLatitude = latLng.latitude;
                shopLongitude = latLng.longitude;

                marker = googleMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(shopLatitude,
                                        shopLongitude))
                        .draggable(true).visible(true));

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Shop");

// Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_supervisor));

// adding marker
        mMap.addMarker(marker);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isValid(String shopName,String shopOwnername,String phoneNumber){
        return !shopName.equals("") && !shopOwnername.equals("") && !phoneNumber.equals("");
    }

}
