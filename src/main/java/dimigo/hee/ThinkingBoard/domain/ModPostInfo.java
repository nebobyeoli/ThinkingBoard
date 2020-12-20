package dimigo.hee.ThinkingBoard.domain;

// 게시글의 수정/삭제 같은 실행 정보를 주고받는 데에 사용.
// 게시글을 "Modify"한 "Info" 저장 클래스
public class ModPostInfo {
    private int id; // 수정/삭제된 게시글 id
    private String modType; // "edit", "delete"
    private String result; // "done", "fail", "noChange"

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.setId(Integer.parseInt(id));
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getModType() {
        return modType;
    }

    public void setModType(String modType) {
        this.modType = modType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
