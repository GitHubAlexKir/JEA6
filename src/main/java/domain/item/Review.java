package domain.item;

import domain.dto.ReviewDTO;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Auteur mag niet leeg zijn.")
    private String author;
    @Min(value = 1,message = "Waardering moet minimaal 1 zijn.")
    @Max(value = 5, message = "Waardering moet maximaal 5 zijn.")
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
