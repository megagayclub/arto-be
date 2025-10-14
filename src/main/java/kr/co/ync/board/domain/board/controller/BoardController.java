package kr.co.ync.board.domain.board.controller;

import jakarta.validation.Valid;
import kr.co.ync.board.domain.board.dto.BoardModRequest;
import kr.co.ync.board.domain.board.dto.BoardRegisterRequest;
import kr.co.ync.board.domain.board.service.BoardService;
import kr.co.ync.board.domain.board.service.query.BoardQueryService;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



// C : POST /board
// R : GET /board/list
// R : GET /board/{id}
// U : PUT /board
// D : DELETE /board/{id}
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardQueryService boardQueryService;

    @PostMapping
    public void register(
            @Valid @RequestBody BoardRegisterRequest request
    ) {
        boardService.register(request);
    }

    @GetMapping("/list")
    public ResponseEntity find(PageRequest request) {
        return ResponseEntity.ok(
         boardQueryService.findBoardWithReplyCount(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(
          boardQueryService.findById(id)
        );
    }

    @PutMapping
    public void modify(
            @Valid @RequestBody BoardModRequest request
    ) {
        boardService.modify(request);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id) {
        boardService.remove(id);
    }

}
