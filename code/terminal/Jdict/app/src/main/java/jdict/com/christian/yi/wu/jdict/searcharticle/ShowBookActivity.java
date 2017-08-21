package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import jdict.com.christian.yi.wu.jdict.R;

public class ShowBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String bookType = intent.getStringExtra("booktype");
        Toast.makeText(this, bookType, Toast.LENGTH_LONG).show();
    }
}
