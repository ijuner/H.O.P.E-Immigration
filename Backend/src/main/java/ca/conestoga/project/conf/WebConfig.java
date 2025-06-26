package ca.conestoga.project.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // add to all paths
            .allowCredentials(true) // is sent to Cookie
            .allowedOriginPatterns("*") // all origins allowed
            .allowedMethods("GET", "POST", "PUT", "DELETE") // all methods allowed
            .allowedHeaders("*")
            .exposedHeaders("*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 映射pdf目录
    registry.addResourceHandler("/pdf/**").addResourceLocations("file:pdf/");
  }
}
