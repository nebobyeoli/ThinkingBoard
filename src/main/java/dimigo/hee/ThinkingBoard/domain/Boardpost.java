package dimigo.hee.ThinkingBoard.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Boardpost
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 게시글 번호 - 고유 id
    private String title; // 게시글 제목
    private String contents; // 게시글 내용

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
