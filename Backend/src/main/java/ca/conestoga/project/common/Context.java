package ca.conestoga.project.common;

import ca.conestoga.project.product.bo.user.ApiLoginUser;
import ca.conestoga.project.product.bo.user.ApiUserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Context
{
  // login user collection
  private static Map<String, ApiLoginUser> loginUsers = new ConcurrentHashMap<>();

  // new login
  public static String addLoginUser(ApiLoginUser entity) {
    // generate a token for login user
    // simple token generation, no encryption, no user information
    UUID uuid = UUID.randomUUID();
    loginUsers.put(uuid.toString(), entity);
    return uuid.toString();
  }

  // get login user by user name
  public static ApiLoginUser getLoginUser(String token) {
    return loginUsers.get(token);
  }

  public static void removeLoginUser(String token) {
    loginUsers.remove(token);
  }

  private static Map dic = new ConcurrentHashMap<>();
  // data dictionary
  public static Object dictionary(String key) {
      if(dic.isEmpty()) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          Map data = objectMapper.readValue(ResourceUtils.getFile("classpath:" + "dictionary.json"), Map.class);
          dic.putAll(data);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    if(StringUtils.hasText(key)) {
      return dic.get(key);
    }
    return dic;
  }
}
