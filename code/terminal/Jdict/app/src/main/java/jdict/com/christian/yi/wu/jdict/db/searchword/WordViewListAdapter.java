package jdict.com.christian.yi.wu.jdict.db.searchword;

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

public class WordViewListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<WordView> mWordViewList;

    public WordViewListAdapter(Context context, ArrayList<WordView> wordViewList) {

        mContext = context;

        mWordViewList = wordViewList;
    }

    @Override
    public int getCount() {
        return mWordViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordViewList.get(i);
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
        WordView wordview = mWordViewList.get(i);

        //设置内容
        item_word_content.setText(wordview.getContent());
        item_word_meaning.setText(wordview.getMeaning());

        return view;
    }
}
