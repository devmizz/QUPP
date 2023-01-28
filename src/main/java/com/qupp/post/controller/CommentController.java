package com.qupp.post.controller;

import com.qupp.post.dto.request.RequestRegisterComment;
import com.qupp.post.dto.request.RequestUpdateComment;
import com.qupp.post.dto.response.ResponseAnswer;
import com.qupp.post.dto.response.ResponsePost;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.service.AnswerService;
import com.qupp.post.service.CommentService;
import com.qupp.post.service.QuestionService;
import com.qupp.user.controller.dto.response.ResponseUser;
import com.qupp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "comment", description = "댓글 API")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "질문글에 댓글 등록 , 접근 제한 API", description = "질문글에 댓글의 정보를 입력받아 등록한다.", tags = "comment")
    @PostMapping("/question/{id}/comment")
    public ResponseEntity<ResponsePost> questionCommentRegister(
            @Parameter(name = "id", description = "번호", in = ParameterIn.PATH)
            @PathVariable("id") long id,
            @RequestBody RequestRegisterComment requestRegisterComment
    ) {
        String nickname = requestRegisterComment.getAuthor();
        ResponseUser responseUser = userService.postsRegisterUser(nickname);
        requestRegisterComment.setUser(responseUser.toEntity());

        ResponseQuestion responseQuestion = questionService.findOne(id);
        requestRegisterComment.setQuestion(responseQuestion.toEntity());

        long questionId = commentService.postRegister(requestRegisterComment, responseQuestion, null);
        ResponsePost responsePost = questionService.getResponsePost(questionId);

        return ResponseEntity.ok(responsePost);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "답변글에 댓글 등록 , 접근 제한 API", description = "답변글에 댓글의 정보를 입력받아 등록한다.", tags = "comment")
    @PostMapping("/question/{questionId}/answer/{answerId}/comment")
    public ResponseEntity<ResponsePost> answerCommentRegister(
            @PathVariable("questionId") long questionId,
            @PathVariable("answerId") long answerId,
            @RequestBody RequestRegisterComment requestRegisterComment
    ) {
        String nickname = requestRegisterComment.getAuthor();
        ResponseUser responseUser = userService.postsRegisterUser(nickname);
        requestRegisterComment.setUser(responseUser.toEntity());

        ResponseAnswer responseAnswer = answerService.findOne(answerId);

        commentService.postRegister(requestRegisterComment, null, responseAnswer);
        ResponsePost responsePost = questionService.getResponsePost(questionId);

        return ResponseEntity.ok(responsePost);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 수정 , 접근 제한 API", description = "댓글의 정보를 수정한다.", tags = "comment")
    @PutMapping("/comment/{id}")
    public ResponseEntity<ResponsePost> updateComment(
            @PathVariable("id") long id,
            @RequestBody RequestUpdateComment requestUpdateComment
    ) {
        long questionId = commentService.update(id, requestUpdateComment);
        ResponsePost responsePost = questionService.getResponsePost(questionId);

        return ResponseEntity.ok(responsePost);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 삭제 , 접근 제한 API", description = "댓글의 정보를 삭제한다.", tags = "comment")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ResponsePost> deleteComment(
            @PathVariable("id") long id
    ) {
        commentService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
