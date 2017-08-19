package jdict.com.christian.yi.wu.jdict.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ClearButton extends Button {

    private boolean mStatus;

    public ClearButton(Context context) {
        this(context, null);
    }

    public ClearButton(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();
    }

    private void init() {

        setClearButtonStatus(false);
    }

    public void setClearButtonStatus(boolean status) {

        mStatus = status;

        setHint(mStatus ? "查询" : "取消");
    }

    public boolean getStatus() {

        return mStatus;
    }
}
