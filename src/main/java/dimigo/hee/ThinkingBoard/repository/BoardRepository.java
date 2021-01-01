package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import java.util.ArrayList;

public interface BoardRepository
{
    /*** SAVE ***/

    void save(Boardpost bp);

    /*** CONFIRM ***/

    /*** FIND ***/

    Boardpost findById(int id);

    ArrayList<Boardpost> findAll();

    boolean hasPostofId(int id, String password);

    ArrayList<Boardpost> findAllHasIds(String[] idList); // "poetic license"; uniform `has`
    ArrayList<Boardpost> findAllHasTitles(String[] titleList, String mode);
    ArrayList<Boardpost> findAllHasCategories(String[] catList, String mode);
    ArrayList<Boardpost> findAllHasContents(String[] contList, String mode);

    /*** EDIT ***/

    /*** DELETE ***/

    void deleteById(int id);
}
