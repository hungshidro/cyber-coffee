package com.example.myapplication.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private List<AuthUI.IdpConfig> providers;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ActivityResultLauncher<Intent> signLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            (result) -> {
                IdpResponse response = result.getIdpResponse();
                if(result.getResultCode() == Activity.RESULT_OK){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        innit();
    }

    private void innit() {
        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder()
                        .setDefaultCountryIso("vn")
                        .build()
        );
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.USER_REFERENCE);
        listener = myAuth -> {
            Dexter.withContext(this)
                    .withPermissions(Arrays.asList(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    )).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(multiplePermissionsReport.areAllPermissionsGranted()){
                            FirebaseUser user = myAuth.getCurrentUser();
                            if(user != null){
                                checkFromDatabase();
                            }
                            else goToLoginLayout();
                        }
                        else
                            Toast.makeText(StartActivity.this, "Please enable all permission", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                }
            }).check();
        };
    }

    private void goToLoginLayout() {
        signLauncher.launch(AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setTheme(R.style.LoginTheme)

                .build());
    }

    private void checkFromDatabase() {
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            userModel.setUid(snapshot.getKey());
                            Log.e("test", userModel.getPhoneNumber().equals(Common.ADMIN_PHONENUMBER)+"");
                            Log.e("test", userModel.getPhoneNumber());
                            if(userModel.getPhoneNumber().equals(Common.ADMIN_PHONENUMBER)){
                                Log.e("test", userModel.getPhoneNumber());
                                toAdminActivity();
                                Log.e("test1", userModel.getPhoneNumber());
                            }
                            else{
                                goMainActivity(userModel);
                            }
                        }
                        else toRegisterLayout();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void toAdminActivity() {
        startActivity(new Intent(StartActivity.this, AdminActivity.class));
        finish();
    }

    private void toRegisterLayout() {
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
        finish();
    }

    private void goMainActivity(UserModel userModel) {
        Common.currentUser = userModel;
        database.getReference().child(Common.RANK_REFERENCE).child(userModel.getUid())
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Common.rankUser = snapshot.getValue(RankModel.class);
                    Log.e("rankpoint", Common.rankUser.getRankpoint()+" login");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if(firebaseAuth != null && listener != null){
            firebaseAuth.removeAuthStateListener(listener);
        }
        super.onStop();
    }
}