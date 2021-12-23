package com.yalahwy.models;

import java.io.Serializable;

public class SocialSettingsModel implements Serializable {
    public Data data;
    public String message;
    public int status;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public class Data{
        public int id;
        public String facebook;
        public String gplus;
        public String twitter;
        public String linkedin;
        public String dribble;
        public int f_status;
        public int g_status;
        public int t_status;
        public int l_status;
        public int d_status;
        public int f_check;
        public int g_check;
        public String fclient_id;
        public String fclient_secret;
        public String fredirect;
        public String gclient_id;
        public String gclient_secret;
        public String gredirect;


        public int getId() {
            return id;
        }

        public String getFacebook() {
            return facebook;
        }

        public String getGplus() {
            return gplus;
        }

        public String getTwitter() {
            return twitter;
        }

        public String getLinkedin() {
            return linkedin;
        }

        public String getDribble() {
            return dribble;
        }

        public int getF_status() {
            return f_status;
        }

        public int getG_status() {
            return g_status;
        }

        public int getT_status() {
            return t_status;
        }

        public int getL_status() {
            return l_status;
        }

        public int getD_status() {
            return d_status;
        }

        public int getF_check() {
            return f_check;
        }

        public int getG_check() {
            return g_check;
        }

        public String getFclient_id() {
            return fclient_id;
        }

        public String getFclient_secret() {
            return fclient_secret;
        }

        public String getFredirect() {
            return fredirect;
        }

        public String getGclient_id() {
            return gclient_id;
        }

        public String getGclient_secret() {
            return gclient_secret;
        }

        public String getGredirect() {
            return gredirect;
        }
    }

}
