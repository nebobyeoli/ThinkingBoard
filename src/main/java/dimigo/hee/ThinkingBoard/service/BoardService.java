package dimigo.hee.ThinkingBoard.service;

import dimigo.hee.ThinkingBoard.controller.PostForm;
import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.domain.ModPostInfo;
import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;

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
        Boardpost boardPost = new Boardpost(); // 새 게시글

        boardPost.setTitle(post.getTitle());
        boardPost.setCategory(post.getCategory());
        boardPost.setContents(post.getContents());
        boardPost.setPassword(post.getPassword());

        return this.register(boardPost);
    }

    public ArrayList<Boardpost> findByIdContain(String ids) {
        if (ids.equals("")) return this.findAll();

        String[] idsArr = ids.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            for (String id : idsArr)
                if (Integer.toString(post.getId()).contains(id)) {
                    list.add(post);
                    break;
                }
        }
        return list;
    }

    public ArrayList<Boardpost> findByTitleContain(String title) {
        if (title.equals("")) return this.findAll();

        String[] titleArr = title.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            for (String word : titleArr)
                if (post.getTitle().contains(word)) {
                    list.add(post);
                    break;
                }
        }
        return list;
    }

    public ArrayList<Boardpost> findByCategoryContain(String category) {
        if (category == null) return this.findAll();

        String[] catArr = category.split(",");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            for (String cat : catArr)
                if (post.getCategory().contains(cat)) {
                    list.add(post);
                    break;
                }
        }
        return list;
    }

    public ArrayList<Boardpost> findByContentsContain(String contents) {
        if (contents.equals("")) return this.findAll();

        String[] contsArr = contents.split(" ");
        ArrayList<Boardpost> list = new ArrayList<>();

        for (Boardpost post : this.findAll()) {
            for (String word : contsArr)
                if (post.getContents().contains(word)) {
                    list.add(post);
                    break;
                }
        }
        return list;
    }

    public Boardpost delete(Boardpost post) {
        return this.deleteById(post.getId());
    }

    public Boardpost getMatchingPost(int id, String password) {
        Boardpost post = this.findById(id);
        if (post.getPassword() == null) return post; // 비밀번호가 설정되지 않은 글은 어떤 비밀번호를 넣어도 비밀번호가 일치한다고 간주함
        if (post.getPassword().equals(password)) return post; // 비밀번호가 일치하는 경우
        return null; // 비밀번호가 아닌 경우
    }

    public ModPostInfo editPost(int id, PostForm post) {

        ModPostInfo mpInfo = new ModPostInfo();
        mpInfo.setId(id);
        mpInfo.setModType("edit");

        Boardpost editedPost = this.findById(id);
        boolean edited = false;

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
