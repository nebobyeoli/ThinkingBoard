package dimigo.hee.ThinkingBoard.repository;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BoardRepository
{
    // 기본 SpringDataJPA 제공 함수들

    Boardpost save(Boardpost boardPost);

    Boardpost findById(int id);

    ArrayList<Boardpost> findAll();
    // 모든 Boardpost 반환
    // boardService의 기본 외 함수들은
    // findAll, findById에서 반환된 ArrayList<Boardpost> 데이터를 이용하여 실행됨

    Boardpost deleteById(int id);
}
