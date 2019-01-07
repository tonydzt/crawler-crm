package com.teamdev.jxbrowser.chromium;

public class Tab {
    private final TabCaption caption;
    private final TabContent content;

    Tab(TabCaption caption, TabContent content) {
        this.caption = caption;
        this.content = content;
    }

    public TabCaption getCaption() {
        return caption;
    }

    public TabContent getContent() {
        return content;
    }
}
