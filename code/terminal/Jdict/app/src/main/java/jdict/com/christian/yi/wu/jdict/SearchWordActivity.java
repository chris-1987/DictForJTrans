package jdict.com.christian.yi.wu.jdict;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

public class SearchWordActivity extends AppCompatActivity {

    private ClearEditText mEditText;

    private ClearButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        mButton = (ClearButton)findViewById(R.id.search_word_concrete_clearbutton);

        mEditText = (ClearEditText)findViewById(R.id.search_word_concrete_clearedittext);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                mEditText.setClearIconStatus(s.length() > 0 ? true : false);

                mButton.setClearButtonStatus(s.length() > 0 ? true : false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButton.getStatus() == true) { // request

                    Intent intent = new Intent(SearchWordActivity.this, SearchWordResultActivity.class);

                    intent.putExtra("word", mEditText.getText().toString());

                    startActivity(intent);
                }
                else { // cancel, return back to the main activity

                    Intent intent = new Intent(SearchWordActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // return back to the main activity, destroy activities on the stack until reaching the main

                    startActivity(intent);
                }
            }
        });
    }
}
