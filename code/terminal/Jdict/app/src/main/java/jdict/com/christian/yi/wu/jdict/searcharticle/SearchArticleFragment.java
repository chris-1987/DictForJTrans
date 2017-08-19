package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Date;

import jdict.com.christian.yi.wu.jdict.R;
import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;


public class SearchArticleFragment extends Fragment {

    String mVersion;

    SearchView mSearchView; // search view

    Button mToUpdateArticleButton;

    ImageButton mToUpdateArticleImageButton1;

    ImageButton mToUpdateArticleImageButton2;

    ImageButton mToUpdateArticleImageButton3;

    Button mToUnderWorkArticleButton;

    ImageButton mToUnderWorkArticleImageButton1;

    ImageButton mToUnderWorkArticleImageButton2;

    ImageButton mToUnderWorkArticleImageButton3;

    Button mToFinishedArticleButton;

    ImageButton mToFinishedArticleImageButton1;

    ImageButton mToFinishedArticleImageButton2;

    ImageButton mToFinishedArticleImageButton3;

    public SearchArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_article, container, false);

        // initialize search view
        mSearchView = (SearchView) view.findViewById(R.id.search_article_searchview);
        mSearchView.setIconifiedByDefault(false); // search block visible
        mSearchView.setSubmitButtonEnabled(false); // submit button visible
        mSearchView.onActionViewExpanded(); // action view visible

        // initialize components for update article
        mToUpdateArticleButton = (Button)view.findViewById(R.id.search_article_update_article_button);
        mToUpdateArticleImageButton1 = (ImageButton)view.findViewById(R.id.search_article_update_article_image_button1);
        mToUpdateArticleImageButton2 = (ImageButton)view.findViewById(R.id.search_article_update_article_image_button2);
        mToUpdateArticleImageButton3 = (ImageButton)view.findViewById(R.id.search_article_update_article_image_button3);

        // initialize components for underwork article
        mToUnderWorkArticleButton = (Button)view.findViewById(R.id.search_article_underwork_article_button);
        mToUnderWorkArticleImageButton1 = (ImageButton)view.findViewById(R.id.search_article_underwork_article_image_button1);
        mToUnderWorkArticleImageButton2 = (ImageButton)view.findViewById(R.id.search_article_underwork_article_image_button2);
        mToUnderWorkArticleImageButton3 = (ImageButton)view.findViewById(R.id.search_article_underwork_article_image_button3);

        // initialize components for finished article
        mToFinishedArticleButton = (Button)view.findViewById(R.id.search_article_finished_article_button);
        mToFinishedArticleImageButton1 = (ImageButton)view.findViewById(R.id.search_article_finished_article_image_button1);
        mToFinishedArticleImageButton2 = (ImageButton)view.findViewById(R.id.search_article_finished_article_image_button2);
        mToFinishedArticleImageButton3 = (ImageButton)view.findViewById(R.id.search_article_finished_article_image_button3);

        // pull contents from remote server
        try{

            SearchArticleRequestUtils requestUtils = new SearchArticleRequestUtils();

            requestUtils.refreshSearchArticleFragment(mVersion, new HttpCallBack() {
                @Override
                public void onSuccess(String result) {

                    Toast.makeText(getContext(), "success", Toast.LENGTH_LONG);
                }

                @Override
                public void onFailure(String exception) {

                    Toast.makeText(getContext(), "failure", Toast.LENGTH_LONG);
                }
            });
        }
        catch(Exception e) {

            e.printStackTrace();
        }

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
