package com.example.demo.modules.members;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.exception.NotFoundException;
import com.example.demo.common.persistence.SpecificationUtils;
import com.example.demo.modules.audit.AuditActor;
import com.example.demo.modules.audit.AuditLogService;
import com.example.demo.modules.members.dto.MemberProfileRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final AuditLogService auditLogService;

    public MemberProfileService(MemberProfileRepository memberProfileRepository, AuditLogService auditLogService) {
        this.memberProfileRepository = memberProfileRepository;
        this.auditLogService = auditLogService;
    }

    public Page<MemberProfile> search(String q, String city, String religion, Boolean verified, Boolean active, PageRequestParams params) {
        Specification<MemberProfile> specification = Specification
                .where(SpecificationUtils.<MemberProfile>multiFieldContains(q, "fullName", "profession", "education", "city", "community"))
                .and(SpecificationUtils.<MemberProfile>equalsIgnoreCase("city", city))
                .and(SpecificationUtils.<MemberProfile>equalsIgnoreCase("religion", religion))
                .and((root, query, builder) -> verified == null ? builder.conjunction() : builder.equal(root.get("verified"), verified))
                .and((root, query, builder) -> active == null ? builder.conjunction() : builder.equal(root.get("active"), active));

        Pageable pageable = PageRequest.of(params.safePage(), params.safeSize(),
                Sort.by("asc".equalsIgnoreCase(params.safeSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        params.sortBy() == null || params.sortBy().isBlank() ? "createdAt" : params.sortBy()));

        return memberProfileRepository.findAll(specification, pageable);
    }

    public MemberProfile getById(Long id) {
        return memberProfileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member profile not found"));
    }

    public MemberProfile create(MemberProfileRequest request) {
        MemberProfile entity = new MemberProfile();
        MemberProfileMapper.updateEntity(entity, request);
        return memberProfileRepository.save(entity);
    }

    public MemberProfile update(Long id, MemberProfileRequest request) {
        MemberProfile entity = getById(id);
        MemberProfileMapper.updateEntity(entity, request);
        return memberProfileRepository.save(entity);
    }

    public MemberProfile toggleActive(Long id, boolean active, AuditActor actor) {
        MemberProfile entity = getById(id);
        Boolean oldValue = entity.getActive();
        entity.setActive(active);
        MemberProfile saved = memberProfileRepository.save(entity);
        auditLogService.log(actor, active ? "USER_ACTIVATE" : "USER_SUSPEND", "MemberProfile", saved.getMemberCode(), String.valueOf(oldValue), String.valueOf(active), "Updated member active status");
        return saved;
    }

    public MemberProfile toggleVerified(Long id, boolean verified, AuditActor actor) {
        MemberProfile entity = getById(id);
        Boolean oldValue = entity.getVerified();
        entity.setVerified(verified);
        MemberProfile saved = memberProfileRepository.save(entity);
        auditLogService.log(actor, verified ? "KYC_APPROVE" : "KYC_REJECT", "MemberProfile", saved.getMemberCode(), String.valueOf(oldValue), String.valueOf(verified), "Updated member verification status");
        return saved;
    }
}
