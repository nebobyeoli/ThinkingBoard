package dimigo.hee.ThinkingBoard;

import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import dimigo.hee.ThinkingBoard.repository.JPABoardRepository;
import dimigo.hee.ThinkingBoard.service.BoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfigure
{
    // connection pool: connection 객체로 DB 연결해 줌
    private DataSource dataSource;

    // JPA 실행 매니저
    private EntityManager em;

    public SpringConfigure(DataSource dataSource, EntityManager em) { // 주입
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public BoardRepository boardRepository() {
        return new JPABoardRepository(em);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(this.boardRepository());
    }
}
