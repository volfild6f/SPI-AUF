#FROM de conexión para la descarga de imagen oficial
#FROM registry.redhat.io/rh-sso-7/sso74-openshift-rhel8
FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8
#usuario a utilizar dentro de la imagen
#USER root

COPY sso-extensions.cli /opt/eap/extensions/

#copiar los archivos necesarios
COPY access-user-federation-1.0.0.jar /opt/eap/standalone/deployments
#jar a deployment
 
#creación del usuario admin

#RUN /opt/eap/bin/add-user.sh -u admin -p admin
#RUN /opt/eap/bin/add-user-keycloak.sh -u admin -p admin

#cambiar al usuario de openshift
#USER 185
