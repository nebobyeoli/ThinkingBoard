package dimigo.hee.ThinkingBoard.controller;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class BoardController
{
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/posts/new")
    public String registerBoard() {
        return "registerPost"; // 게시글 입력 뷰 파일로 이동
    }

    @PostMapping("/posts/new")
    public String register(PostForm postForm) {
        Boardpost bp = new Boardpost(); // 새 게시글

        bp.setTitle(postForm.getTitle());
        bp.setContents(postForm.getContents());

        boardService.register(bp);
        return "redirect:/posts"; // 게시글 목록 뷰 파일로 이동
    }

    @GetMapping("/posts")
    public String postList(Model model) {
        ArrayList<Boardpost> posts = boardService.findAllPosts();
        model.addAttribute("postList", posts);
        return "boardPostList";
    }
}
