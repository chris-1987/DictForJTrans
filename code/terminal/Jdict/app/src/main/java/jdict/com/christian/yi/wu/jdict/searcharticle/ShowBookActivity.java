package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.SearchArticleDAO;

public class ShowBookActivity extends AppCompatActivity {

    private Boolean mIsFinished;
    private ShowBookFragment mShowBookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);
        Intent intent = getIntent();
        mIsFinished = intent.getBooleanExtra("isFinished", true);

        displayShowBookFragment();
    }

    void displayShowBookFragment() {
        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (mShowBookFragment == null) {
            mShowBookFragment = ShowBookFragment.newInstance(mIsFinished);
            trans.add(R.id.show_book_fragment, mShowBookFragment);
        }

        trans.show(mShowBookFragment);
        trans.commit();
    }
}
