package aadl.com.buildin.models;

import java.util.ArrayList;
import java.util.List;

import aadl.com.buildin.MainActivity;
import aadl.com.buildin.activities.events.EventsActivity;
import aadl.com.buildin.activities.issues.IssuesActivity;
import aadl.com.buildin.activities.notices.NoticesActivity;

public class HomeItem {

    private String title;
    private String thumbnail;
    private Class onClickClass;


    public HomeItem(String title, String thumbnail, Class onClickClass) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.onClickClass = onClickClass;
    }

    public static class Helper {
         public static List<HomeItem> guestMenuItems(Class className) {
             List<HomeItem> items = new ArrayList<>();

             items.add(new HomeItem("Events", "http://192.168.0.100:8999/calendar.png", className));
             items.add(new HomeItem("Notices", "http://192.168.0.100:8999/protest.png", className));
             items.add(new HomeItem("Issues", "http://192.168.0.100:8999/pros-and-cons.png", className));
             items.add(new HomeItem("Contact Guards", "http://192.168.0.100:8999/policeman.png", className));
             items.add(new HomeItem("Pay Maintenance Bill", "http://192.168.0.100:8999/payment.png", className));

             return items;
        }

        public static List<HomeItem> signInMenuItems() {
            List<HomeItem> items = new ArrayList<>();

            items.add(new HomeItem("Events", "http://192.168.0.100:8999/calendar.png", EventsActivity.class));
            items.add(new HomeItem("Notices", "http://192.168.0.100:8999/protest.png",  NoticesActivity.class));
            items.add(new HomeItem("Issues", "http://192.168.0.100:8999/pros-and-cons.png",  IssuesActivity.class));
            items.add(new HomeItem("Contact Guards", "http://192.168.0.100:8999/policeman.png",  MainActivity.class));
            items.add(new HomeItem("Pay Maintenance Bill", "http://192.168.0.100:8999/payment.png",  MainActivity.class));

            return items;
        }

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getOnClickClass() {
        return onClickClass;
    }

    public void setOnClickClass(Class onClickClass) {
        this.onClickClass = onClickClass;
    }
}

