package com.app.familhas_website.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.familhas_website.category.dto.CategoryRequest;
import com.app.familhas_website.category.dto.CategoryResponse;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return toResponse(getCategoryOrThrow(id));
    }

    public CategoryResponse create(CategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        apply(category, request);
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        CategoryEntity category = getCategoryOrThrow(id);
        apply(category, request);
        return toResponse(categoryRepository.save(category));
    }

    public void delete(Long id) {
        CategoryEntity category = getCategoryOrThrow(id);
        categoryRepository.delete(category);
    }

    private CategoryEntity getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
    }

    private void apply(CategoryEntity category, CategoryRequest request) {
        category.setTitle(request.title().trim());
        category.setStatus(request.status());
        category.setImageUrl(blankToNull(request.imageUrl()));
        category.setDescription(blankToNull(request.description()));
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }

    private CategoryResponse toResponse(CategoryEntity category) {
        return new CategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getStatus(),
                category.getImageUrl(),
                category.getDescription());
    }
}



