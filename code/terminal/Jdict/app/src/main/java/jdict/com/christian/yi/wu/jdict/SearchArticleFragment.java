package jdict.com.christian.yi.wu.jdict;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SearchArticleFragment extends Fragment {

    private TextView title;

    private String name;

    public SearchArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_article, container, false);

        title = (TextView) view.findViewById(R.id.search_article_textview);

        title.setText(name);

        title.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                title.setText("我变了-" + name);
            }
        });

        return view;
    }

    public static final SearchArticleFragment newInstance(String name)
    {
        SearchArticleFragment fragment = new SearchArticleFragment();

        Bundle bundle = new Bundle();

        bundle.putString("name", name);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {

            name = bundle.get("name").toString();
        }
    }

}
