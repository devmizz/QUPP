package com.qupp.post.controller;

import com.qupp.post.dto.request.RequestRegisterAnswer;
import com.qupp.post.dto.request.RequestUpdateAnswer;
import com.qupp.post.dto.response.ResponsePost;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.service.AnswerService;
import com.qupp.post.service.QuestionService;
import com.qupp.user.controller.dto.response.ResponseUser;
import com.qupp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "answer", description = "답변글 API")
@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "답변글 등록 , 접근 제한 API", description = "답변글의 정보를 입력받아 등록한다.", tags = "answer")
    @PostMapping("/question/{id}/answer")
    public ResponseEntity<ResponsePost> register(
            @PathVariable("id") long id,
            @RequestBody RequestRegisterAnswer requestRegisterAnswer
    ) {
        String nickname = requestRegisterAnswer.getAuthor();
        ResponseUser responseUser = userService.postsRegisterUser(nickname);
        requestRegisterAnswer.setUser(responseUser.toEntity());

        ResponseQuestion responseQuestion = questionService.findOne(id);
        requestRegisterAnswer.setQuestion(responseQuestion.toEntity());

        long questionId = answerService.register(requestRegisterAnswer, responseQuestion);
        ResponsePost responsePost = questionService.getResponsePost(questionId);

        return ResponseEntity.ok(responsePost);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "답변글 수정 , 접근 제한 API", description = "딥변글 정보를 수정, 현재는 이미지 수정은 불가", tags = "answer")
    @PutMapping("/answer/{id}")
    public ResponseEntity<ResponsePost> updateAnswer(
            @PathVariable("id") long id,
            @RequestBody RequestUpdateAnswer requestUpdateAnswer
    ) {
        long questionId = answerService.update(id, requestUpdateAnswer);
        ResponsePost responsePost = questionService.getResponsePost(questionId);

        return ResponseEntity.ok(responsePost);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "답변글 삭제 , 접근 제한 API", description = "답변글의 정보를 삭제한다.", tags = "answer")
    @DeleteMapping("/answer/{id}")
    public ResponseEntity<ResponsePost> deleteAnswer(
            @PathVariable("id") long id
    ) {
        answerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
