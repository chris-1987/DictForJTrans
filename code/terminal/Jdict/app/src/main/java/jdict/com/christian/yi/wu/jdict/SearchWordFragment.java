package jdict.com.christian.yi.wu.jdict;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SearchWordFragment extends Fragment {

    private Button suggestion;

    private OnClickSuggestionListener mOnClickSuggestionListener;

    public SearchWordFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_word, container, false);

        suggestion = (Button) view.findViewById(R.id.search_word_button);

        suggestion.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                mOnClickSuggestionListener.onClickSuggestion();
            }
        });

        return view;
    }

    public static final SearchWordFragment newInstance() {

        SearchWordFragment fragment = new SearchWordFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    /*
    a callback interface
     */
    public interface OnClickSuggestionListener{

        public void onClickSuggestion();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof OnClickSuggestionListener) {

            mOnClickSuggestionListener = (OnClickSuggestionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();

        mOnClickSuggestionListener = null;
    }
}
