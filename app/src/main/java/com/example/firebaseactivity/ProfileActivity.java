package com.example.firebaseactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseAuth mAuth;

    Button savebutton;
    EditText edit_name , edit_age;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserData");

        savebutton = findViewById(R.id.saveButtonID);
        edit_name = findViewById(R.id.nameEditTextID);
        edit_age = findViewById(R.id.ageEditTextID);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.signOutMenuId)
                {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });


    }

    private void savedata() {

        String name = edit_name.getText().toString().trim();
        String age = edit_age.getText().toString().trim();

        String key = databaseReference.push().getKey();

        Dataclass dataclass = new Dataclass(name ,age);

        databaseReference.child(key).setValue(dataclass);

        Toast.makeText(this, "Successfully Done !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }


}
