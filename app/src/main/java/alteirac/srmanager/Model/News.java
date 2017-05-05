package alteirac.srmanager.Model;

/**
 * Created by Guillaume on 05/05/2017.
 */

public class News {

    private String title;
    private String desc;
    private int id;

    public News(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
