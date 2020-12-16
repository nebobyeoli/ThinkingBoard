package dimigo.hee.ThinkingBoard;

import dimigo.hee.ThinkingBoard.repository.BoardRepository;
import dimigo.hee.ThinkingBoard.service.BoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigure
{
    private BoardRepository boardRepository;

    public SpringConfigure(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository);
    }
}
