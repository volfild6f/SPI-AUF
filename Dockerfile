#FROM de conexión para la descarga de imagen oficial
#FROM registry.redhat.io/rh-sso-7/sso74-openshift-rhel8
FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8
#usuario a utilizar dentro de la imagen
USER root

RUN mkdir -p opt/eap/extensions
#copiar los archivos necesarios
COPY access-user-federation-1.0.0.jar /opt/eap/standalone/deployments
COPY mssql-jdbc-9.4.0.jre8.jar /opt/eap/extensions/jdbc-driver.jar
COPY sso-extensions.cli /opt/eap/extensions
#COPY jtds-1.3.1.jar /opt/eap/extensions/
#jar a deployment

COPY jtds.tar.gz /opt/eap/modules/

RUN cd /opt/eap/modules/ && \
    tar -xvf /opt/eap/modules/jtds.tar.gz

#COPY sso-extensions.cli /opt/eap/extensions

#creación del usuario admin

#RUN /opt/eap/bin/add-user.sh -u admin -p admin
#RUN /opt/eap/bin/add-user-keycloak.sh -u admin -p admin

#cambiar al usuario de openshift
#USER 185
