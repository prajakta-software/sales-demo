package com.prajakta_softwaredevloper.salesforcedemo.View.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.prajakta_softwaredevloper.salesforcedemo.Model.RoomDatabase.ShopDetails;
import com.prajakta_softwaredevloper.salesforcedemo.R;
import com.prajakta_softwaredevloper.salesforcedemo.ViewModel.ShopViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int REQUEST_LOCATION_PERMISSION = 3;

    private ShopViewModel shopViewModel;
    private EditText shopName, shopContactNumber, shopLocation;
    private ImageView shopImage;
    private String imagePath;
    private Button btnUploadImage, btnSaveShopDetails, btnCaptureImage;
    private double latitude, longitude;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Uri photoURI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        shopName = view.findViewById(R.id.etShopName);
        shopContactNumber = view.findViewById(R.id.etShopContactNumber);
        shopLocation = view.findViewById(R.id.etShopLocation);
        shopImage = view.findViewById(R.id.ivShopImage);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        btnSaveShopDetails = view.findViewById(R.id.btnSaveShopDetails);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);

        btnUploadImage.setOnClickListener(v -> openImageSelector());
        btnSaveShopDetails.setOnClickListener(v -> saveShop());
        btnCaptureImage.setOnClickListener(v -> checkPermissions());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    String address = getAddressFromLatLng(latitude, longitude);
                    shopLocation.setText(address);
                }
            }
        };
        getCurrentLocation();
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void checkPermissions() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        Log.v("ImagePathFromCamera8",imagePath);

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                shopImage.setImageBitmap(bitmap);
                imagePath = data.getData().toString();
                Log.v("ImagePathFromCamera1",imagePath);


            } catch (FileNotFoundException e) {
                Log.v("Media Image Error:", e.toString());
            }
        }  else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // Retrieve the thumbnail image
                Bitmap bmp = (Bitmap) data.getExtras().get("data");

                // Display the thumbnail in ImageView (shopImage)
                shopImage.setImageBitmap(bmp);

                // Save the full-sized image to a file and get its path
                try {
                    File imageFile = createImageFile();
                    // Here you can use imageFile.getAbsolutePath() for imagePath if needed later
                    imagePath = imageFile.getAbsolutePath();
                    Log.v("ImagePathFromCamera",imagePath);
                    insertImageIntoMediaStore(imageFile);

                } catch (IOException e) {
                    // Handle error creating image file
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(requireContext(), "Image capture canceled", Toast.LENGTH_SHORT).show();
            } else {
                // Image capture failed, advise user
                Toast.makeText(requireContext(), "Image capture failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                Toast.makeText(requireContext(), "Camera and storage permissions are required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void saveShop() {
        String name = shopName.getText().toString();
        String contact = shopContactNumber.getText().toString();
        String address = shopLocation.getText().toString();

        if (name.isEmpty() || contact.isEmpty() || imagePath == null) {
            Toast.makeText(requireContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current timestamp
        long currentTime = System.currentTimeMillis();

        // Get the user ID (assuming it's stored in SharedPreferences)
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySalesTeam", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "0");

        ShopDetails details = new ShopDetails();
        details.setTimestamp(currentTime);
        details.setShopName(name);
        details.setContactNumber(contact);
        details.setImagePath(imagePath);
        details.setLatitude(latitude);
        details.setLongitude(longitude);
        details.setAddress(address);
        details.setUserId(userId);

        shopViewModel.insert(details);

        clearFields(); // Clear fields after saving
    }

    private void clearFields() {
        shopName.setText("");
        shopContactNumber.setText("");
        shopLocation.setText("");
        shopImage.setImageResource(0);
        imagePath = null;
    }

    private String getAddressFromLatLng(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            } else {
                return "Address not found";
            }
        } catch (IOException e) {
            Log.v("Location Address Error:", e.toString());
            return "Unable to get address";
        }
    }
    // Method to insert image into MediaStore
    private void insertImageIntoMediaStore(File imageFile) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.getName());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());

        ContentResolver contentResolver = requireActivity().getContentResolver();
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri != null) {
            imagePath = String.valueOf(imageUri);
            Log.v("ImagePathFromCamera22",imagePath);


            Toast.makeText(requireContext(), "Image saved to MediaStore", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to save image to MediaStore", Toast.LENGTH_SHORT).show();
        }
    }
}
