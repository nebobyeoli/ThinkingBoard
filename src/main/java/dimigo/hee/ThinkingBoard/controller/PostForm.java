package dimigo.hee.ThinkingBoard.controller;

/**
 * <h2> PostForm과 BoardPost의 차이 </h2><br>
 *
 * PostForm: category를 checkbox에서 String[]으로 받음 <br>
 * Boardpost: category의 DB 저장을 위해 '/' 문자를 기준으로 String[] 형태를 join함 <br><br>
 *
 * 입력 받을 때 String[] 말고 String 형태로 받으면 ',' 문자를 기준으로 자동 String 변환되지만, <br>
 * 이 경우 카테고리에 ',' 문자를 사용할 수 없게 됨 - 기타 카테고리를 입력 받는 것을 가능하게 할 경우 등.. <br>
 * 추후 '/'보다 빈도 낮은 문자 사용하는 것도 나쁘지 않은 방법임.
 */
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

    /**
     * String[] type → String : 첫 번째 방법 <br><br>
     *
     * 두 번째 방법: BoardController.java > register 함수에서 <br>
     * bp.setCategory(String.join("/", postForm.getCategory())); 설정 <br><br>
     *
     * 세 번째 방법: BoardService.java > register 에서 <br>
     * bp.setCategory(String.join("/", postForm.getCategory())); 설정 <br><br>
     *
     * 0번째 방법: 그냥 입력받을 때부터 배열 말고 그냥 String으로 받는다 (','로 구분되어 반환됨)
     */
    public void setCategory(String[] category) {
        this.category = String.join("/", category);
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
