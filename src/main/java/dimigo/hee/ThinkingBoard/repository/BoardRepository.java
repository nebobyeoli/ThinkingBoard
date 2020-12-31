package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import java.util.ArrayList;

public interface BoardRepository
{
    void save(Boardpost bp);

    Boardpost findById(int id);

    /**
     * 모든 Boardpost 반환 <br>
     * boardService의 기본 외 함수들은 <br>
     * findAll, findById에서 반환된 ArrayList<Boardpost> 데이터를 이용하여 실행됨
     */
    ArrayList<Boardpost> findAll();

    void deleteById(int id);
}
