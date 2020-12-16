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

    public ArrayList<Boardpost> findAllPosts() {
        return boardRepository.findAll();
    }

    public Boardpost findSinglePost(int id) {
        return boardRepository.findById(id);
    }
}
