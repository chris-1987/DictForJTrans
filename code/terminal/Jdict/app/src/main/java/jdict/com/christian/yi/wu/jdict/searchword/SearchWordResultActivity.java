package jdict.com.christian.yi.wu.jdict.searchword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.db.searchword.SearchWordDAO;
import jdict.com.christian.yi.wu.jdict.db.searchword.WordView;
import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;

public class SearchWordResultActivity extends AppCompatActivity {

    private String mContent; // word handler

    private String mFromLanguage;

    private String mToLanguage;

    private TextView mTextView1; // display word

    private Button mButton1; // back to the previous activity

    private Button mButton2; // add word to note

    private TextView mTextView2; // display word meaning

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_word_result);

        // get and set textview for showing word to be queried
        mTextView1 = (TextView) findViewById(R.id.search_word_result_textview);

        Bundle extras = getIntent().getExtras();

        mContent = extras.getString("content");

        mFromLanguage = extras.getString("fromLanguage");

        mToLanguage = extras.getString("toLanguage");

        // set textview1
        mTextView1.setText(mContent);

        // get the button for listening to the "return back" event
        mButton1 = (Button) findViewById(R.id.search_word_result_button1);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchWordResultActivity.this, SearchWordActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });

        //
        mButton2 = (Button) findViewById(R.id.search_word_result_button2);

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO add word to library
                Toast.makeText(getApplicationContext(), "todo: add word to library", Toast.LENGTH_LONG).show();
            }
        });

        // get the textview for showing the traslation
        mTextView2 = (TextView) findViewById(R.id.search_word_result_textview2);

        // search
        SearchWordDAO dao = new SearchWordDAO(SearchWordResultActivity.this);
        ArrayList<WordView> wordViewList = dao.dbQueryWord(mContent, mFromLanguage);

        if (wordViewList.size() == 0) { //  search online

            // send a request to baidu for retrieving the translation
            try {

                SearchWordRequestUtils requestUtils = new SearchWordRequestUtils();

                requestUtils.translate(mContent, mFromLanguage, mToLanguage, new HttpCallBack() {

                    @Override
                    public void onSuccess(String result) {

                        // persistent storage
                        SearchWordDAO dao = new SearchWordDAO(SearchWordResultActivity.this);
                        dao.dbCacheWord(mContent, result, mFromLanguage);

                        // show result
                        mTextView2.setText(result);
                    }

                    @Override
                    public void onFailure(String exception) {

                        Toast.makeText(getApplicationContext(), "failed to retrieve the answer due to" + exception, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        else { // show result

            String meaning = "";

            for(WordView wordView :wordViewList) {

                meaning = meaning + wordView.getMeaning() + "\n";
            }

            mTextView2.setText(meaning);
        }
    }
}
