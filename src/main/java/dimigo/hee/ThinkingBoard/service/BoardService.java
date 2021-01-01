package dimigo.hee.ThinkingBoard.service;

import dimigo.hee.ThinkingBoard.controller.PostForm;
import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.domain.ModPostInfo;
import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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

    /*** CONFIRM ***/

    /**
     * 비밀번호가 설정되지 않은 경우 (어떤 비밀번호를 넣어도 비밀번호가 일치한다고 간주) <br>
     * 비밀번호가 일치하는 경우
     * <pre>return true</pre>
     */
    public boolean hasPostofId(int id, String password) {
        return boardRepository.hasPostofId(id, password);
    }
    public boolean hasPostofId(String id, String password) {
        return boardRepository.hasPostofId(Integer.parseInt(id), password);
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

    public ArrayList<Boardpost> findAllHasIds(String ids) {
        return boardRepository.findAllHasIds(this.toKeyArr(ids, " "));
    }

    public ArrayList<Boardpost> findAllHasTitles(String titles, String mode) {
        return boardRepository.findAllHasTitles(this.toKeyArr(titles, " "), mode);
    }

    public ArrayList<Boardpost> findAllHasCategories(String categories, String mode) {
        return boardRepository.findAllHasCategories(this.toKeyArr(categories, " "), mode);
    }

    public ArrayList<Boardpost> findAllHasContents(String contents, String mode) {
        return boardRepository.findAllHasContents(this.toKeyArr(contents, ","), mode);
    }

    /**
     * String to (distinct & !hasEmpty) StringArray
     */
    public String[] toKeyArr(String s, String regex) {
        return Arrays.stream(s.split(regex))
                .distinct() // no duplicates
                .filter(v -> v != null && v.length() > 0)
                .toArray(String[]::new);
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

    /*** DELETE ***/

    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }
    public void deleteById(String id) {
        boardRepository.deleteById(Integer.parseInt(id));
    }
}
