package dimigo.hee.ThinkingBoard.controller;

// 게시글 형식
public class PostForm
{
    private String title; // 게시글 제목
    private String contents; // 게시글 내용
    private String category; // 해당하는 카테고리

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = String.join("/", category); // String array > 첫 번째 방법

        // 두 번째 방법: BoardController.java > register 함수에서
        // bp.setCategory(String.join("/", postForm.getCategory())); 설정

        // 세 번째 방법: BoardService.java > register 에서
        // boardPost.setCategory(String.join("/", postForm.getCategory())); 설정
    }
}
