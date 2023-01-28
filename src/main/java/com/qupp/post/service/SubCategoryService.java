package com.qupp.post.service;

import com.qupp.post.dto.inner.ResponseCategory;
import com.qupp.post.repository.SubCategory;
import com.qupp.post.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public ResponseCategory getCategory(String dept) {
        ResponseCategory responseCategory = new ResponseCategory();
        SubCategory subCategory = subCategoryRepository.findByDept(dept).orElseThrow(() -> new NoSuchElementException("대분류가 존재하지 않습니다."));
        responseCategory.addCategory(subCategory.getCategory());
        return responseCategory;
    }
}
