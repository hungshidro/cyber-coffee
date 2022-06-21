package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference rankref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()    );
        innit();
        setDefault();
    }

    private void setDefault() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.edtPhoneNumber.setText(user.getPhoneNumber());
        binding.edtPhoneNumber.setEnabled(false);
        binding.radiobtFemale.setChecked(true);
    }

    private void innit() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(Common.USER_REFERENCE);
        rankref = database.getReference().child(Common.RANK_REFERENCE);

        binding.btRegister.setOnClickListener(v -> {
            String firstName = binding.edtFirstname.getText().toString();
            String lastName = binding.edtLastname.getText().toString();
            UserModel userModel = new UserModel();
            if(firstName != "" && lastName != ""){
                boolean gender = true;
                switch(binding.radioGroupGender.getCheckedRadioButtonId()){
                    case R.id.radiobt_female: gender = false; break;
                    case R.id.radiobt_male: gender = true; break;
                }
                userModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                userModel.setPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                userModel.setFirtName(binding.edtFirstname.getText().toString());
                userModel.setLastName(binding.edtLastname.getText().toString());
                userModel.setGender(gender);
                reference.child(userModel.getUid())
                        .setValue(userModel)
                        .addOnFailureListener(e -> {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_LONG).show();
                                Common.currentUser = userModel;
                                RankModel rank = new RankModel();
                                rank.setName("Topaz");
                                rank.setRankpoint(0);
                                rankref.child(userModel.getUid()).setValue(rank);
                                Common.rankUser = rank;
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            }
                        });
            }
        });
    }
}