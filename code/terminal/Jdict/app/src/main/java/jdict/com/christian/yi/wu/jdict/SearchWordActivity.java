package jdict.com.christian.yi.wu.jdict;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.db.SearchWordDAO;
import jdict.com.christian.yi.wu.jdict.db.Word;
import jdict.com.christian.yi.wu.jdict.db.WordListAdapter;

public class SearchWordActivity extends AppCompatActivity {

    private SearchView mSearchView;

    private ClearButton mClearButton;

    private String mWord;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        // get clear button
        mClearButton = (ClearButton) findViewById(R.id.search_word_clearButton);

        // get and set search view
        mSearchView = (SearchView) findViewById(R.id.search_word_searchview);

        mSearchView.setIconifiedByDefault(false); // search block visible

        mSearchView.setSubmitButtonEnabled(false); // submit button visible

        mSearchView.onActionViewExpanded(); // action view visible

        // get history list view
        mListView = (ListView) findViewById(R.id.search_word_listview);

        // listen to query change & submit events
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String s) {

                mClearButton.setClearButtonStatus(TextUtils.isEmpty(s) ? false : true);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {

                if (!TextUtils.isEmpty(s)) {

                    Intent intent = new Intent(SearchWordActivity.this, SearchWordResultActivity.class);

                    intent.putExtra("word", s);

                    startActivity(intent);
                }

                return true;
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mClearButton.getStatus() == true) { // jump to result activity

                    Intent intent = new Intent(SearchWordActivity.this, SearchWordResultActivity.class);

                    intent.putExtra("word", mSearchView.getQuery().toString());

                    startActivity(intent);
                }
                else { // back to main activity

                    Intent intent = new Intent(SearchWordActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }
        });


        SearchWordDAO dao = new SearchWordDAO(SearchWordActivity.this);

        ArrayList<Word> wordList = dao.dbQueryAll();

        WordListAdapter wordListAdapter = new WordListAdapter(this, wordList);

        mListView.setAdapter(wordListAdapter);
    }
}
