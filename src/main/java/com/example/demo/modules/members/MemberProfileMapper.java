package com.example.demo.modules.members;

import com.example.demo.modules.members.dto.MemberProfileRequest;
import com.example.demo.modules.members.dto.MemberProfileResponse;

public final class MemberProfileMapper {

    private MemberProfileMapper() {
    }

    public static MemberProfileResponse toResponse(MemberProfile entity) {
        return new MemberProfileResponse(
                entity.getId(),
                entity.getMemberCode(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAge(),
                entity.getCity(),
                entity.getState(),
                entity.getReligion(),
                entity.getCommunity(),
                entity.getProfession(),
                entity.getEducation(),
                entity.getAnnualIncome(),
                entity.getIntent(),
                entity.getBio(),
                entity.getVerified(),
                entity.getProfileCompletion(),
                entity.getActive()
        );
    }

    public static void updateEntity(MemberProfile entity, MemberProfileRequest request) {
        entity.setMemberCode(request.memberCode());
        entity.setFullName(request.fullName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setAge(request.age());
        entity.setCity(request.city());
        entity.setState(request.state());
        entity.setReligion(request.religion());
        entity.setCommunity(request.community());
        entity.setProfession(request.profession());
        entity.setEducation(request.education());
        entity.setAnnualIncome(request.annualIncome());
        entity.setIntent(request.intent());
        entity.setBio(request.bio());
        entity.setVerified(request.verified());
        entity.setProfileCompletion(request.profileCompletion());
        entity.setActive(request.active());
    }
}
