package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<entity, primary key data type>
// SpringConfigure에서 use
// save, findById, findAll 메소드가 기본으로 이미 구현되어 있음
public interface SpringDataJPABoardRepository extends JpaRepository<Boardpost, Integer>, BoardRepository
{

}
