package com.qupp.post.service;

import com.qupp.post.dto.response.ResponseAnswer;
import com.qupp.post.dto.response.ResponseComment;
import com.qupp.post.dto.response.ResponsePost;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.repository.Answer;
import com.qupp.post.repository.Comment;
import com.qupp.post.repository.Category;
import com.qupp.post.repository.Question;
import com.qupp.user.controller.dto.response.ResponseUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostComponent {

    public ResponsePost getResponsePost(Question question) {
        ResponseQuestion responseQuestion = getResponseQuestion(question);

        List<ResponseAnswer> responseAnswers = new ArrayList<>();

        if(question.getAnswers() != null) {
            responseAnswers = getResponseAnswers(question.getAnswers());
        }

        return new ResponsePost(responseQuestion, responseAnswers);
    }

    public ResponseQuestion getResponseQuestion(Question question) {
        ResponseQuestion responseQuestion = new ResponseQuestion().fromEntity(question);

        if(question.getUser() != null) {
            responseQuestion.setUser(
                    new ResponseUser().fromEntity(question.getUser())
            );
        }

        List<ResponseComment> responseComments;

        if(question.getComments() != null) {
            responseComments = getResponseComments(question.getComments());
            responseQuestion.setComments(responseComments);
        }

        if(question.getCategory() != null) {
            Category category = question.getCategory();
            responseQuestion.setCategory(category.getCollege());
            responseQuestion.setSubCategory(category.getSubCategory().getDept());
        }

        return responseQuestion;
    }

    private List<ResponseAnswer> getResponseAnswers(List<Answer> answers) {
        return answers.stream().map(a -> getResponseAnswer(a)).collect(Collectors.toList());
    }

    private ResponseAnswer getResponseAnswer(Answer answer) {
        ResponseUser responseUser = new ResponseUser().fromEntity(answer.getUser());

        ResponseAnswer responseAnswer = new ResponseAnswer().fromEntity(answer);
        responseAnswer.setUser(responseUser);

        List<ResponseComment> responseComments;

        if(answer.getComments() != null) {
            responseComments = getResponseComments(answer.getComments());
            responseAnswer.setComments(responseComments);
        }

        return responseAnswer;
    }

    private List<ResponseComment> getResponseComments(List<Comment> comments) {
        return comments.stream().map(c -> getResponseComment(c)).collect(Collectors.toList());
    }

    private ResponseComment getResponseComment(Comment comment) {
        ResponseUser responseUser = new ResponseUser().fromEntity(comment.getUser());

        ResponseComment responseComment = new ResponseComment().fromEntity(comment);
        responseComment.setUser(responseUser);

        return responseComment;
    }
}
