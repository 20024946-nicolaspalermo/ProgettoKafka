package com.example.fix_it_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fix_it_app.Login.LoginActivity;
import com.example.fix_it_app.Model.Users;
import com.example.fix_it_app.Utili.Connection;
import com.example.fix_it_app.Utili.Loading;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    private FirebaseUser user;
    private TextView nameUser, surnameUser, emailUser, phoneUser, statusUser;
    private Connection connection;
    private Loading loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        connection = new Connection(requireContext());
        loading = new Loading(requireActivity());
        nameUser = view.findViewById(R.id.nameUser);
        surnameUser = view.findViewById(R.id.surnameUser);
        emailUser = view.findViewById(R.id.text_email);
        phoneUser = view.findViewById(R.id.text_phone);
        statusUser = view.findViewById(R.id.statusUser);

        if(!connection.checkConnection()){
            Toast.makeText(requireActivity(), "No internet connection, check your settings", Toast.LENGTH_SHORT).show();
            return;
        }

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("users");

        loading.loadingAlertDialog();
        Query query = myRef.orderByChild("email").equalTo(user.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String name = " " + ds.child("name").getValue();
                        String surname = " " + ds.child("surname").getValue();
                        String email = " " + ds.child("email").getValue();
                        String phone = " " + ds.child("phone").getValue();

                        if(ds.child("document").getValue().equals(""))
                            statusUser.setText("Status utente registrato");
                        else
                            statusUser.setText("Status utente convalidato");

                        nameUser.setText(name);
                        surnameUser.setText(surname);
                        emailUser.setText(email);
                        phoneUser.setText(phone);
                        loading.dismissDialog();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
