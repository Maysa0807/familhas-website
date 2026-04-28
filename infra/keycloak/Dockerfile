FROM quay.io/keycloak/keycloak:26.4.0

USER root
COPY providers/ojdbc17.jar /opt/keycloak/providers/ojdbc17.jar
COPY scripts/start-with-keystore.sh /opt/keycloak/scripts/start-with-keystore.sh
RUN sed -i 's/\r$//' /opt/keycloak/scripts/start-with-keystore.sh && chmod +x /opt/keycloak/scripts/start-with-keystore.sh
USER 1000
ENTRYPOINT ["/opt/keycloak/scripts/start-with-keystore.sh"]
