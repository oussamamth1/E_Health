package com.sem.e_health;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.sem.e_health.MainActivity.changeStatusBarToWhite;

public class DoctorActivity extends AppCompatActivity implements ContactAdapter.ItemClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference delRf;

    FirebaseAuth mAuth;
    ContactAdapter Adapter;
    RecyclerView recyclerview;
    List<Client> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        changeStatusBarToWhite(this);
        recyclerview = findViewById(R.id.RC);
        enableSwipeToDeleteAndUndo();


        Adapter = new ContactAdapter(this, listData);
        Adapter.setClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("E-Health/Doctors/" + Sub() + "/Clients");
        DatabaseReference myRef1 = database.getReference("E-Health");
        delRf = database.getReference("E-Health/Doctors/" + Sub());

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerview.setAdapter(Adapter);
        recyclerview.setHasFixedSize(true);
        myRef.addValueEventListener(vel);


        DatabaseReference Doc = myRef1.child("Doctors");
        Doc.child(Sub()).child("UID").setValue(mAuth.getUid());


    }

    ValueEventListener vel = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Client client;
            listData.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                client = ds.getValue(Client.class);
                if (client != null) {
                    listData.add(client);

                }

            }
            Adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                Adapter.removeItem(position, delRf);

            }

        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerview);
    }

    public void onItemClickListener(View view, int position) {

        Intent intent = new Intent(DoctorActivity.this, Addtest.class);

        intent.putExtra("name", listData.get(position).getName());
        intent.putExtra("lastname", listData.get(position).getLastName());
        intent.putExtra("docid", Sub());
        startActivity(intent);

    }

    public String Sub() {

        String filename = (mAuth.getCurrentUser().getEmail());
        int iend = filename.indexOf("@");

        String subString;
        if (iend != -1) {
            subString = filename.substring(0, iend); //this will give abc
            return subString;
        }
        return null;
    }


    public void onLoggedOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DoctorActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onHistoryItemClickListener(View view, int position) {
        Intent intent = new Intent(DoctorActivity.this, Addtest.class);

        intent.putExtra("name", listData.get(position).getName());
        intent.putExtra("lastname", listData.get(position).getLastName());
        intent.putExtra("docid", Sub());
        startActivity(intent);
    }

    @Override
    public void onCallItemClickListener(View view, int position) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listData.get(position).getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
