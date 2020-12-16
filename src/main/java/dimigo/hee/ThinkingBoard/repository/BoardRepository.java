package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;

import java.util.ArrayList;

public interface BoardRepository
{
    Boardpost save(Boardpost boardPost);
    Boardpost findById(int id);
    Boardpost findByTitle(String title);
    ArrayList<Boardpost> findAll(); // 모든 Boardpost 반환
}
