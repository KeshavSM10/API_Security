package monetization;

import java.time.LocalDate;
import jakarta.persistence.*;
import user.User;

@Entity
@Table(name = "apiusage")
public class APIUsage {
	
	    @Id 
	    @GeneratedValue
	    private Long Aid;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	    private User user;

	    private String endpoint;
	    private int callCount;
	    private LocalDate date;
	    
		public Long getId() {
			return Aid;
		}
		public void setId(Long id) {
			this.Aid = id;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
		public int getCallCount() {
			return callCount;
		}
		public void setCallCount(int callCount) {
			this.callCount = callCount;
		}
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
}
