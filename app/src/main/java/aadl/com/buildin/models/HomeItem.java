package aadl.com.buildin.models;

import java.util.ArrayList;
import java.util.List;

import aadl.com.buildin.MainActivity;
import aadl.com.buildin.R;
import aadl.com.buildin.activities.ComingSoonActivity;
import aadl.com.buildin.activities.events.EventsActivity;
import aadl.com.buildin.activities.guards.GuardsActivity;
import aadl.com.buildin.activities.issues.IssuesActivity;
import aadl.com.buildin.activities.notices.NoticesActivity;

public class HomeItem {

    private String title;
    private int thumbnail;
    private Class onClickClass;


    public HomeItem(String title, int thumbnail, Class onClickClass) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.onClickClass = onClickClass;
    }

    public static class Helper {
         public static List<HomeItem> guestMenuItems(Class className) {
             List<HomeItem> items = new ArrayList<>();


             items.add(new HomeItem("Events", R.drawable.calendar, className));
             items.add(new HomeItem("Notices", R.drawable.protest,  className));
             items.add(new HomeItem("Issues", R.drawable.pros_and_cons,  className));
             items.add(new HomeItem("Contact Guards", R.drawable.policeman,  className));
             items.add(new HomeItem("Pay Maintenance Bill", R.drawable.payment,  className));
             return items;
        }

        public static List<HomeItem> signInMenuItems() {
            List<HomeItem> items = new ArrayList<>();

            items.add(new HomeItem("Events", R.drawable.calendar, EventsActivity.class));
            items.add(new HomeItem("Notices", R.drawable.protest,  NoticesActivity.class));
            items.add(new HomeItem("Issues", R.drawable.pros_and_cons,  IssuesActivity.class));
            items.add(new HomeItem("Contact Guards", R.drawable.policeman,  GuardsActivity.class));
            items.add(new HomeItem("Pay Maintenance Bill", R.drawable.payment,  ComingSoonActivity.class));

            return items;
        }

    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
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

