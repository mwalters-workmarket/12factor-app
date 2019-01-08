# 12factor-app

Toy app highlighting best practices regarding app configuration in the spirit of 12-factor app


* All environment-specific configuration is externalized.

* application.yaml includes only environment-agnostic properties.. those properties whose values are independent of the environment.
  For example, we'll always deploy server on port 8080
  
* For developing locally, there's an application-local.yaml file.  You can have Spring load it by specifying `-Dspring.profiles.active=local` when deploying the JAR or building the app
  
* No env-specific properties files for qa, prod.  When the app is deployed into a real environment, the deployment framework (Nomad) will populate environment variables
  
* Spring has a peculiar property loading order, internal profile-specific property files have higher priority than external profile-nonspecific properties files
  https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config
  We avoid this completely by using only env vars in real environments


* Fail fast: Required properties should cause app to fail fast if not specified (See class `CoolFeatureConfiguration.java`)
  * If we invoke `mvn clean install` , the build will fail because required property `cool.feature.url` is purposely not found in application.yaml
  * However, `mvn clean install --spring.profiles.active=local` will complete successfully
  
  
  
The goal is for it to be impossible for property values specific to one environment to leak into another env.

For example, someone defines a thread.pool.max.size in application.yaml (erroneous because the its is env-specific), with a local-dev friendly value of 5.

Later, when deploying to production, forget to override the value, and thus the thread pool in prod is way undersized, causing issues
  
This cannot happen if we follow the steps above, for if for any env, this value is not specified (and there is no default value), the app won't even start in that env.

