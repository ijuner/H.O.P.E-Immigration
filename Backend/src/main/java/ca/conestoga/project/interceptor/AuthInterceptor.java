package ca.conestoga.project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.conestoga.project.annotation.Authen;
import ca.conestoga.project.common.Context;
import ca.conestoga.project.product.controller.api.ApiController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {
  private static Log log = LogFactory.getLog(AuthInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler) throws Exception {
    log.info("preHandle");
    HandlerMethod method = (HandlerMethod)handler;
    Authen authen = method.getMethodAnnotation(Authen.class);
    if(authen != null) {
      // need authentication, then check is user authenticated
      if(Context.getLoginUser(request.getHeader("Authorization")) == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
                         Object handler, ModelAndView modelAndView) throws Exception {
    log.info("postHandle");
  }
}
