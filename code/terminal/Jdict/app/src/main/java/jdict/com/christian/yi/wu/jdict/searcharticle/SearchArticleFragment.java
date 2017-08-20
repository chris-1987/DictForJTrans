package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.JBook;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.JChapter;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.SearchArticleDAO;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.SearchArticleDatabaseHelper;
import jdict.com.christian.yi.wu.jdict.utility.FileUtils;
import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;


public class SearchArticleFragment extends Fragment implements View.OnClickListener {

    SearchView mSearchView; // search view

    Button mUnfinishedBookButton;

    ImageButton mUnfinishedBookImageButton1;

    ImageButton mUnfinishedBookImageButton2;

    ImageButton mUnfinishedBookImageButton3;

    Button mFinishedBookButton;

    ImageButton mFinishedBookImageButton1;

    ImageButton mFinishedBookImageButton2;

    ImageButton mFinishedBookImageButton3;

    public SearchArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_article, container, false);

        // initialize search view
        mSearchView = (SearchView) view.findViewById(R.id.search_book_searchview);
        mSearchView.setIconifiedByDefault(false); // search block visible
        mSearchView.setSubmitButtonEnabled(false); // submit button visible
        mSearchView.onActionViewExpanded(); // action view visible

        // initialize components for unfinished book
        mUnfinishedBookButton = (Button) view.findViewById(R.id.search_article_unfinished_book_button);
        mUnfinishedBookImageButton1 = (ImageButton) view.findViewById(R.id.search_article_unfinished_book_image_button1);
        mUnfinishedBookImageButton2 = (ImageButton) view.findViewById(R.id.search_article_unfinished_book_image_button2);
        mUnfinishedBookImageButton3 = (ImageButton) view.findViewById(R.id.search_article_unfinished_book_image_button3);

        // initialize components for finished book
        mFinishedBookButton = (Button) view.findViewById(R.id.search_article_finished_book_button);
        mFinishedBookImageButton1 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button1);
        mFinishedBookImageButton2 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button2);
        mFinishedBookImageButton3 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button3);

        // refresh cached jbooks on the local machine
        SearchArticleRequestUtils requestUtils = new SearchArticleRequestUtils();
        requestUtils.refreshCachedBook(new HttpCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    if (!result.equals("no data")) {
                        SearchArticleDAO dao = new SearchArticleDAO(getContext());
                        dao.clearDB(); // clear db
                        // update db
                        ArrayList<JBook> bookList = new ArrayList<JBook>();
                        JSONArray jArr = new JSONArray(result);
                        for (int i = 0; i < jArr.length(); ++i) {
                            JSONObject json_data = jArr.getJSONObject(i);
                            JBook book = new JBook();
                            book.setId(json_data.getInt("id"));
                            book.setAuthor(json_data.getString("author"));
                            book.setTitle(json_data.getString("title"));
                            book.setImg_url(json_data.getString("img_url"));
                            book.setSummary(json_data.getString("summary"));
                            book.setFinished(json_data.getInt("finished"));
                            bookList.add(book);
                        }
                        ArrayList<JChapter> chapterList = new ArrayList<JChapter>();
                        dao.updateDB(bookList, chapterList);
                    } else {

                        Log.d("refreshCachedBook", "no data");
                        // do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String exception) {
                //TODO
            }
        });

        //download img for cached books

//        requestUtils.downloadImgForCachedBook(null, null, new HttpCallBack(){
//            @Override
//            public void onFailure(String exception) {
//
//            }
//
//            @Override
//            public void onSuccess(String result) {
//
//            }
//        });


        mUnfinishedBookButton.setOnClickListener(this);
        mUnfinishedBookImageButton1.setOnClickListener(this);
  //      mUnfinishedBookImageButton1.setImageResource();
        mUnfinishedBookImageButton2.setOnClickListener(this);
  //      mUnfinishedBookImageButton1.setImageResource();
        mUnfinishedBookImageButton3.setOnClickListener(this);
  //      mUnfinishedBookImageButton1.setImageResource();

        mUnfinishedBookButton.setOnClickListener(this);
        mFinishedBookImageButton1.setOnClickListener(this);
        mFinishedBookImageButton2.setOnClickListener(this);
        mFinishedBookImageButton3.setOnClickListener(this);

        return view;
    }

    public static final SearchArticleFragment newInstance() {
        SearchArticleFragment fragment = new SearchArticleFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        if (view == mUnfinishedBookButton) {
            Intent intent = new Intent(getActivity(), ShowBookActivity.class);
            intent.putExtra("type", "unfinished");
            startActivity(intent);
        } else if (view == mFinishedBookButton) {
            Intent intent = new Intent(getActivity(), ShowBookActivity.class);
            intent.putExtra("type", "finished");
            startActivity(intent);
        } else if (view == mUnfinishedBookImageButton1) {


        } else if (view == mUnfinishedBookImageButton2) {


        } else if (view == mUnfinishedBookImageButton3) {


        } else if (view == mFinishedBookImageButton1) {


        } else if (view == mFinishedBookImageButton2) {


        } else if (view == mFinishedBookImageButton3) {

        }

    }
}
