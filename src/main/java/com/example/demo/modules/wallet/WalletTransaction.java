package com.example.demo.modules.wallet;

import com.example.demo.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wallet_transactions", indexes = {
        @Index(name = "idx_wallet_user_email", columnList = "userEmail"),
        @Index(name = "idx_wallet_type", columnList = "walletType")
})
public class WalletTransaction extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String userEmail;
    @Column(nullable = false, length = 30)
    private String walletType;
    @Column(nullable = false, length = 30)
    private String transactionType;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, length = 80)
    private String source;
    @Column(nullable = false, length = 80)
    private String referenceId;
    private Instant expiresAt;
}
