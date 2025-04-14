package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monetization.APIUsage;
import user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface APIUsageRepo extends JpaRepository<APIUsage, Long> {

    APIUsage findByUserIdAndEndpointAndDate(Long userId, String endpoint, LocalDate date);
    Optional<APIUsage> findByUserAndEndpointAndDate(User user, String endpoint, LocalDate date);
    @Query("SELECT SUM(a.callCount) FROM APIUsage a WHERE a.user.id = :userId AND a.date = :date")
    int countCallsByUserAndDate(Long userId, LocalDate date);
    List<APIUsage> findByUserOrderByDateDesc(User user);
}

