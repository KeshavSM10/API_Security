package payments;

import jakarta.persistence.*;
import monetization.APIPlan;
import monetization.APIPlan;

import java.time.LocalDate;

import user.User;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    private double amount;
    private String transactionId;
    
    @Enumerated(EnumType.STRING)
    private APIPlan planPurchased;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentSatus paymentStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public APIPlan getPlanPurchased() { return planPurchased; }
    public void setPlanPurchased(APIPlan planPurchased) { this.planPurchased = planPurchased; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public PaymentSatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentSatus paymentStatus) { this.paymentStatus = paymentStatus; }
}

