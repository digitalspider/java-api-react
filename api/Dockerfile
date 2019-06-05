FROM alpine:edge
MAINTAINER dvittor@gmail.com
# https://www.baeldung.com/dockerizing-spring-boot-application
# http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

# Add java
RUN apk add --no-cache openjdk8
COPY ./etc/files/UnlimitedJCEPolicyJDK8/* \
  /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
# Add alias
RUN echo "alias ll='ls -la'" > /etc/profile.d/00-aliases.sh
# Setup Timezone
RUN apk add --no-cache tzdata
RUN cp /usr/share/zoneinfo/Australia/Sydney /etc/localtime
RUN echo "Australia/Sydney" >  /etc/timezone
RUN apk del tzdata

WORKDIR /opt/app

# Copy jar and properties file
COPY target/java-api.jar /opt/app/
#COPY target/application.properties /opt/app/

# Startup command
CMD ["/usr/bin/java","-jar", "/opt/app/java-api.jar","--spring.config.location=file:/opt/app"]
# Expose port
EXPOSE 8080
