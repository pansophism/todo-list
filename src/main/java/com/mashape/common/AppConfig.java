package com.mashape.common;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yxzhao on 10/18/14.
 */
public class AppConfig extends CompositeConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
  private static AppConfig instance;

  public synchronized static AppConfig getInstance() {
   if(instance == null) {
     initProperties();
   }
    return instance;
  }

  private static void initProperties() {
    instance = new AppConfig();
    instance.addConfiguration(new SystemConfiguration());
    try
    {
      instance.addConfiguration(new PropertiesConfiguration("instance.properties"));
    }
    catch (ConfigurationException e)
    {
      LOG.error("ConfigurationException : ", e);
      throw new RuntimeException("Cannot find instance.properties. Not able to proceed.");
    }
  }
}
