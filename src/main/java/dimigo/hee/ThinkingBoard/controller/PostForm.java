package dimigo.hee.ThinkingBoard.controller;

// 게시글 형식 - 폼 입력받는 틀용
// PostForm과 Boardpost의 구체적 차이는 BoardService.java의 register 부분에 작성되어 있
public class PostForm
{
    private String title; // 게시글 제목
    private String category; // 해당하는 카테고리
    private String contents; // 게시글 내용
    private String password; // 게시글 비밀번호

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = String.join("/", category); // String[] type >> String : 첫 번째 방법

        // 두 번째 방법: BoardController.java > register 함수에서
        // bp.setCategory(String.join("/", postForm.getCategory())); 설정

        // 세 번째 방법: BoardService.java > register 에서
        // boardPost.setCategory(String.join("/", postForm.getCategory())); 설정

        // -- 0번째 방법: 그냥 입력받을 때부터 배열 말고 그냥 String으로 받는다 --
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
