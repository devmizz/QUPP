package com.qupp.user.service;


import com.qupp.jwt.JwtProvider;
import com.qupp.mail.EmailService;
import com.qupp.mail.dto.EmailToken;
import com.qupp.mail.dto.ResponseMessage;
import com.qupp.mail.respository.EmailTokenRepository;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.repository.Answer;
import com.qupp.post.repository.Comment;
import com.qupp.post.repository.Question;
import com.qupp.post.service.PostComponent;
import com.qupp.user.controller.dto.request.RequestCreateUser;
import com.qupp.user.controller.dto.request.RequestEmailUpdate;
import com.qupp.user.controller.dto.request.RequestLogin;
import com.qupp.user.controller.dto.request.RequestNicknameUpdate;
import com.qupp.user.controller.dto.request.RequestUpdatePassword;
import com.qupp.user.controller.dto.response.ResponseLogin;
import com.qupp.user.controller.dto.response.ResponseRegister;
import com.qupp.user.controller.dto.response.ResponseUser;
import com.qupp.user.controller.dto.response.ResponseUserUpdate;
import com.qupp.user.repository.User;
import com.qupp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final PostComponent postComponent;
    private final EmailService emailService;
    private int tmpPassword ;
    private final EmailTokenRepository emailTokenRepository;

    @Transactional
    public ResponseRegister userRegister(RequestCreateUser requestCreateUser) throws MessagingException {

        userRepository.findByEmail(requestCreateUser.getEmail())
                .ifPresent(user -> { throw new IllegalArgumentException("???????????? ?????????????????????.");
                });

        userRepository.findByNickname(requestCreateUser.getNickname())
                .ifPresent(user -> {throw new IllegalArgumentException("???????????? ?????????????????????.");
                });

        requestCreateUser.setPassword(passwordEncoder.encode(requestCreateUser.getPassword()));

        User user = requestCreateUser.toEntity();
        userRepository.save(user);

        EmailToken emailToken = EmailToken.generateVerificationToken(user);
        emailTokenRepository.save(emailToken);

        String token = emailToken.getVerificationToken();
        sendRegisterEmail(user, token);

        return ResponseRegister.builder()
                .responseUser(ResponseUser.fromEntity(user))
                .build();
    }

    public void sendRegisterEmail(User user, String emailToken) throws MessagingException {
        String message = "QUPP ??????????????? ?????????????????? ????????? ???????????????.";

        ResponseMessage responseMessage = ResponseMessage.builder()
                .to(user.getEmail())
                .subject("QUPP, ?????? ?????? ??????")
                .message(message + " \nhttp://localhost:8080/accountVerification/%s ".formatted(emailToken))
                .build();

        emailService.sendEmail(responseMessage);
    }

    @Transactional
    public void verifyAccount(User user) {
        user.verifyAccount();
        userRepository.save(user);
    }

    @Transactional
    public void expiredToken(EmailToken token) {
        token.expiredToken();
        emailTokenRepository.save(token);
    }

    @Transactional
    public ResponseMessage sendEmail(String email){
        Random random = new Random();
        tmpPassword = random.nextInt(999999);

        return ResponseMessage.builder()
                .to(email)
                .subject("?????????????????? ?????? ??????")
                .message("?????????????????? : %d".formatted(tmpPassword))
                .build();
    }

    public int getTmpPassword() {
        return tmpPassword;
    }

    @Transactional
    public ResponseUser updatePassword(long id, RequestUpdatePassword requestUpdatePassword) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("????????? ??????????????????.")
        );

        if (passwordEncoder.matches(requestUpdatePassword.getUpdatePassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestUpdatePassword.getUpdatePassword()));
        }

        return ResponseUser.fromEntity(user);
    }

    @Transactional
    public boolean isDuplicateEmail(String email) {

        return userRepository.findByEmail(email)
                .isPresent();
    }

    @Transactional
    public boolean isDuplicateNickname(String nickname) {

        return userRepository.findByNickname(nickname)
                .isPresent();
    }

    @Transactional
    public ResponseLogin login(RequestLogin requestLogin) {
        User user = userRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow(() -> new NoSuchElementException("????????? ??????????????????."));

        if ( user.isVerifiedAccount()== false) {
            throw new BadCredentialsException("????????? ????????? ????????? ???????????????.");
        }

        if (passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            return ResponseLogin.builder()
                    .responseUser(ResponseUser.fromEntity(user))
                    .jwtToken(genAccessToken(user))
                    .build();
        } else {
            throw new BadCredentialsException("????????? ?????????????????????.");
        }
    }

    @Transactional
    public ResponseUser findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("??????????????? ????????????."));

        return ResponseUser.fromEntity(user);
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public ResponseUserUpdate updateNickname(long id, RequestNicknameUpdate requestNicknameUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("????????? ??????????????? ????????????."));
        String nickname = requestNicknameUpdate.getNickname();
        if (!isDuplicateNickname(nickname)) {
            user.setNickname(nickname);
            return ResponseUserUpdate.builder()
                    .email(user.getEmail())
                    .nickname(nickname)
                    .build();
        }
        throw new IllegalArgumentException("??????????????? ????????? ???????????????.");
    }

    @Transactional
    public ResponseUserUpdate updateEmail(long id, RequestEmailUpdate requestEmailUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("????????? ??????????????? ????????????."));
        String email = requestEmailUpdate.getEmail();
        if (!isDuplicateEmail(email)) {
            user.setEmail(email);
            return ResponseUserUpdate.builder()
                    .nickname(user.getNickname())
                    .email(email)
                    .build();
        }
        throw new IllegalArgumentException("??????????????? ????????? ???????????????.");
    }

    public ResponseUser postsRegisterUser(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchElementException("????????? ??????????????????."));

        return ResponseUser.fromEntity(user);
    }

    
    @Transactional
    public Page<ResponseQuestion> findUserQuestions(long id, Pageable pageable){

        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("??????????????? ????????????."));
        List<Question> questions = user.getQuestions();
        List<ResponseQuestion> responseQuestions = new ArrayList<>();

        for (Question question : questions) {
            ResponseQuestion responseQuestion = postComponent.getResponseQuestion(question);
            responseQuestions.add(responseQuestion);
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), responseQuestions.size());
        final Page<ResponseQuestion> page = new PageImpl<>(responseQuestions.subList(start, end), pageable, responseQuestions.size());

        return page;
    }

    @Transactional
    public Page<ResponseQuestion> findUserAnswers(long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("??????????????? ????????????."));
        List<Answer> answers = user.getAnswers();
        List<ResponseQuestion> responseQuestions = new ArrayList<>();

        for (Answer answer : answers) {
            ResponseQuestion responseQuestion = postComponent.getResponseQuestion(answer.getQuestion()); // -> ?????? ????????? ?????? ?????? '?????????' ??? ??????
            responseQuestions.add(responseQuestion);
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), responseQuestions.size());
        final Page<ResponseQuestion> page = new PageImpl<>(responseQuestions.subList(start, end), pageable, responseQuestions.size());

        return page;
    }

    @Transactional
    public Page<ResponseQuestion> findUserComments(long id, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("??????????????? ????????????."));
        List<Comment> comments = user.getComments();
        List<ResponseQuestion> responseQuestions = new ArrayList<>();

        for (Comment comment : comments) {
            Question question = comment.getQuestion();
            ResponseQuestion responseQuestion = postComponent.getResponseQuestion(
                    question != null ? question : comment.getAnswer().getQuestion()); // ????????? ?????? ???????????? ?????? ??????, ????????? ?????? ???????????? ????????? ?????? ?????? ??????
            responseQuestions.add(responseQuestion);
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), responseQuestions.size());
        final Page<ResponseQuestion> page = new PageImpl<>(responseQuestions.subList(start, end), pageable, responseQuestions.size());

        return page;
    }

    @Transactional
    public String genAccessToken(User user) {
        return jwtProvider.generateAccessToken(user.getAccessTokenClaims(), 60 * 60 * 60 * 24);
    }


}
