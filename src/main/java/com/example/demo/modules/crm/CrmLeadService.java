package com.example.demo.modules.crm;

import com.example.demo.common.api.PageRequestParams;
import com.example.demo.common.persistence.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CrmLeadService {

    private final CrmLeadRepository crmLeadRepository;

    public CrmLeadService(CrmLeadRepository crmLeadRepository) {
        this.crmLeadRepository = crmLeadRepository;
    }

    public Page<CrmLead> search(String q, String rm, String status, PageRequestParams params) {
        Specification<CrmLead> specification = Specification
                .where(SpecificationUtils.<CrmLead>multiFieldContains(q, "memberName", "email", "city", "sourceChannel"))
                .and(SpecificationUtils.<CrmLead>equalsIgnoreCase("relationshipManager", rm))
                .and(SpecificationUtils.<CrmLead>equalsIgnoreCase("status", status));

        return crmLeadRepository.findAll(specification,
                PageRequest.of(params.safePage(), params.safeSize(), Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
