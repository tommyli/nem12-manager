package co.firefire.fenergy;// Tommy Li (tommy.li@firefire.co), 2017-07-28

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

  @GetMapping(value = {"/user", "/login", "/currentlogin"})
  public Map<String, String> currentlogin(Principal principal) {
    Map<String, String> result = new HashMap<>();
    result.put("name", principal.getName());

    if (principal instanceof OAuth2Authentication && ((OAuth2Authentication) principal).getUserAuthentication() != null) {
      return getAuthDetails(((OAuth2Authentication) principal).getUserAuthentication());
    }

    return result;
  }

  @GetMapping(value = {"/me"})
  public Map<String, String> me(Principal principal) {
    Map<String, String> result = new HashMap<>();
    result.put("name", principal.getName());

    if (principal instanceof OAuth2Authentication && ((OAuth2Authentication) principal).getUserAuthentication() != null) {
      Authentication userAuth = ((OAuth2Authentication) principal).getUserAuthentication();
      if (userAuth instanceof OAuth2Authentication && ((OAuth2Authentication) userAuth).getUserAuthentication() != null) {
        return getAuthDetails(((OAuth2Authentication) userAuth).getUserAuthentication());
      }
    }

    return result;
  }

  private Map<String, String> getAuthDetails(Authentication authentication) {
    Map<String, String> authDetails = (Map<String, String>) authentication.getDetails();

    Map<String, String> result = new HashMap<>();
    result.put("email", authDetails.get("email"));
    result.put("given_name", authDetails.get("given_name"));
    result.put("family_name", authDetails.get("family_name"));
    result.put("picture", authDetails.get("picture"));
    result.put("locale", authDetails.get("locale"));

    return result;
  }
}