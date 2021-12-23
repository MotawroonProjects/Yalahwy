package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class SliderDataModel implements Serializable {
    private List<SliderModel> data;
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<SliderModel> getData() {
        return data;
    }

    public static class SliderModel implements Serializable {
        private int id;
        private String subtitle_text;
        private String subtitle_size;
        private String subtitle_color;
        private String subtitle_anime;
        private String title_text;
        private String title_size;
        private String title_color;
        private String title_anime;
        private String details_text;
        private String details_size;
        private String details_color;
        private String details_anime;
        private String photo;
        private String position;
        private String link;
        private String key;

        public int getId() {
            return id;
        }

        public String getSubtitle_text() {
            return subtitle_text;
        }

        public String getSubtitle_size() {
            return subtitle_size;
        }

        public String getSubtitle_color() {
            return subtitle_color;
        }

        public String getSubtitle_anime() {
            return subtitle_anime;
        }

        public String getTitle_text() {
            return title_text;
        }

        public String getTitle_size() {
            return title_size;
        }

        public String getTitle_color() {
            return title_color;
        }

        public String getTitle_anime() {
            return title_anime;
        }

        public String getDetails_text() {
            return details_text;
        }

        public String getDetails_size() {
            return details_size;
        }

        public String getDetails_color() {
            return details_color;
        }

        public String getDetails_anime() {
            return details_anime;
        }

        public String getPhoto() {
            return photo;
        }

        public String getPosition() {
            return position;
        }

        public String getLink() {
            return link;
        }

        public String getKey() {
            return key;
        }
    }
}
