package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class JPABoardRepository implements BoardRepository
{
    private EntityManager em;

    public JPABoardRepository(EntityManager em) {
        this.em = em;
    }

    /*** SAVE ***/

    /**
     * JPA 기본 (쿼리날림) 함수 사용 <br>
     * save, findById = DB 기본제공함수 <br>
     * persist = '영속' -- 저장
     */
    @Override
    public void save(Boardpost bp) {
        em.persist(bp);
    }

    /*** FIND ***/

    @Override
    public Boardpost findById(int id) {
        return em.find(Boardpost.class, id);
    }

    @Override
    public ArrayList<Boardpost> findAll() {
        String ql = "SELECT bp FROM Boardpost bp";
        return (ArrayList<Boardpost>) em.createQuery(ql, Boardpost.class).getResultList();
    }

    /*** DELETE ***/

    @Override
    public void deleteById(int id) {
        em.remove(this.findById(id));
    }
}
