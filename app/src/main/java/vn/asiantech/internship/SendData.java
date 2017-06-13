package vn.asiantech.internship;

import java.io.Serializable;

/**
 * Created by datbu on 14-06-2017.
 */

class SendData implements Serializable {
    private MainActivity.OnClick listener;

    SendData(MainActivity.OnClick listener) {
        this.listener = listener;
    }

    MainActivity.OnClick getListener() {
        return listener;
    }

    public void setListener(MainActivity.OnClick listener) {
        this.listener = listener;
    }
}
