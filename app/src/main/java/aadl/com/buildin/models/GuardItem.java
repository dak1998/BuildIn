package aadl.com.buildin.models;

public class GuardItem {
    private String id;
    private String name;
    private String contact;
    private String shift;

    public GuardItem(String id, String name, String contact, String shift) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.shift = shift;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
