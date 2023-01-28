package com.qupp.post.controller;

import com.qupp.post.dto.inner.ResponseCategory;
import com.qupp.post.dto.request.RequestRegisterQuestion;
import com.qupp.post.dto.request.RequestUpdateQuestion;
import com.qupp.post.dto.response.ResponsePost;
import com.qupp.post.dto.response.ResponseQuestion;
import com.qupp.post.service.CategoryService;
import com.qupp.post.service.QuestionService;
import com.qupp.post.service.SubCategoryService;
import com.qupp.user.controller.dto.response.ResponseUser;
import com.qupp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "question", description = "질문글 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "질문글 등록 , 접근 제한 API", description = "질문글의 정보를 입력받아 등록한다.", tags = "question")
    @PostMapping("/question")
    public ResponseEntity<ResponsePost> register(
            @Valid @RequestBody RequestRegisterQuestion requestRegisterQuestion
    ) {
        String nickname = requestRegisterQuestion.getAuthor();
        ResponseUser responseUser = userService.postsRegisterUser(nickname);
        requestRegisterQuestion.setUser(responseUser.toEntity());

        ResponseCategory responseCategory = subCategoryService.getCategory(requestRegisterQuestion.getDept());

        return ResponseEntity.ok(questionService.register(requestRegisterQuestion, responseCategory));
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "질문 상세 조회", description = "질문 상세 조회 페이지 - 답변에 대한 정보도 함께", tags = "question")
    @GetMapping("/question/{id}")
    public ResponseEntity<ResponsePost> findOne(
            @Parameter(name = "id", description = "번호", in = ParameterIn.PATH)
            @PathVariable("id") long id
    ) {
        return ResponseEntity.ok(questionService.getResponsePost(id));
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "질문 리스트", description = "전체 질문 조회 - 답변에 대한 정보는 담기지 않음", tags = "question")
    @GetMapping("/questions")
    public ResponseEntity<Page<ResponsePost>> findAll(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.PATH)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(name = "category", description = "카테고리", in = ParameterIn.PATH)
            @RequestParam(value = "category", required = false) String category) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("registerTime").descending());

        Long categoryId = categoryService.findByCollege(category);

        if (categoryId == null) {
            return ResponseEntity.ok(questionService.findAll(pageRequest));
        }

        return ResponseEntity.ok(questionService.pagingByCategory(categoryId, pageRequest));
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "질문 제목 검색 리스트", description = "전체 질문 조회(제목 검색) - 답변에 대한 정보는 담기지 않음", tags = "question")
    @GetMapping("/questions/search/title")
    public ResponseEntity<Page<ResponsePost>> findSearchTitle(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.PATH)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(name = "category", description = "카테고리", in = ParameterIn.PATH)
            @RequestParam(value = "category", required = false) String category,
            @Parameter(name = "keyword", description = "키워드", in = ParameterIn.PATH)
            @RequestParam(value = "keyword") String keyword) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("registerTime").descending());

        Long categoryId = categoryService.findByCollege(category);

        if (categoryId == null) {
            return ResponseEntity.ok(questionService.pagingBySearchTitle(keyword, pageRequest));
        }

        return ResponseEntity.ok(questionService.pagingByCategoryAndSearchTitle(categoryId, keyword, pageRequest));
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "질문 제목+내용 검색 리스트", description = "전체 질문 조회(제목+내용 검색) - 답변에 대한 정보는 담기지 않음", tags = "question")
    @GetMapping("/questions/search/post")
    public ResponseEntity<Page<ResponsePost>> findSearchTitleAndContent(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.PATH)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(name = "category", description = "카테고리", in = ParameterIn.PATH)
            @RequestParam(value = "category", required = false) String category,
            @Parameter(name = "keyword", description = "키워드", in = ParameterIn.PATH)
            @RequestParam(value = "keyword", required = false) String keyword) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("registerTime").descending());

        Long categoryId = categoryService.findByCollege(category);

        if (categoryId == null) {
            return ResponseEntity.ok(questionService.pagingBySearchTitleOrContent(keyword, pageRequest));
        }

        return ResponseEntity.ok(questionService.pagingByCategoryAndSearchTitleOrContent(categoryId, keyword, pageRequest));
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "질문 유저이름 검색 리스트", description = "전체 질문 조회(유저이름 검색) - 답변에 대한 정보는 담기지 않음", tags = "question")
    @GetMapping("/questions/search/writer")
    public ResponseEntity<Page<ResponsePost>> findSearchWriter(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.PATH)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(name = "category", description = "카테고리", in = ParameterIn.PATH)
            @RequestParam(value = "category", required = false) String category,
            @Parameter(name = "keyword", description = "키워드", in = ParameterIn.PATH)
            @RequestParam(value = "keyword", required = false) String keyword) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("registerTime").descending());

        Long categoryId = categoryService.findByCollege(category);

        if (categoryId == null) {
            return ResponseEntity.ok(questionService.pagingBySearchWriter(keyword, pageRequest));
        }

        return ResponseEntity.ok(questionService.pagingByCategoryAndSearchWriter(categoryId, keyword, pageRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "질문 수정 , 접근 제한 API", description = "질문 수정 - 현재는 이미지에 대한 수정 불가", tags = "question")
    @PutMapping("/question/{id}")
    public ResponseEntity<ResponsePost> updateQuestion(
            @Parameter(name = "id", description = "번호", in = ParameterIn.PATH)
            @PathVariable("id") long id,
            @RequestBody RequestUpdateQuestion updateQuestion
    ) {
        return ResponseEntity.ok(questionService.update(id, updateQuestion));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "질문글 삭제 , 접근 제한 API", description = "질문 삭제 - 반환 객체 X", tags = "question")
    @DeleteMapping("/question/{id}")
    public ResponseEntity<ResponseQuestion> deleteQuestion (
            @Parameter(name = "id", description = "번호", in = ParameterIn.PATH)
            @PathVariable("id") long id
    ) {
        questionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}