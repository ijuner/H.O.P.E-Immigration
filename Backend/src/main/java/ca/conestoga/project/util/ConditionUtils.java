package ca.conestoga.project.util;

import java.util.Locale;

public class ConditionUtils {
  public static boolean conditionGreater(int source, int target) {
    return source > target;
  }

  public static boolean conditionLess(int source, int target) {
    return source < target;
  }

  public static boolean conditionEqual(int source, int target) {
    return source == target;
  }
  public static boolean conditionEqualString(String source, String target) {
    return source.equals(target);
  }


  public static boolean conditionBetween(int source, int targetLess, int targetGreater) {
    return (source >= targetLess) && (targetGreater >= source);
  }

  /**
   * generate a method name from the field name
   * @param fieldName
   * @return
   */
  public static String generateMethodName(String fieldName) {
    // upper case the first letter of the field
    String upperCaseFieldName = fieldName.substring(0,1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
    return "get".concat(upperCaseFieldName);
  }

}
