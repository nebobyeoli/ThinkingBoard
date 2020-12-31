package dimigo.hee.ThinkingBoard.service;

import dimigo.hee.ThinkingBoard.controller.PostForm;
import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.domain.ModPostInfo;
import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Locale;

@Transactional
// 트랜잭션 = 하나의 일처리 단위
// 설정 = async 일 때 micro 단위의 순서 정함
public class BoardService
{
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public int register(Boardpost bp) {
        boardRepository.save(bp);
        return bp.getId();
    }

    public int register(PostForm post) {
        Boardpost bp = new Boardpost(); // 새 게시글: 데이터 저장 형식으로 자동 변환하여 저장해 줌
        bp.setTitle(post.getTitle());
        bp.setCategory(post.getCategory());
        bp.setContents(post.getContents());
        bp.setPassword(post.getPassword());
        return this.register(bp);
    }

    public Boardpost findById(int id) {
        return boardRepository.findById(id);
    }

    public Boardpost findById(String id) {
        return boardRepository.findById(Integer.parseInt(id));
    }

    public ArrayList<Boardpost> findAll() {
        return boardRepository.findAll();
    }

    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }

    public void deleteById(String id) {
        boardRepository.deleteById(Integer.parseInt(id));
    }

    /** findBy ~ Contains : 공백 문자 (띄어쓰기)를 기준으로 <br>
     * 각 "단어"가 모두 포함된 글 list를 findAll 결과에서 추려내어 반환.
     */
    public ArrayList<Boardpost> findByIdContains(String ids) {
        if (ids.equals("")) return this.findAll();

        String[] idsArr = ids.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            int contains = 0;
            for (String id : idsArr) if (Integer.toString(post.getId()).toLowerCase(Locale.ROOT).contains(id.toLowerCase(Locale.ROOT))) contains++;
            if (contains == idsArr.length) list.add(post);
        }
        return list;
    }

    public ArrayList<Boardpost> findByTitleContains(String title) {
        if (title.equals("")) return this.findAll();

        String[] titleArr = title.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            int contains = 0;
            for (String word : titleArr) if (post.getTitle().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))) contains++;
            if (contains == titleArr.length) list.add(post);
        }
        return list;
    }

    // 카테고리로 검색 : 공백 대신 checkbox 자동변환 join 구분자인 ',' 문자를 기준으로 카테고리 분리.
    public ArrayList<Boardpost> findByCategoryContains(String category) {
        if (category == null) return this.findAll();

        String[] catArr = category.split(",");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            int contains = 0;
            for (String cat : catArr) if (post.getCategory().toLowerCase(Locale.ROOT).contains(cat.toLowerCase(Locale.ROOT))) contains++;
            if (contains == catArr.length) list.add(post);
        }
        return list;
    }

    public ArrayList<Boardpost> findByContentsContains(String contents) {
        if (contents.equals("")) return this.findAll();

        String[] contsArr = contents.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            int contains = 0;
            for (String word : contsArr) if (post.getContents().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))) contains++;
            if (contains == contsArr.length) list.add(post);
        }
        return list;
    }

    public void delete(Boardpost post) {
        this.deleteById(post.getId());
    }

    public Boardpost getMatchingPost(String id, String password) {
        return this.getMatchingPost(Integer.parseInt(id), password);
    }

    public Boardpost getMatchingPost(int id, String password) {
        Boardpost post = this.findById(id);
        if (post.getPassword() == null) return post; // 비밀번호가 설정되지 않은 글은 어떤 비밀번호를 넣어도 비밀번호가 일치한다고 간주함
        if (post.getPassword().equals(password)) return post; // 비밀번호가 일치하는 경우
        return null; // 비밀번호가 아닌 경우
    }

    public ModPostInfo editPost(String id, PostForm post) {
        return this.editPost(Integer.parseInt(id), post);
    }

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
        if (!editedPost.getContents().equals(post.getContents())) {
            editedPost.setContents(post.getContents());
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
}
