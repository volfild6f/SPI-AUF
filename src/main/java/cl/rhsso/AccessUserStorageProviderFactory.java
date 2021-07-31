package cl.rhsso;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;
import org.keycloak.storage.UserStorageProviderModel;
import org.keycloak.storage.user.ImportSynchronization;
import org.keycloak.storage.user.SynchronizationResult;

import cl.rhsso.vo.UserInfoVO;

public class AccessUserStorageProviderFactory implements UserStorageProviderFactory<AccessUserStorageProvider>, ImportSynchronization {

    private static final Logger logger = Logger.getLogger(AccessUserStorageProviderFactory.class);
    
    public static final String PROVIDER_NAME = "access-user-federation";
    protected List<ProviderConfigProperty> configMetadata;
    
    {
        configMetadata = ProviderConfigurationBuilder.create()
                .property().name("jdbcUrl").type(ProviderConfigProperty.STRING_TYPE).label("jdbcUrl").defaultValue("jdbc:jtds:sqlserver://{host}:{port}/{database}").helpText("Url JDBC driver database").add()
                .property().name("userName").type(ProviderConfigProperty.STRING_TYPE).label("userName").helpText("Username database").add()
                .property().name("driver").type(ProviderConfigProperty.STRING_TYPE).label("driver").defaultValue("net.sourceforge.jtds.jdbc.Driver").helpText("Driver JDBC Class").add()
        		.property().name("password").type(ProviderConfigProperty.PASSWORD).label("password").helpText("Password Database").add().build();
    }
    
    
    private static final String JNDI_EJB_PROVIDER = "java:global/access-user-federation-1.0.0/";
    
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }
    
    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
    
    @Override
    public AccessUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        
        AccessUserStorageProvider provider = null;
        try {
        	InitialContext ctx = new InitialContext();
        	
			provider = (AccessUserStorageProvider)ctx.lookup(JNDI_EJB_PROVIDER + AccessUserStorageProvider.class.getSimpleName());
			
			provider.setModel(model);
			provider.setSession(session);
			
			
			Properties props = new Properties();
	        props.setProperty("jdbcUrl", model.getConfig().getFirst("jdbcUrl"));
	        props.setProperty("userName", model.getConfig().getFirst("userName"));
	        props.setProperty("password", model.getConfig().getFirst("password"));
	        props.setProperty("driver", model.getConfig().getFirst("driver"));
		    provider.setProperties(props);
		    
		    System.out.println("jdbc url: " + model.getConfig().getFirst("jdbcUrl"));
		    System.out.println("jdbc username: " + model.getConfig().getFirst("userName"));
		    System.out.println("jdbc password: " + model.getConfig().getFirst("password"));
		    System.out.println("jdbc driver: " + model.getConfig().getFirst("driver"));
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
        return provider;
    }

	@Override
	public SynchronizationResult sync(KeycloakSessionFactory sessionFactory, String realmId,
			UserStorageProviderModel model) {
		InitialContext ctx;
		
		final SynchronizationResult syncResult = new SynchronizationResult();

//		try {
//			ctx = new InitialContext();
//			AccessUserStorageProvider localProvider = (AccessUserStorageProvider)ctx.lookup(JNDI_EJB_PROVIDER + AccessUserStorageProvider.class.getSimpleName());
//			 
//			for (UserInfoVO user: localProvider.getAllUsers()) {
//				syncResult.increaseAdded();
//				logger.debug("user: "+user.toString());
//			}
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		
		logger.debug("dentro de sync");
		return syncResult;
	}

	@Override
	public SynchronizationResult syncSince(Date lastSync, KeycloakSessionFactory sessionFactory, String realmId,
			UserStorageProviderModel model) {
		
		InitialContext ctx;
		
		final SynchronizationResult syncResult = new SynchronizationResult();

//		try {
//			ctx = new InitialContext();
//			AccessUserStorageProvider localProvider = (AccessUserStorageProvider)ctx.lookup(JNDI_EJB_PROVIDER + AccessUserStorageProvider.class.getSimpleName());
//			 
//			for (UserInfoVO user: localProvider.getAllUsers()) {
//				syncResult.increaseAdded();
//				logger.debug("user: "+user.toString());
//			}
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		
		logger.debug("dentro de sync");
		return syncResult;
	}
}
