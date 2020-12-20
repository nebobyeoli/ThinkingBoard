package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BoardRepository
{
    Boardpost save(Boardpost boardPost);

    Boardpost findById(int id);

//    Boardpost findByTitle(@Param("title") String title);
//
//    Boardpost findByCategory(@Param("category") String category);
//
//    Boardpost findByContents(@Param("contents") String contents);

//    @Modifying
//    @Query(value = "SELECT * FROM Boardpost WHERE post.title LIKE LOWER(CONCAT('%',:title,'%'))", nativeQuery = true)
//    Boardpost findByTitle(@Param("title") String title);
//
//    @Modifying
//    @Query(value = "SELECT * FROM Boardpost WHERE post.category LIKE LOWER(CONCAT('%',:category,'%'))", nativeQuery = true)
//    Boardpost findByCategory(@Param("category") String category);
//
//    @Modifying
//    @Query(value = "SELECT * FROM Boardpost WHERE post.contents LIKE LOWER(CONCAT('%',:contents,'%'))", nativeQuery = true)
//    Boardpost findByContents(@Param("contents") String contents);

    ArrayList<Boardpost> findAll(); // 모든 Boardpost 반환
    Boardpost deleteById(int id);
}
