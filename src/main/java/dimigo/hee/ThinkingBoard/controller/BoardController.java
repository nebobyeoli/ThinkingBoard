package dimigo.hee.ThinkingBoard.controller;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register() {
        return "registerPost"; // 게시글 입력 뷰 파일로 이동
    }

    @PostMapping("/posts/new")
    public String registerPost(PostForm post) {
        Boardpost bp = new Boardpost(); // 새 게시글

        bp.setTitle(post.getTitle());
        bp.setCategory(post.getCategory());
        bp.setContents(post.getContents());
        bp.setPassword(post.getPassword());

        boardService.register(bp);
        return "redirect:/posts"; // 게시글 목록 뷰 파일로 이동
    }

    @GetMapping("/posts")
    public String postList(
            @ModelAttribute("modOnePostResult") ModOnePostResult modOnePostResult,
            Model model
    ) {

        model.addAttribute("mod1pResult", modOnePostResult);

        ArrayList<Boardpost> posts = boardService.findAllPosts();
        model.addAttribute("postList", posts);

        return "boardPostList";
    }

    // Pass model attributes between Spring MVC controllers https://stackoverflow.com/a/17346284
    @PostMapping("/posts/deleteOne")
    public String deleteOnePost(
            OnePost post,
            RedirectAttributes redirAttributes
    ) {

        ModOnePostResult modOnePostResult = new ModOnePostResult();
        modOnePostResult.setId(post.getId());
        modOnePostResult.setModType("delete");

        Boardpost match = boardService.matchPassword(post.getId(), post.getPassword());
        if (match == null) modOnePostResult.setResult(false);
        else modOnePostResult.setResult(true);

        redirAttributes.addFlashAttribute("modOnePostResult", modOnePostResult);

        return "redirect:/posts";
    }
}

class OnePost {
    private int id;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class ModOnePostResult {
    private int id;
    private String modType;
    private boolean result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModType() {
        return modType;
    }

    public void setModType(String modType) {
        this.modType = modType;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
