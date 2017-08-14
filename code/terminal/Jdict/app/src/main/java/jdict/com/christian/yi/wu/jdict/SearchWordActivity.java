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
import android.widget.SearchView;
import android.widget.Toast;

public class SearchWordActivity extends AppCompatActivity {

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        mSearchView = (SearchView)findViewById(R.id.search_word_searchview);

        mSearchView.setIconifiedByDefault(false); // search block visible

        mSearchView.setSubmitButtonEnabled(true); // submit button visible

        mSearchView.onActionViewExpanded(); // action view visible

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextChange(String s) {

                if (TextUtils.isEmpty(s)) {


                }
                else {


                }

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

    }
}
