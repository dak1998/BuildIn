package aadl.com.buildin.models;

import java.util.List;

public class IssueItem {
    private String id;
    private String title;
    private String description;
    private int upvotes;
    private int downvotes;
    private List<String> image_data;


    public IssueItem(String id, String title, String description, int upvotes, int downvotes, List<String> image_data) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.image_data = image_data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public List<String> getImage_data() {
        return image_data;
    }

    public void setImage_data(List<String> image_data) {
        this.image_data = image_data;
    }
}
