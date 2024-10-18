/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.utility;

import java.util.ResourceBundle;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class ResourceProperties implements Constants {

  public static final String DEFAULT_BASE_URL = "base.url";
  public static final String DEFAULT_SENDER_MAIL = "sayaligondane23@gmail.com";

  public static final String EXCEPTION_01 = "ex.01";
  public static final String EXCEPTION_02 = "ex.02";
  public static final String EXCEPTION_03 = "ex.03";
  public static final String EXCEPTION_04 = "ex.04";

  private ResourceProperties() {
  }

  /**
   * Returns a message using a template from a keyed message store and specified
   * parameters.
   *
   * Eventually, these messages will need to be read from a property bundle file.
   *
   * @param key message template key
   * @return formatted message
   */
  public static String getMessage(String key) {
    return ResourceBundle.getBundle(EXCEPTION_MESSAGES).getString(key);
  }
}
