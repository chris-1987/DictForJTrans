package jdict.com.christian.yi.wu.jdict.utility;

/**
 * Created by Administrator on 2017/8/19.
 */

public class MyPair<T1, T2> {

    public T1 first;

    public T2 second;

    MyPair(T1 first, T2 second) {

        this.first = first;

        this.second = second;
    }

    public static class StringPair extends MyPair<String, String> {

        public StringPair(String first, String second) {

            super(first, second);
        }
    }
}
