package jdict.com.christian.yi.wu.jdict;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SearchArticleFragment extends Fragment {

    private Button mSuggestion;

    public SearchArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_article, container, false);

        mSuggestion = (Button) view.findViewById(R.id.search_article_button);

        mSuggestion.setText("请输入标题");

        mSuggestion.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "input title", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public static final SearchArticleFragment newInstance()
    {
        SearchArticleFragment fragment = new SearchArticleFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
