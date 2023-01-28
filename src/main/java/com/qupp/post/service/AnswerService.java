package com.qupp.post.service;

import com.qupp.post.dto.request.RequestRegisterAnswer;
import com.qupp.post.dto.request.RequestUpdateAnswer;
import com.qupp.post.dto.response.ResponseAnswer;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.repository.Answer;
import com.qupp.post.repository.AnswerRepository;
import com.qupp.user.controller.dto.response.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public long register(RequestRegisterAnswer requestRegisterAnswer, ResponseQuestion responseQuestion) {
        LocalDateTime now = LocalDateTime.now();
        Answer answer = requestRegisterAnswer.toEntity(now, now);

        answer.addQuestion(responseQuestion);

        answer = answerRepository.save(answer);

        return answer.getQuestion().getId();

    }

    @Transactional
    public ResponseAnswer findOne(long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 답글입니다."));

        return getResponseAnswer(answer);
    }

    @Transactional
    public long update(long id, RequestUpdateAnswer requestUpdateAnswer) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 답변입니다."));

        answer.setContent(requestUpdateAnswer.getContent());
        answer.setUpdateTime(LocalDateTime.now());

        return answer.getQuestion().getId();
    }

    @Transactional
    public long delete(long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 답변입니다."));
        answer.delete();

        return answer.getQuestion().getId();
    }

    @Transactional
    public ResponseAnswer getResponseAnswer(Answer a) {
        ResponseAnswer responseAnswer = new ResponseAnswer().fromEntity(a);

        ResponseUser responseUser = new ResponseUser().fromEntity(a.getUser());
        responseAnswer.setUser(responseUser);

        return responseAnswer;
    }
}
