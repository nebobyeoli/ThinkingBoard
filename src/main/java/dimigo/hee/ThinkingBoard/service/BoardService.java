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

    /*** 기본 제공 함수들 ***/

    // 주입된 boardRepository의 함수들을 사용

    public int register(Boardpost post) {
        boardRepository.save(post);
        return post.getId();
    }

    public Boardpost findById(int id) {
        return boardRepository.findById(id);
    }

    public ArrayList<Boardpost> findAll() {
        return boardRepository.findAll();
    }

    public Boardpost deleteById(int id) {
        return boardRepository.deleteById(id);
    }

    /*** 기본 함수 활용 -- 보조 함수들 ***/

    public int register(PostForm post) {
        Boardpost boardPost = new Boardpost(); // 새 게시글: 데이터 저장 형식으로 자동 변환하여 저장해 줌

        // PostForm과 BoardPost의 차이

        // PostForm: category를 checkbox에서 String[]으로 받음
        // Boardpost: category의 DB 저장을 위해 '/' 문자를 기준으로 String[] 형태를 join함

        // 입력 받을 때 String[] 말고 String 형태로 받으면 ',' 문자를 기준으로 자동 String 변환되지만,
        // 이 경우 카테고리에 ',' 문자를 사용할 수 없게 됨 - 기타 카테고리를 입력 받는 것을 가능하게 할 경우 등..
        // 추후 '/'보다 빈도 낮은 문자 사용하는 것도 나쁘지 않은 방법임.

        boardPost.setTitle(post.getTitle());
        boardPost.setCategory(post.getCategory());
        boardPost.setContents(post.getContents());
        boardPost.setPassword(post.getPassword());

        return this.register(boardPost);
    }

    // id 입력이 String인 경우 사용.

    public Boardpost findById(String id) {
        return this.findById(Integer.parseInt(id));
    }

    public Boardpost deleteById(String id) {
        return this.deleteById(Integer.parseInt(id));
    }

    // findBy ~ Contain : 공백 문자 (띄어쓰기)를 기준으로 각 "단어"가 모두 포함된 글 list를
    // findAll 결과에서 추려내어 반환.

    public ArrayList<Boardpost> findByIdContain(String ids) {
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

    public ArrayList<Boardpost> findByTitleContain(String title) {
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
    public ArrayList<Boardpost> findByCategoryContain(String category) {
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

    public ArrayList<Boardpost> findByContentsContain(String contents) {
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

    public Boardpost delete(Boardpost post) {
        return this.deleteById(post.getId());
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
