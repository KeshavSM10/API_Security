package monetization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import payments.Payment;
import payments.PaymentSatus;
import repository.APIUsageRepo;
import repository.PaymentRepo;
import repository.UserRepo;
import user.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MonetizationService {

    @Autowired
    private UserRepo userrepo;
    
    @Autowired
    private PaymentRepo paymentRepo;
    
    @Autowired
    private APIUsageRepo apiusagerepository;
    
    private static final Map<APIPlan, Integer> PlanDailyLimit = new HashMap<>();
    private static final Map<APIPlan, Double> PlanPrice = new HashMap<>();
    
    static {
        PlanDailyLimit.put(APIPlan.FREE, 10);
        PlanDailyLimit.put(APIPlan.BASIC, 100);
        PlanDailyLimit.put(APIPlan.PREMIUM, 1000);
        
        PlanPrice.put(APIPlan.FREE, 0.0);
        PlanPrice.put(APIPlan.BASIC, 19.99);
        PlanPrice.put(APIPlan.PREMIUM, 49.99);
    }
    
    public boolean canMakeAPICall(String username, String endpoint) {
        Optional<User> userOpt = userrepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        
        User user = userOpt.get();
        APIPlan plan = user.getApiPlan();
        int dailyLimit = PlanDailyLimit.get(plan);
        
        LocalDate today = LocalDate.now();
        int todaysUsage = apiusagerepository.countCallsByUserAndDate(user.getId(), today);
        
        return todaysUsage < dailyLimit;
    }
    
    public void recordAPICall(String username, String endpoint) {
        Optional<User> userOpt = userrepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return;
        }
        
        User user = userOpt.get();
        LocalDate today = LocalDate.now();
        
        Optional<APIUsage> existingUsage = apiusagerepository.findByUserAndEndpointAndDate(
                user, endpoint, today);
        
        if (existingUsage.isPresent()) {
            APIUsage usage = existingUsage.get();
            usage.setCallCount(usage.getCallCount() + 1);
            apiusagerepository.save(usage);
        } 
        
        else {
            APIUsage newUsage = new APIUsage();
            newUsage.setUser(user);
            newUsage.setEndpoint(endpoint);
            newUsage.setCallCount(1);
            newUsage.setDate(today);
            apiusagerepository.save(newUsage);
        }
    }
    

    public User upgradePlan(String username, APIPlan newPlan, String paymentId, double amount, LocalDate lacoaldate, PaymentSatus ps) {
        Optional<User> userOpt = userrepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        
        User user = userOpt.get();
        
        user.setApiPlan(newPlan);
        userrepo.save(user);
        
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(amount);
        payment.setPaymentDate(lacoaldate);
        payment.setPaymentStatus(ps);
        payment.setTransactionId(paymentId);
        payment.setPlanPurchased(newPlan);
        paymentRepo.save(payment);
        
        return user;
    }
    
    public double getPlanPrice(APIPlan plan) {
        return PlanPrice.get(plan);
    }
    
    public int getRemainingAPICalls(String username) {
        Optional<User> userOpt = userrepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return 0;
        }
        
        User user = userOpt.get();
        APIPlan plan = user.getApiPlan();
        int dailyLimit = PlanDailyLimit.get(plan);
        
        LocalDate today = LocalDate.now();
        int todaysUsage = apiusagerepository.countCallsByUserAndDate(user.getId(), today);
        
        return Math.max(0, dailyLimit - todaysUsage);
    }

    public Map<String, Object> getUserUsageStats(String username) {
        Optional<User> userOpt = userrepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        
        User user = userOpt.get();
        APIPlan plan = user.getApiPlan();
        int dailyLimit = PlanDailyLimit.get(plan);
        
        LocalDate today = LocalDate.now();
        int todaysUsage = apiusagerepository.countCallsByUserAndDate(user.getId(), today);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("username", username);
        stats.put("plan", plan.name());
        stats.put("dailyLimit", dailyLimit);
        stats.put("todaysUsage", todaysUsage);
        stats.put("remainingCalls", Math.max(0, dailyLimit - todaysUsage));
        stats.put("percentageUsed", (double) todaysUsage / dailyLimit * 100);
        
        return stats;
    }
}