package com.example.demo.modules.members;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.exception.NotFoundException;
import com.example.demo.common.persistence.SpecificationUtils;
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

    public MemberProfileService(MemberProfileRepository memberProfileRepository) {
        this.memberProfileRepository = memberProfileRepository;
    }

    public Page<MemberProfile> search(String q, String city, String religion, Boolean verified, PageRequestParams params) {
        Specification<MemberProfile> specification = Specification
                .where(SpecificationUtils.<MemberProfile>multiFieldContains(q, "fullName", "profession", "education", "city", "community"))
                .and(SpecificationUtils.<MemberProfile>equalsIgnoreCase("city", city))
                .and(SpecificationUtils.<MemberProfile>equalsIgnoreCase("religion", religion))
                .and((root, query, builder) -> verified == null ? builder.conjunction() : builder.equal(root.get("verified"), verified));

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
}
