package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class list extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //        TextView close = findViewById(R.id.close);
//        builder.setView(mview);
//         AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//        builder.setItems(list, new DialogInterface.OnClickListener() {
//            TextView textView;
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                textView = findViewById(R.id.textView2);
//                textView.setText(list[which]);
//            }
//        });
//        dialog.show();
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.activity_list);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.list_bg);
//        dialog.show();
//        close = findViewById(R.id.close);
//        close.setOnClickListener(v -> dialog.dismiss());


        //        setContentView(R.layout.activity_list);
//        listView = findViewById(R.id.listView);
//        close = findViewById(R.id.close);
//        ArrayAdapter<String> ad;
//        ad = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(ad);
//        close.setOnClickListener(v -> finish());
    }
}