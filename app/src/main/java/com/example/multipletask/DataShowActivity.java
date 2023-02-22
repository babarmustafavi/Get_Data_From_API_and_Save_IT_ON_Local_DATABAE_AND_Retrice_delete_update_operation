package com.example.multipletask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multipletask.databinding.ActivityDataShowBinding;

import java.util.ArrayList;

public class DataShowActivity extends AppCompatActivity {
    SqliteDatabase sp;
    ArrayList<RetriveDataModel> arrayList = new ArrayList<>();
    String titileupdate;
    ActivityDataShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //database object and get data from pervious activity into string and set titles
        sp = new SqliteDatabase(this);
        titileupdate = getIntent().getStringExtra("title");
        binding.titleset.setText(titileupdate);
        //chek data is empty or not and set data
        if (titileupdate.equals("") || titileupdate.isEmpty()) {
            Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show();
        } else {
            arrayList = sp.readdatadouble(titileupdate);
            if (arrayList.size() != 0) {
                binding.textset.setText(arrayList.get(0).getYear());
            }
        }


        binding.editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for uupdating data and show dialog for asking
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DataShowActivity.this);
                alertDialog.setTitle("Update");

                final EditText input = new EditText(DataShowActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                //input.setText(jsonModels.get(position).getTitle());
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.editicon);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sp.updateData(input.getText().toString(), titileupdate);
                                startActivity(new Intent(DataShowActivity.this, MainActivity.class));

                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


        binding.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete data and  show dialog for asking
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DataShowActivity.this);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are You Sure Want to Delete?");

//
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sp.deleteData(titileupdate);
                                startActivity(new Intent(DataShowActivity.this, MainActivity.class));
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

    }
}