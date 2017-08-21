package jdict.com.christian.yi.wu.jdict.db.searcharticle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jdict.com.christian.yi.wu.jdict.R;

/**
 * Created by Administrator on 2017/8/21.
 */

public class JBookListAdapter extends BaseAdapter {

        private Context mContext;

        private ArrayList<JBook> mJBookList;

        public JBookListAdapter(Context context, ArrayList<JBook> wordViewList) {

            mContext = context;

            mJBookList = wordViewList;
        }

        @Override
        public int getCount() {
            return mJBookList.size();
        }

        @Override
        public Object getItem(int i) {
            return mJBookList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            View view = null;

            if (convertView != null) {

                view = convertView;
            } else {

                view = View.inflate(mContext, R.layout.list_item_for_show_book, null);
            }

            //find controllers
            TextView item_book_author = (TextView) view.findViewById(R.id.item_show_book_author);
            TextView item_book_title = (TextView) view.findViewById(R.id.item_show_book_title);

            //find content
            JBook jBook = mJBookList.get(i);

            //set content
            item_book_author.setText(jBook.getAuthor());
            item_book_title.setText(jBook.getTitle());

            return view;
        }
    }

