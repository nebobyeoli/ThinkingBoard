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

    /*** CONFIRM ***/

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

    @Override
    public boolean hasPostofId(int id, String password) {
        Boardpost bp = this.findById(id);
        return (bp.getPassword() != null) || bp.getPassword().equals(password);
    }

    @Override
    public ArrayList<Boardpost> findAllHasIds(String[] idList) {
        String ql = "SELECT bp FROM Boardpost bp WHERE bp.id = :id";
        return (ArrayList<Boardpost>) em.createQuery(ql, Boardpost.class).setParameter("id", idList[0]).getResultList();
    }

    @Override
    public ArrayList<Boardpost> findAllHasTitles(String[] titleList, String mode) {
        StringBuffer ql = new StringBuffer("SELECT bp FROM Boardpost bp");
        for (String title : titleList) ql.append(String.format(" %s bp.title LIKE '%%%s%%'", title.equals(titleList[0]) ? "WHERE" : mode, title));
        return (ArrayList<Boardpost>) em.createQuery(ql.toString(), Boardpost.class).getResultList();
    }

    @Override
    public ArrayList<Boardpost> findAllHasCategories(String[] catList, String mode) {
        StringBuffer ql = new StringBuffer("SELECT bp FROM Boardpost bp");
        for (String cat : catList) ql.append(String.format(" %s bp.category LIKE '%%%s%%'", cat.equals(catList[0]) ? "WHERE" : mode, cat));
        return (ArrayList<Boardpost>) em.createQuery(ql.toString(), Boardpost.class).getResultList();
    }

    @Override
    public ArrayList<Boardpost> findAllHasContents(String[] contList, String mode) {
        StringBuffer ql = new StringBuffer("SELECT bp FROM Boardpost bp");
        for (String cont : contList) ql.append(String.format(" %s bp.content LIKE '%%%s%%'", cont.equals(contList[0]) ? "WHERE" : mode, cont));
        return (ArrayList<Boardpost>) em.createQuery(ql.toString(), Boardpost.class).getResultList();
    }

    /*** EDIT ***/

    /*** DELETE ***/

    @Override
    public void deleteById(int id) {
        em.remove(this.findById(id));
    }
}
