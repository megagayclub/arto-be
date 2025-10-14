package kr.co.ync.board.domain.reply.controller;

import jakarta.validation.Valid;
import kr.co.ync.board.domain.reply.dto.Reply;
import kr.co.ync.board.domain.reply.dto.ReplyModRequest;
import kr.co.ync.board.domain.reply.dto.ReplyRegisterRequest;
import kr.co.ync.board.domain.reply.service.ReplyService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;
    @PostMapping
    public void register(
           @Valid @RequestBody ReplyRegisterRequest request){
        replyService.register(request);
    }
    @GetMapping("/board/{boardId}")
    public ResponseEntity findByBoardId(
            @PathVariable("boardId") Long boardId
    ){
        return ResponseEntity.ok(
                replyService.findByBoardId(boardId)
        );
    }
    @PutMapping
    public void modify(
           @Valid @RequestBody ReplyModRequest request
    ){
         replyService.modify(request);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id){
        replyService.remove(id);
    }

}
