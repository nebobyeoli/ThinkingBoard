package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import java.util.ArrayList;

public interface BoardRepository
{
    /*** SAVE ***/

    void save(Boardpost bp);

    /*** FIND ***/

    Boardpost findById(int id);

    ArrayList<Boardpost> findAll();

    /*** DELETE ***/

    void deleteById(int id);
}
