package dimigo.hee.ThinkingBoard.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 게시글 형식 - DB 저장용 <br>
 * PostForm과의 구체적 차이는 PostForm.java에 있음
 */
@Entity
public class Boardpost
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 게시글 번호 - 고유 id
    private String title; // 게시글 제목
    private String category; // 게시글 카테고리
    private String contents; // 게시글 내용
    private String password; // 게시글 비밀번호

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
