package com.example.demo.modules.stories;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "success_stories", indexes = {
        @Index(name = "idx_story_city", columnList = "city"),
        @Index(name = "idx_story_featured", columnList = "featured")
})
public class SuccessStoryEntity extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String coupleName;
    @Column(nullable = false, length = 120)
    private String slug;
    @Column(nullable = false, length = 80)
    private String city;
    @Column(nullable = false, length = 1000)
    private String summary;
    @Column(nullable = false, length = 1000)
    private String story;
    @Column(nullable = false)
    private Boolean featured;
}
