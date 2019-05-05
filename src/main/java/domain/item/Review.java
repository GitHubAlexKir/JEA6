package domain.item;

import domain.dto.ReviewDTO;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author Alex
 * Review entity
 **/
@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false)
    private String author;
    @Column(nullable = false)
    private int appreciation;
    @Column
    private String content;

    public Review() {
    }

    public Review(ReviewDTO reviewDTO) {
        this.author = reviewDTO.getAuthor();
        this.appreciation = reviewDTO.getAppreciation();
        this.content = reviewDTO.getContent();

    }
    //Getters en Setters
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
