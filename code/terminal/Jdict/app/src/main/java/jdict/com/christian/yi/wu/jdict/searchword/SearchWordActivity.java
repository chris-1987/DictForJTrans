package jdict.com.christian.yi.wu.jdict.searchword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import jdict.com.christian.yi.wu.jdict.MainActivity;
import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.utility.ClearButton;

public class SearchWordActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchView mSearchView;

    private ClearButton mClearButton;

    private Button mZhButton;

    private Button mJpButton;

    private SearchWordLanguageFragment mZhFragment;

    private SearchWordLanguageFragment mJpFragment;

    private String mFromLanguage;

    private String mToLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        // get and set clear button
        mClearButton = (ClearButton) findViewById(R.id.search_word_clearButton);

        // get and set search view
        mSearchView = (SearchView) findViewById(R.id.search_word_searchview);
        mSearchView.setIconifiedByDefault(false); // search block visible
        mSearchView.setSubmitButtonEnabled(false); // submit button visible
        mSearchView.onActionViewExpanded(); // action view visible

        // set listener to clear button
        mClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mClearButton.getStatus() == true) { // jump to result activity

                    Intent intent = new Intent(SearchWordActivity.this, SearchWordResultActivity.class);

                    intent.putExtra("content", mSearchView.getQuery().toString());

                    intent.putExtra("fromLanguage", mFromLanguage);

                    intent.putExtra("toLanguage", mToLanguage);

                    startActivity(intent);
                } else { // back to main activity

                    Intent intent = new Intent(SearchWordActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }
        });

        // set listener to search view
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String s) {

                if (TextUtils.isEmpty(s) == true) { // is empty

                    mClearButton.setClearButtonStatus(false);

                    switch(mFromLanguage) {

                        case "zh": mZhFragment.queryCachedWords();break;

                        case "jp": mJpFragment.queryCachedWords();break;
                    }
                }
                else {

                    mClearButton.setClearButtonStatus(true);

                    switch(mFromLanguage) {

                        case "zh": mZhFragment.querySuggestion(s);break;

                        case "jp": mJpFragment.querySuggestion(s);break;
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {

                if (!TextUtils.isEmpty(s)) {

                    Intent intent = new Intent(SearchWordActivity.this, SearchWordResultActivity.class);

                    intent.putExtra("content", s);

                    intent.putExtra("fromLanguage", mFromLanguage);

                    intent.putExtra("toLanguage", mToLanguage);

                    startActivity(intent);
                }

                return true;
            }
        });

        mZhButton = (Button) findViewById(R.id.search_word_language_zh_button);
        mZhButton.setOnClickListener(this);

        mJpButton = (Button) findViewById(R.id.search_word_language_jp_button);
        mJpButton.setOnClickListener(this);

        showSearchWordLanguageZhFragment();
    }

    @Override
    public void onClick(View view) {

        if (view == mZhButton) {

            showSearchWordLanguageZhFragment();

        } else if (view == mJpButton) {

            showSearchWordLanguageJpFragment();
        }
    }

    public void hideAllFragments(android.support.v4.app.FragmentTransaction trans) {

        if (mZhFragment != null) {

            trans.hide(mZhFragment);
        }

        if (mJpFragment != null) {

            trans.hide(mJpFragment);
        }
    }

    private void showSearchWordLanguageZhFragment() {

        mFromLanguage = "zh";

        mToLanguage = "jp";

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (mZhFragment == null) {

            mZhFragment = SearchWordLanguageFragment.newInstance(mFromLanguage, mToLanguage);

            trans.add(R.id.search_word_language_fragment, mZhFragment);
        }

        hideAllFragments(trans);

        trans.show(mZhFragment);

        trans.commit();

 //       Toast.makeText(getApplicationContext(),"from: " + mFromLanguage + " to: " + mToLanguage, Toast.LENGTH_LONG).show();
    }

    private void showSearchWordLanguageJpFragment() {

        mFromLanguage = "jp";

        mToLanguage = "zh";

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (mJpFragment == null) {

            mJpFragment = SearchWordLanguageFragment.newInstance(mFromLanguage, mToLanguage);

            trans.add(R.id.search_word_language_fragment, mJpFragment);
        }

        hideAllFragments(trans);

        trans.show(mJpFragment);

        trans.commit();

//        Toast.makeText(getApplicationContext(),"from: " + mFromLanguage + " to: " + mToLanguage, Toast.LENGTH_LONG).show();
    }
}
