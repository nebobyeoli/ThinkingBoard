package dimigo.hee.ThinkingBoard.service;

import dimigo.hee.ThinkingBoard.controller.PostForm;
import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.domain.ModPostInfo;
import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 트랜잭션 = 하나의 일처리 단위 <br>
 * 설정 = async 일 때 micro 단위의 순서 정함
 */
@Transactional
public class BoardService
{
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*** SAVE ***/

    public int register(Boardpost bp) {
        boardRepository.save(bp);
        return bp.getId();
    }

    public int register(PostForm post) {
        Boardpost bp = new Boardpost(); // 새 게시글: 데이터 저장 형식으로 자동 변환하여 저장해 줌
        bp.setTitle(post.getTitle());
        bp.setCategory(post.getCategory());
        bp.setContent(post.getContent());
        bp.setPassword(post.getPassword());
        return this.register(bp);
    }

    /*** FIND ***/

    public ArrayList<Boardpost> findAll() {
        return boardRepository.findAll();
    }

    public Boardpost findById(int id) {
        return boardRepository.findById(id);
    }
    public Boardpost findById(String id) {
        return boardRepository.findById(Integer.parseInt(id));
    }

    /** findBy ~ Contains : 공백 문자 (띄어쓰기)를 기준으로 <br>
     * 각 "단어"가 모두 포함된 글 list를 findAll 결과에서 추려내어 반환.
     */
    public ArrayList<Boardpost> findByContainsId(String ids) {
        if (ids.equals("")) return this.findAll();

        String[] idsArr = ids.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost bp : this.findAll()) {
            int contains = 0;
            for (String id : idsArr) if (Integer.toString(bp.getId()).toLowerCase(Locale.ROOT).contains(id.toLowerCase(Locale.ROOT))) contains++;
            if (contains == idsArr.length) list.add(bp);
        }
        return list;
    }

    public ArrayList<Boardpost> findByContainsTitle(String title) {
        if (title.equals("")) return this.findAll();

        String[] titleArr = title.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost bp : this.findAll()) {
            int contains = 0;
            for (String word : titleArr) if (bp.getTitle().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))) contains++;
            if (contains == titleArr.length) list.add(bp);
        }
        return list;
    }

    /**
     * 카테고리로 검색: <br>
     * 공백 대신 checkbox 자동변환 join 구분자인 ',' 문자를 기준으로 카테고리 분리.
     */
    public ArrayList<Boardpost> findByContainsCategory(String category) {
        if (category == null) return this.findAll();

        String[] catArr = category.split(",");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost bp : this.findAll()) {
            int contains = 0;
            for (String cat : catArr) if (bp.getCategory().toLowerCase(Locale.ROOT).contains(cat.toLowerCase(Locale.ROOT))) contains++;
            if (contains == catArr.length) list.add(bp);
        }
        return list;
    }

    public ArrayList<Boardpost> findByContainsContents(String content) {
        if (content.equals("")) return this.findAll();

        String[] contsArr = content.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost bp : this.findAll()) {
            int contains = 0;
            for (String word : contsArr) if (bp.getContent().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))) contains++;
            if (contains == contsArr.length) list.add(bp);
        }
        return list;
    }

    /*** EDIT ***/

    public ModPostInfo editPost(int id, PostForm post) {

        ModPostInfo mpInfo = new ModPostInfo();
        mpInfo.setId(id);
        mpInfo.setModType("edit");

        Boardpost editedPost = this.findById(id);
        boolean edited = false; // "수정됨" 여부 저장 boolean

        if (!editedPost.getTitle().equals(post.getTitle())) {
            editedPost.setTitle(post.getTitle());
            edited = true;
        }
        if (!editedPost.getCategory().equals(post.getCategory())) {
            editedPost.setCategory(post.getCategory());
            edited = true;
        }
        if (!editedPost.getContent().equals(post.getContent())) {
            editedPost.setContent(post.getContent());
            edited = true;
        }
        if (editedPost.getPassword().equals("") && !post.getPassword().equals("")) {
            editedPost.setPassword(post.getPassword());
            edited = true;
        }
        else if (!editedPost.getPassword().equals(post.getPassword())) {
            editedPost.setPassword(post.getPassword());
            edited = true;
        }

        mpInfo.setResult(edited ? "done" : "noChange");
        return mpInfo;
    }
    public ModPostInfo editPost(String id, PostForm post) {
        return this.editPost(Integer.parseInt(id), post);
    }

    /*** CONFIRM ***/

    /**
     * return true: <br>
     * - 비밀번호가 설정되지 않은 글은 어떤 비밀번호를 넣어도 비밀번호가 일치한다고 간주함 <br>
     * - 비밀번호가 일치하는 경우
     */
    public boolean hasIdPost(int id, String password) {
        Boardpost bp = this.findById(id);
        return (bp.getPassword() != null) || bp.getPassword().equals(password);
    }
    public boolean hasIdPost(String id, String password) {
        return this.hasIdPost(Integer.parseInt(id), password);
    }

    /*** DELETE ***/

    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }
    public void deleteById(String id) {
        boardRepository.deleteById(Integer.parseInt(id));
    }
}
