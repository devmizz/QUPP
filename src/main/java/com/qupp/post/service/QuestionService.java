package com.qupp.post.service;

import com.qupp.post.dto.inner.ResponseCategory;
import com.qupp.post.dto.request.RequestRegisterQuestion;
import com.qupp.post.dto.request.RequestUpdateQuestion;
import com.qupp.post.dto.response.ResponsePost;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.repository.Question;
import com.qupp.post.repository.QuestionRepository;
import com.qupp.user.controller.dto.response.ResponseUser;
import com.qupp.user.repository.User;
import com.qupp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final PostComponent postComponent;

    @Transactional
    public ResponsePost register(
            RequestRegisterQuestion registerQuestion
            , ResponseCategory responseCategory
    ) {
        LocalDateTime now = LocalDateTime.now();
        Question question = registerQuestion.toEntity(now, now);

        question.addCategory(responseCategory.getCategory());

        question = questionRepository.save(question);

        return postComponent.getResponsePost(question);
    }

    @Transactional
    public ResponseQuestion findOne(long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));

        return getResponseQuestion(question);
    }

    @Transactional
    public Page<ResponsePost> findAll(Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findAll(pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingByCategory(Long categoryId, Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findByCategoryId(categoryId, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingBySearchTitle(String keyword, Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findByTitleContaining(keyword, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingByCategoryAndSearchTitle(Long categoryId, String keyword, Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findByCategoryIdAndTitleContaining(categoryId, keyword, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingBySearchTitleOrContent(String keyword, Pageable pageable) {
        String title = keyword;
        String content = keyword;

        Page<ResponsePost> questions = questionRepository.findByTitleContainingOrContentContaining(title, content, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingByCategoryAndSearchTitleOrContent(Long categoryId, String keyword, Pageable pageable) {
        String title = keyword;
        String content = keyword;

        Page<ResponsePost> questions = questionRepository.findByCategoryIdAndTitleContainingOrCategoryIdAndContentContaining(categoryId, title, categoryId, content, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingBySearchWriter(String keyword, Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findByUserNicknameContaining(keyword, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    @Transactional
    public Page<ResponsePost> pagingByCategoryAndSearchWriter(Long categoryId, String keyword, Pageable pageable) {
        Page<ResponsePost> questions = questionRepository.findByCategoryIdAndUserNicknameContaining(categoryId, keyword, pageable)
                .map(q -> getResponseQuestionWithPost(q));

        return questions;
    }

    private ResponsePost getResponseQuestionWithPost(Question question) {
        return postComponent.getResponsePost(question);
    }

    @Transactional
    public ResponsePost update(long id, RequestUpdateQuestion updateQuestion) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));

        question.setTitle(updateQuestion.getTitle());
        question.setContent(updateQuestion.getContent());
        question.setUpdateTime(LocalDateTime.now());

        return postComponent.getResponsePost(question);
    }

    @Transactional
    public ResponseQuestion delete(long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));

        question.delete();

        return null;
    }

    //    글 목록에서 필요한 정보를 담기 위한 메서드. 질문글 정보 + 유저 정보
    @Transactional
    public ResponseQuestion getResponseQuestion(Question q) {
        ResponseQuestion responseQuestion = new ResponseQuestion().fromEntity(q);

        ResponseUser responseUser = new ResponseUser().fromEntity(q.getUser());
        responseQuestion.setUser(responseUser);

        return responseQuestion;
    }

    //    상세글 조회 시에 필요한 정보를 담기 위한 메서드. 질문글 + 답변글 + 유저 정보
    @Transactional
    public ResponsePost getResponsePost(long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 질문입니다."));

        return postComponent.getResponsePost(question);
    }

}
