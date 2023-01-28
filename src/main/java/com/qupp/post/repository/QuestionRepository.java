package com.qupp.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByCategoryId(@Param("category_id") Long categoryId, Pageable pageable);

    Page<Question> findByTitleContaining(@Param("title") String keyword, Pageable pageable);

    Page<Question> findByCategoryIdAndTitleContaining(@Param("category_id") Long categoryId, @Param("title") String keyword, Pageable pageable);

    Page<Question> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Question> findByCategoryIdAndTitleContainingOrCategoryIdAndContentContaining(@Param("category_id") Long categoryId1, String title, @Param("category_id") Long categoryId2, String content, Pageable pageable);

    Page<Question> findByUserNicknameContaining(@Param("nickname") String keyword, Pageable pageable);

    Page<Question> findByCategoryIdAndUserNicknameContaining(@Param("category_id") Long categoryId, @Param("nickname") String keyword, Pageable pageable);
}
