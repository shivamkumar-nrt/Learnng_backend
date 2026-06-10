package com.example.demo.modules.cms;

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
@Table(name = "cms_pages", indexes = {
        @Index(name = "idx_cms_slug", columnList = "slug", unique = true),
        @Index(name = "idx_cms_status", columnList = "status")
})
public class CmsPage extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String title;
    @Column(nullable = false, length = 120, unique = true)
    private String slug;
    @Column(nullable = false, length = 40)
    private String status;
    @Column(nullable = false, length = 1000)
    private String excerpt;
    @Column(nullable = false, length = 4000)
    private String body;
}
