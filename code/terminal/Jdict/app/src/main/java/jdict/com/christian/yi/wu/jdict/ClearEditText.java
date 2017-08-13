package jdict.com.christian.yi.wu.jdict;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jdict.com.christian.yi.wu.jdict.R;

public class ClearEditText extends EditText {

    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {

        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {

        mClearDrawable = getCompoundDrawables()[2]; // get the drawable on the right side of the textview

        if (mClearDrawable == null) {

            mClearDrawable = getResources().getDrawable(R.drawable.delete_selector);  // deprecated
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

        setClearIconStatus(false); // set to invisible
    }

    /**
     * If push the clear button, clear the text in the editview.
     * Check if the motion occurs at the the location where the clear button is placed. If yes, clear the text in the editview
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public void setClearIconStatus(boolean status) {

        Drawable right = status ? mClearDrawable : null;

        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]); // reset the drawable according to status
    }
}
