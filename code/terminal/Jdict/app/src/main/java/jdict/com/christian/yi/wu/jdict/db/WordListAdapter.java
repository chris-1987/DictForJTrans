package jdict.com.christian.yi.wu.jdict.db;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.R;

/**
 * Created by Administrator on 2017/8/14.
 */

public class WordListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Word> mWordList;

    public WordListAdapter(Context context, ArrayList<Word> wordList) {

        mContext = context;

        mWordList = wordList;
    }

    @Override
    public int getCount() {
        return mWordList.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view = null;

        if(convertView != null){

            view = convertView;
        }else {

            view =  View.inflate(mContext, R.layout.list_item, null);
        }

        //找到控件
        TextView item_word_content = (TextView) view.findViewById(R.id.item_word_content);
        TextView item_word_meaning = (TextView) view.findViewById(R.id.item_word_meaning);

        //找到内容
        Word word = mWordList.get(i);

        //设置内容
        item_word_content.setText(word.getContent());
        item_word_meaning.setText(word.getMeaning());

        return view;
    }
}
