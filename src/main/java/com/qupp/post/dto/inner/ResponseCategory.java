package com.qupp.post.dto.inner;

import com.qupp.post.repository.Category;
import lombok.Getter;

@Getter
public class ResponseCategory {

    private Category category;

    public void addCategory(Category category) {
        this.category = category;
    }
}
