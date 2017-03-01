package com.slim.me.ganker.ui.event;

/**
 * Created by Slim on 2017/3/1.
 */

public class JumpToWebEvent {

    public final String url;
    public final String title;

    public JumpToWebEvent(String url, String title) {
        this.url = url;
        this.title = title;
    }
}
