FROM maven:3.6.1-jdk-8
VOLUME /tmp
ADD . / purchase-manager/
EXPOSE 8083
CMD (cd purchase-manager/; mvn spring-boot:run -Denvironment=compose;)
