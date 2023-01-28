package com.qupp.post.service;

import com.qupp.post.dto.request.RequestRegisterComment;
import com.qupp.post.dto.request.RequestUpdateComment;
import com.qupp.post.dto.response.ResponseAnswer;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.repository.Comment;
import com.qupp.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public long postRegister(RequestRegisterComment requestRegisterComment, ResponseQuestion responseQuestion, ResponseAnswer responseAnswer) {
        LocalDateTime now = LocalDateTime.now();
        Comment comment = requestRegisterComment.toEntity(now, now);
        if (responseQuestion != null) {
            comment.addQuestion(responseQuestion);
        }

        if (responseAnswer != null) {
            comment.addAnswer(responseAnswer);
        }

        comment = commentRepository.save(comment);

        if (responseQuestion != null) {
            return comment.getQuestion().getId();
        }

        return 0;
    }

    @Transactional
    public long update(long id, RequestUpdateComment requestUpdateComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));

        comment.setComment(requestUpdateComment.getComment());
        comment.setUpdateTime(LocalDateTime.now());

        if (comment.getQuestion() != null) {
            return comment.getQuestion().getId();
        }

        return comment.getAnswer().getId();
    }

    @Transactional
    public long delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));

        comment.delete();

        if (comment.getQuestion() != null) {
            return comment.getQuestion().getId();
        }

        return comment.getAnswer().getId();
    }

}
