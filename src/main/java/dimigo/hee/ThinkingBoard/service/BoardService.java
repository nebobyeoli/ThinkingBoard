package dimigo.hee.ThinkingBoard.service;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
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

    public int register(Boardpost boardPost) {
        boardRepository.save(boardPost);
        return boardPost.getId();
    }

    public Boardpost findById(int id) {
        return boardRepository.findById(id);
    }

    public Boardpost matchPassword(int id, String password) {
        Boardpost post = boardRepository.findById(id);
        if (post.getPassword() == null) return post; // 비밀번호가 설정되지 않은 글은 어떤 비밀번호를 넣어도 비밀번호가 일치한다고 간주함
        if (post.getPassword().equals(password)) return post; // 비밀번호가 일치하는 경우
        return null; // 비밀번호가 아닌 경우
    }

    public Boardpost findByTitle(String title) {
        return boardRepository.findByTitle(title);
    }

    public ArrayList<Boardpost> findAllPosts() {
        return boardRepository.findAll();
    }

    public Boardpost deleteById(int id) {
        return boardRepository.deleteById(id);
    }
}
