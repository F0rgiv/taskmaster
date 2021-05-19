package com.f0rgiv.taskmaster.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Location {
  static String TAG = "locations";

  public static String getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, Context context, Geocoder geocoder) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return null;
    }
    fusedLocationProviderClient.getLastLocation()
      .addOnSuccessListener(location -> {
        Log.i(TAG, "getCurrentLocationComplete: " + location);
        List<Address> address;
        try {
          address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
          Log.i(TAG, "getCurrentLocation: addresses" + address);
          String result =  address.get(0).getPremises();
//          return result;
        } catch (IOException e) {
          e.printStackTrace();
        }
      })
      .addOnCompleteListener(location -> Log.i(TAG, "getCurrentLocation: " + location));
    return null;
  }
}
