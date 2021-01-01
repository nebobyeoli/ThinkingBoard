package dimigo.hee.ThinkingBoard.controller;

import dimigo.hee.ThinkingBoard.domain.Boardpost;
import dimigo.hee.ThinkingBoard.domain.ModPostInfo;
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

    /**
     * GET으로의 "/post/new"는 등록 폼으로 이동
     */
    @GetMapping("/posts/new")
    public String register() {
        return "registerPost"; // 게시글 입력 뷰 파일로 이동
    }

    /**
     * POST로의 "/post/new"는 SUBMIT 저장 진행
     */
    @PostMapping("/posts/new")
    public String registerPost(PostForm post) {
        boardService.register(post);
        return "redirect:/posts"; // 게시글 목록 뷰 파일로 이동
    }

    @GetMapping("/posts")
    public String postList(
            @ModelAttribute("mpInfo") ModPostInfo mpInfo,
            Model model
    ) {

        model.addAttribute("mpInfo", mpInfo);

        ArrayList<Boardpost> posts = boardService.findAll();
        model.addAttribute("postList", posts);

        return "boardPostList";
    }

    @GetMapping("/posts/search")
    public String search(
            @ModelAttribute("mpInfo") ModPostInfo mpInfo,
            Model model
    ) {

        model.addAttribute("mpInfo", mpInfo);
        model.addAttribute("searchType", "title");

        ArrayList<Boardpost> posts = boardService.findAll();
        model.addAttribute("searchList", posts);

        return "searchPost";
    }

    @PostMapping("/posts/search")
    public String searchPost(
            @ModelAttribute("mpInfo") ModPostInfo mpInfo,
            String searchType,
            String searchByCat,
            String searchContent,
            Model model
    ) {

        ArrayList<Boardpost> list = new ArrayList<>();

        switch (searchType) {
            case "id": // 게시글 #번호 로 검색
                list = boardService.findByContainsId(searchContent);
                break;
            case "title": // 제목으로 검색
                list = boardService.findByContainsTitle(searchContent);
                break;
            case "category": // 카테고리로 검색
                list = boardService.findByContainsCategory(searchByCat);
                break;
            case "contents": // 글 내용으로 검색
                list = boardService.findByContainsContents(searchContent);
                break;
            default:
                break;
        }

        model.addAttribute("mpInfo", mpInfo);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchList", list);

        return "searchPost";
    }

    /**
     * <h2>
     * <a href="https://stackoverflow.com/a/17346284">
     *     Pass model attributes between Spring MVC controllers
     * </a>
     * </h2>
     */
    @PostMapping("/posts/editPost")
    public String editOnePost(
            int id,
            String password,
            Model model,
            RedirectAttributes redirAttributes
    ) {

        ModPostInfo mpInfo = new ModPostInfo();
        mpInfo.setId(id);
        mpInfo.setModType("edit");

        boolean hasId = boardService.hasIdPost(id, password);
        String redirect;

        if (!hasId) { // 비밀번호 틀리셨어요~
            mpInfo.setResult("fail");
            redirAttributes.addFlashAttribute("mpInfo", mpInfo);
            redirect = "redirect:/posts"; // 돌아가세요~
        }
        else { // 비밀번호 맞으셨어요~
            mpInfo.setResult("done");
            model.addAttribute("post", boardService.findById(id));
            redirect = "editPost"; // 글 수정 가능합니다~
        }

        return redirect;
    }

    /**
     * submit된 폼 데이터를 받아 query → update 수행
     */
    @PostMapping("/posts/editPost/submit")
    public String editPostSubmit(
            int id,
            PostForm post,
            RedirectAttributes redirAttributes
    ) {

        ModPostInfo mpInfo = boardService.editPost(id, post);
        redirAttributes.addFlashAttribute("mpInfo", mpInfo);

        return "redirect:/posts";
    }

    @PostMapping("/posts/deleteOne")
    public String deleteOnePost(
            int id,
            String password,
            RedirectAttributes redirAttributes
    ) {

        ModPostInfo mpInfo = new ModPostInfo();
        mpInfo.setId(id);
        mpInfo.setModType("delete");

        boolean hasId = boardService.hasIdPost(id, password);

        if (!hasId) {
            mpInfo.setResult("fail");
        }
        else {
            mpInfo.setResult("done");
            boardService.deleteById(id);
        }

        redirAttributes.addFlashAttribute("mpInfo", mpInfo);

        return "redirect:/posts";
    }
}
