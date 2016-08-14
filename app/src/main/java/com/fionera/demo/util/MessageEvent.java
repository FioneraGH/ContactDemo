package com.fionera.demo.util;

/**
 * Created by fionera on 16-8-14.
 */

public class MessageEvent {
    public static class SearchEvent{
        private String searchContent;

        public SearchEvent(String searchContent) {
            this.searchContent = searchContent;
        }

        public String getSearchContent() {
            return searchContent;
        }

        public void setSearchContent(String searchContent) {
            this.searchContent = searchContent;
        }
    }
}
