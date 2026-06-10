package com.example.demo.modules.cms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmsPageRepository extends JpaRepository<CmsPage, Long>, JpaSpecificationExecutor<CmsPage> {
}
