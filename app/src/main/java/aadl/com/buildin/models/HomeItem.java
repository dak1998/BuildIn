package aadl.com.buildin.models;

public class HomeItem {

    private String title;
    private String thumbnail;
    private Class onClickClass;


    public HomeItem(String title, String thumbnail, Class onClickClass) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.onClickClass = onClickClass;
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
