package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.JBook;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.JBookListAdapter;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.SearchArticleDAO;
import jdict.com.christian.yi.wu.jdict.db.searchword.WordViewListAdapter;
import jdict.com.christian.yi.wu.jdict.searchword.SearchWordResultActivity;
import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;

/**
 * do: create an instance of this fragment.
 */
public class ShowBookFragment extends Fragment {
    private static final String ARG_PARAM1 = "isFinished";

    private boolean mIsFinished;

    private ListView mListView;

    private int mFetchedNum = 0; // number of fetches

    private static final int mFetchBookNum = 5; // number of books to fetch in a time

    private SearchArticleDAO mDao;

    private ArrayList<JBook> mBookList;

    private JBookListAdapter mBookListAdapter;

    public ShowBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isFinished finished or unfinished.
     * @return A new instance of fragment ShowBookFragment.
     */
    public static ShowBookFragment newInstance(boolean isFinished) {
        ShowBookFragment fragment = new ShowBookFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isFinished);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsFinished = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_book, container, false);

        mListView = (ListView) view.findViewById(R.id.show_book_listview);

        mBookList = new ArrayList<>();

        mBookListAdapter = new JBookListAdapter(getContext(), mBookList);

        mListView.setAdapter(mBookListAdapter);

        showMoreBooks();
        
//        mBookViewList = mDao.dbQueryCachedBooks();
//        // set listview
//        mWordViewList = mDao.dbQueryCachedWords(mFromLanguage);
//
//        mWordViewListAdapter = new WordViewListAdapter(getContext(), mWordViewList);
//
//        mListView.setAdapter(mWordViewListAdapter);

        return view;
    }

    private void showMoreBooks() {

        // fetch book from the remote server, first time
        SearchArticleRequestUtils requestUtils = new SearchArticleRequestUtils();
        requestUtils.retrieveBook(mFetchedNum, mFetchBookNum, mIsFinished, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {

                mFetchedNum += mFetchBookNum;

                try {
                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); ++i) {

                        JSONObject json_data = jArray.getJSONObject(i);

                        JBook book = new JBook();

                        book.setAuthor(json_data.getString("author"));
                        book.setSummary(json_data.getString("summary"));
                        book.setImg_url(json_data.getString("img_url"));
                        book.setFinished(json_data.getInt("finished"));
                        book.setId(json_data.getInt("id"));
                        book.setTitle(json_data.getString("title"));
                        mBookList.add(book);
                    }

                    mBookListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String exception) {


            }
        });
    }
}

