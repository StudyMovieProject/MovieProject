package project.movie.viewPage;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class BoardPageController {
    private final BoardService boardService;
    @GetMapping("list")
    public String getList(@AuthenticationPrincipal UserDetails userDetails, Model model){
        if(userDetails==null){
            model.addAttribute("msg","로그인 후 이용해주세요.");
        }else{
            List<BoardRespDto> posts = boardService.getMyList(userDetails.getUsername());
            model.addAttribute("posts",posts);
        }
        return "board/list";
    }

    @GetMapping("detail/{id}")
    public String getDetail(@PathVariable int id, Model model) {
        BoardRespDto post = boardService.getList(id);
        model.addAttribute("post", post);
        return "board/detail";
    }

    @GetMapping("write")
    public String getWrite() {

        return "board/write";
    }
}
