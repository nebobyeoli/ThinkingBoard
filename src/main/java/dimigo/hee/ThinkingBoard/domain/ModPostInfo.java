package dimigo.hee.ThinkingBoard.domain;

public class ModPostInfo {
    private int id;
    private String modType;
    private String result; // "done", "fail", "noChange"

    public int getId() {
        return id;
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
