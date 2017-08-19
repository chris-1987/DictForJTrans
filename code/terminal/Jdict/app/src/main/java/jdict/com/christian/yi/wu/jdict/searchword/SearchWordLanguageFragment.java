package jdict.com.christian.yi.wu.jdict.searchword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.db.searchword.SearchWordDAO;
import jdict.com.christian.yi.wu.jdict.db.searchword.WordView;
import jdict.com.christian.yi.wu.jdict.db.searchword.WordViewListAdapter;


public class SearchWordLanguageFragment extends Fragment {

    private static final String ARG_PARAM1 = "FROM_LANGUAGE";
    private static final String ARG_PARAM2 = "TO_LANGUAGE";

    private String mFromLanguage;

    private String mToLanguage;

    private ListView mListView;

    private Button mClearButton;

    ArrayList<WordView> mWordViewList;

    private WordViewListAdapter mWordViewListAdapter;

    private SearchWordDAO mDao;

    public SearchWordLanguageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchWordLanguageFragment newInstance(String fromLanguage, String toLanguage) {
        SearchWordLanguageFragment fragment = new SearchWordLanguageFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fromLanguage);
        args.putString(ARG_PARAM2, toLanguage);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFromLanguage = getArguments().getString(ARG_PARAM1);
            mToLanguage = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_word_language, container, false);

        mListView = (ListView)view.findViewById(R.id.search_word_language_listview);

        mDao = new SearchWordDAO(getContext());

        // set listview
        mWordViewList = mDao.dbQueryCachedWords(mFromLanguage);

        mWordViewListAdapter = new WordViewListAdapter(getContext(), mWordViewList);

        mListView.setAdapter(mWordViewListAdapter);

        mClearButton = view.findViewById(R.id.search_word_language_button);

        mClearButton.setVisibility(mWordViewList.size() == 0 ? View.GONE : View.VISIBLE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), SearchWordResultActivity.class);

                TextView contentView = (TextView)view.findViewById(R.id.item_word_content);

                intent.putExtra("content", contentView.getText());

                intent.putExtra("fromLanguage", mFromLanguage);

                intent.putExtra("toLanguage", mToLanguage);

                startActivity(intent);
            }
        });

        // listen to clear button
        mClearButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                mDao.dbClearCacheCWord(mFromLanguage); // clear cache

                mWordViewList.clear();

                mWordViewListAdapter.notifyDataSetChanged(); // repaint
            }
        });

        return view;
    }

    /**
     * conduct an approximate match
     * @param content
     */
    public void querySuggestion(String content) {

        mWordViewList.clear();

        mWordViewList.addAll(mDao.dbQuerySuggestion(content, mFromLanguage));

        mWordViewListAdapter.notifyDataSetChanged(); // repaint

        mClearButton.setVisibility(View.GONE);
    }

    /**
     * conduct a complete match
     */
    public void queryCachedWords() {

        mWordViewList.clear();

        mWordViewList.addAll(mDao.dbQueryCachedWords(mFromLanguage));

        mWordViewListAdapter.notifyDataSetChanged();

        mClearButton.setVisibility(mWordViewList.size() == 0 ? View.GONE : View.VISIBLE);
    }
}
