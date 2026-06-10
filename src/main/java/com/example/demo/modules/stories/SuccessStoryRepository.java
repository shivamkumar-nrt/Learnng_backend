package com.example.demo.modules.stories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SuccessStoryRepository extends JpaRepository<SuccessStoryEntity, Long>, JpaSpecificationExecutor<SuccessStoryEntity> {
}
