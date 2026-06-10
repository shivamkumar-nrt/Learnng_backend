package com.example.demo.modules.members;

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
@Table(name = "member_profiles", indexes = {
        @Index(name = "idx_member_code", columnList = "memberCode", unique = true),
        @Index(name = "idx_member_city", columnList = "city"),
        @Index(name = "idx_member_religion", columnList = "religion"),
        @Index(name = "idx_member_active_verified", columnList = "active,verified")
})
public class MemberProfile extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String memberCode;
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, unique = true, length = 120)
    private String email;
    @Column(nullable = false, unique = true, length = 20)
    private String phone;
    private Integer age;
    @Column(length = 80)
    private String city;
    @Column(length = 80)
    private String state;
    @Column(length = 80)
    private String religion;
    @Column(length = 80)
    private String community;
    @Column(length = 120)
    private String profession;
    @Column(length = 120)
    private String education;
    @Column(length = 60)
    private String annualIncome;
    @Column(length = 40)
    private String intent;
    @Column(length = 1000)
    private String bio;
    private Boolean verified;
    private Integer profileCompletion;
    private Boolean active;
}
