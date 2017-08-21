package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
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
    TextView mUnfinishedBookTextView1;
    TextView mUnfinishedBookTextView2;
    TextView mUnfinishedBookTextView3;
    Button mFinishedBookButton;
    ImageButton mFinishedBookImageButton1;
    ImageButton mFinishedBookImageButton2;
    ImageButton mFinishedBookImageButton3;
    TextView mFinishedBookTextView1;
    TextView mFinishedBookTextView2;
    TextView mFinishedBookTextView3;

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
        mUnfinishedBookTextView1 = (TextView) view.findViewById(R.id.search_article_unfinished_book_textview1);
        mUnfinishedBookTextView2 = (TextView) view.findViewById(R.id.search_article_unfinished_book_textview2);
        mUnfinishedBookTextView3 = (TextView) view.findViewById(R.id.search_article_unfinished_book_textview3);

        // initialize components for finished book
        mFinishedBookButton = (Button) view.findViewById(R.id.search_article_finished_book_button);
        mFinishedBookImageButton1 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button1);
        mFinishedBookImageButton2 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button2);
        mFinishedBookImageButton3 = (ImageButton) view.findViewById(R.id.search_article_finished_book_image_button3);
        mFinishedBookTextView1 = (TextView) view.findViewById(R.id.search_article_finished_book_textview1);
        mFinishedBookTextView2 = (TextView) view.findViewById(R.id.search_article_finished_book_textview2);
        mFinishedBookTextView3 = (TextView) view.findViewById(R.id.search_article_finished_book_textview3);

        SearchArticleRequestUtils requestUtils = new SearchArticleRequestUtils();
        requestUtils.refreshCachedBook(new HttpCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    if (!result.equals("no data")) {
                        Log.d("refreshCachedBook", result);
                        SearchArticleDAO dao = new SearchArticleDAO(getContext());
                        dao.clearCachedBook(); // clear db
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
                        dao.updateCachedBook(bookList);
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

        //download images for cached books
        SearchArticleDAO dao = new SearchArticleDAO(getContext());
        ArrayList<JBook> finishedBookList = new ArrayList<>();
        ArrayList<JBook> unfinishedBookList = new ArrayList<>();
        ArrayList<JChapter> chapterList = new ArrayList<>();
        dao.queryDB(finishedBookList, unfinishedBookList, chapterList);
        requestUtils.downloadImgForBook(finishedBookList, unfinishedBookList, new HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("downloadImgForBook", result);

                try {
                    JSONArray jArr = new JSONArray(result);
                    Log.d("arrlen", Integer.toString(jArr.length()));
                    for (int i = 0; i < jArr.length(); ++i) {
                        JSONObject json_data = jArr.getJSONObject(i);
                        String imgUrl = json_data.getString("imgUrl");
                        byte[] imgFile = Base64.decode(json_data.getString("imgFile"), Base64.DEFAULT);
                        FileUtils.createFile(imgUrl);
                        FileUtils.writeFile(imgFile, imgUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String exception) {

            }
        });

        // show cached books in the view (hard code)
        // TODO: better way with no hard code, should also modify sqlUtile
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            if (finishedBookList.size() > 0) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + finishedBookList.get(0).getImg_url());
                mFinishedBookImageButton1.setImageBitmap(bm);
                mFinishedBookTextView1.setText(finishedBookList.get(0).getTitle());
            }
            if (finishedBookList.size() > 1) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + finishedBookList.get(1).getImg_url());
                mFinishedBookImageButton2.setImageBitmap(bm);
                mFinishedBookTextView2.setText(finishedBookList.get(1).getTitle());
            }
            if (finishedBookList.size() > 2) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + finishedBookList.get(2).getImg_url());
                mFinishedBookImageButton3.setImageBitmap(bm);
                mFinishedBookTextView3.setText(finishedBookList.get(2).getTitle());
            }

            if (unfinishedBookList.size() > 0) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + unfinishedBookList.get(0).getImg_url());
                mUnfinishedBookImageButton1.setImageBitmap(bm);
                mUnfinishedBookTextView1.setText(unfinishedBookList.get(0).getTitle());
            }
            if (unfinishedBookList.size() > 1) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + unfinishedBookList.get(1).getImg_url());
                mUnfinishedBookImageButton2.setImageBitmap(bm);
                mUnfinishedBookTextView2.setText(unfinishedBookList.get(1).getTitle());
            }
            if (unfinishedBookList.size() > 2) {
                Bitmap bm = BitmapFactory.decodeFile(FileUtils.SDPATH + unfinishedBookList.get(2).getImg_url());
                mUnfinishedBookImageButton3.setImageBitmap(bm);
                mUnfinishedBookTextView3.setText(unfinishedBookList.get(2).getTitle());
            }
        }

        // set listeners to the controllers
        mUnfinishedBookButton.setOnClickListener(this);
        mUnfinishedBookImageButton1.setOnClickListener(this);
        mUnfinishedBookImageButton2.setOnClickListener(this);
        mUnfinishedBookImageButton3.setOnClickListener(this);
        mFinishedBookButton.setOnClickListener(this);
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
            intent.putExtra("isFinished", false);
            startActivity(intent);
        } else if (view == mFinishedBookButton) {
            Toast.makeText(getContext(), "here", Toast.LENGTH_LONG);
            Intent intent = new Intent(getActivity(), ShowBookActivity.class);
            intent.putExtra("isFinished", true);
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
