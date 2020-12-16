package dimigo.hee.ThinkingBoard.controller;

// 게시글 형식
public class PostForm
{
    private String title; // 게시글 제목
    private String contents; // 게시글 내용

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
