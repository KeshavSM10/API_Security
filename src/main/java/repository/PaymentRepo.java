package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import payments.Payment;
import user.User;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
	    List<Payment> findByUser(User user);
}
