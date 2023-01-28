package com.qupp.post.service;

import com.qupp.post.repository.Category;
import com.qupp.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long findByCollege(String college) {
        Category category = categoryRepository.findByCollege(college)
                .orElse(null);

        if (category == null) {
            return null;
        }

        return category.getId();
    }
}
