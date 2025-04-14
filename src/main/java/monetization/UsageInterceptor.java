package monetization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class UsageInterceptor implements HandlerInterceptor {

    @Autowired
    private MonetizationService monetizationService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.contains("/public/") || path.contains("/payment/")) {
            return true;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        String username = authentication.getName();
        String endpoint = request.getRequestURI();
        
        if (!monetizationService.canMakeAPICall(username, endpoint)) {
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            response.getWriter().write("API call limit reached for your plan. Please upgrade to continue.");
            return false;
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String path = request.getRequestURI();
        if (path.contains("/public/") || path.contains("/payment/")) {
            return;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String endpoint = request.getRequestURI();
            monetizationService.recordAPICall(username, endpoint);
        }
    }
}