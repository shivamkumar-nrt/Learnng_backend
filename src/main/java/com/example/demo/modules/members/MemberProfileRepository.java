package com.example.demo.modules.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long>, JpaSpecificationExecutor<MemberProfile> {
}
