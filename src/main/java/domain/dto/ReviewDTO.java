package domain.dto;
/**
 * @author Alex
 * ReviewDTO class
 **/
public class ReviewDTO {
    private long id;
    private String author;
    private int appreciation;
    private String content;

    public ReviewDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(int appreciation) {
        this.appreciation = appreciation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", appreciation=" + appreciation +
                ", content='" + content + '\'' +
                '}';
    }
}
