package jdict.com.christian.yi.wu.jdict;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jdict.com.christian.yi.wu.jdict.searcharticle.SearchArticleFragment;
import jdict.com.christian.yi.wu.jdict.searchword.SearchWordActivity;
import jdict.com.christian.yi.wu.jdict.searchword.SearchWordFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchWordFragment.OnClickSuggestionListener {

    private Button sab; // search article button

    private Button swb; // search word button

    private Button lb; // library button

    private Button tb; // task button

    private Button sb; // setting button

    private SearchArticleFragment saf;

    private SearchWordFragment swf;

    private SettingFragment sf;

    private TaskFragment tf;

    private LibraryFragment lf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); // main activity

        sab = (Button) findViewById(R.id.search_article_button);
        sab.setOnClickListener(this);

        swb = (Button) findViewById(R.id.search_word_button);
        swb.setOnClickListener(this);

        lb = (Button) findViewById(R.id.library_button);
        lb.setOnClickListener(this);

        tb = (Button) findViewById(R.id.task_button);
        tb.setOnClickListener(this);

        sb = (Button) findViewById(R.id.setting_button);
        sb.setOnClickListener(this);

       showSearchWordFragment(); // show the first fragment
    }

    private void hideAllFragments(android.support.v4.app.FragmentTransaction trans) {
        if (saf != null) {

            trans.hide(saf);
        }

        if (swf != null) {

            trans.hide(swf);
        }

        if (lf != null) {

            trans.hide(lf);
        }

        if (tf != null) {

            trans.hide(tf);
        }

        if (sf != null) {

            trans.hide(sf);
        }
    }

    private void showSearchWordFragment() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (swf == null) {

            swf = SearchWordFragment.newInstance();

            trans.add(R.id.main_frame_layout, swf);
        }

        hideAllFragments(trans);

        trans.show(swf);

        trans.commit();
    }

    private void showSettingFragment() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (sf == null) {

            sf = SettingFragment.newInstance("setting");

            trans.add(R.id.main_frame_layout, sf);
        }

        hideAllFragments(trans);

        trans.show(sf);

        trans.commit();
    }

    private void showLibraryFragment() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (lf == null) {

            lf = LibraryFragment.newInstance("library");

            trans.add(R.id.main_frame_layout, lf);
        }

        hideAllFragments(trans);

        trans.show(lf);

        trans.commit();
    }


    private void showTaskFragment() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (tf == null) {

            tf = TaskFragment.newInstance("task");

            trans.add(R.id.main_frame_layout, tf);
        }

        hideAllFragments(trans);

        trans.show(tf);

        trans.commit();
    }

    private void showSearchArticleFragment() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (saf == null) {

            saf = SearchArticleFragment.newInstance();

            trans.add(R.id.main_frame_layout, saf);
        }

        hideAllFragments(trans);

        trans.show(saf);

        trans.commit();
    }

    @Override
    public void onClick(View view) {

        if (view == swb) {

            showSearchWordFragment();

        } else if (view == sab) {

            showSearchArticleFragment();

        } else if (view == sb) {

            showSettingFragment();
        }
        else if (view == tb) {

            showTaskFragment();
        }
        else  if (view == lb){

            showLibraryFragment();
        }
    }

    @Override
    /**
     * launch SearchWordActivity from SearchWordFragment
     */
    public void onClickSuggestion() {

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        Intent intent = new Intent(MainActivity.this, SearchWordActivity.class);

        startActivity(intent);
    }
}
