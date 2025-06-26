package ca.conestoga.project.product.controller;

import ca.conestoga.project.common.Context;
import ca.conestoga.project.common.LanguageEnum;
import ca.conestoga.project.product.bo.user.ApiLoginUser;
import ca.conestoga.project.product.bo.user.ApiUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@RestController
public class HopeController {

  @Autowired
  private HttpServletRequest request;
  // get login user
  protected ApiLoginUser getLoginUser() {
    String token = getLoginToken();
    return Context.getLoginUser(token);
  }

  protected String getLoginToken() {
    return Objects.requireNonNull(request.getHeader("Authorization"));
  }

  protected String getLanguage() {
    if (request.getParameter("lang") != null) {
      String language = request.getParameter("lang");
      return LanguageEnum.findStatusByValue(language).getValue();
    }
    return Optional.ofNullable(request.getHeader("lang")).orElse("en");
  }
}
