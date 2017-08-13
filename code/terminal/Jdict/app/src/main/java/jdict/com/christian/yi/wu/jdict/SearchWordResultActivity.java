package jdict.com.christian.yi.wu.jdict;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchWordResultActivity extends AppCompatActivity {

    private String mWord;

    private TextView mTextView;

    private Button mButton1;

    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word_result);

        // update text view
        mTextView = (TextView) findViewById(R.id.search_word_result_textview);

        Bundle extras = getIntent().getExtras();

        mWord = extras.getString("word");

        mTextView.setText(mWord);

        // listen to click event on returning back
        mButton1 = (Button)findViewById(R.id.search_word_result_button1);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchWordResultActivity.this, SearchWordActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        // listen to click event on adding words
        mButton2 = (Button) findViewById(R.id.search_word_result_button2);

        mButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //TODO add word to library
                Toast.makeText(getApplicationContext(), "todo: add word to library", Toast.LENGTH_LONG).show();
            }
        });
    }

}
