package cl.rhsso;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
//import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
//import org.keycloak.common.util.EnvUtil;
import org.keycloak.component.ComponentModel;
//import org.keycloak.credential.CredentialAuthentication;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.provider.ProviderConfigProperty;
//import org.keycloak.representations.UserInfo;
//import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

//import org.keycloak.quickstart.storage.user.*;
import cl.rhsso.dao.UserFederationDAO;
import cl.rhsso.dao.impl.UserFederationDAOImpl;
//import cl.rhsso.utils.CypherBCryptPass;
import cl.rhsso.vo.UserInfoVO;

/**
 * <h1>Provider principal de control de comportamiento RHSSO Data Base</h1>
 * Sobreescribe métodos de comportamiento para la sincronización, autenticación
 * y búsqueda de usuarios para una federación administrada a través de base de
 * datos.
 * 
 * @author Red Hat
 * @version 1.0
 * @since 2021-02-15
 */

@Stateful
@Local(AccessUserStorageProvider.class)
public class AccessUserStorageProvider implements UserStorageProvider,
		//UserFederationProvider,
		UserLookupProvider, 
		UserQueryProvider,
		UserRegistrationProvider,
		CredentialInputUpdater,
		CredentialInputValidator,
		OnUserCache,
		FormAction, FormActionFactory{
	
	private static Logger logger = Logger.getLogger(AccessUserStorageProvider.class.getName());
    //public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

	
	protected KeycloakSession session;
	protected Properties properties;
	protected ComponentModel model;

	public KeycloakSession getSession() {
		return session;
	}

	public void setSession(KeycloakSession session) {
		this.session = session;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public ComponentModel getModel() {
		return model;
	}

	public void setModel(ComponentModel model) {
		this.model = model;
	}

	protected Map<String, UserModel> loadedUsers = new HashMap<>();

//	@PersistenceContext
//	protected EntityManager em;

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		logger.info("1 - dentro de getUserById AccessUser");
		StorageId storageId = new StorageId(id);
		String username = storageId.getExternalId();

		return getUserByUsername(username, realm);
	}
	
	@Override
	/**
	 * Obtiene toda la información del usuario solicitando hacia la base de datos,
	 * la búsqueda se realiza mediante username como atributo
	 *
	 * @param username nombre de usuario el cual se buscará en base de datos
	 * @param realm    nombre del reino en el cual se esté generando la búsqueda
	 * @return the value {@code 0} if {@code x == y}; a value less than {@code 0} if
	 *         {@code x < y}; and a value greater than {@code 0} if {@code x > y}
	 */
	
	public UserModel getUserByUsername(String username, RealmModel realm) {
		logger.info("2 - va al metodo getUserByUsername dentro username: " + username+"\n");

		UserModel adapter = loadedUsers.get(username);
		if (adapter == null) {
			logger.info("3 - Linea 142 Metodo getUserByUsername -- cuando adapter es null...");

			adapter = new AbstractUserAdapterFederatedStorage(session, realm, model) {

				@Override
				public String getUsername() {
					logger.info("4 -  Linea 149 dentro de getUsername, dentro del if... " + username);
					//return null;
					return username;
				}

				@Override
				public void setUsername(String username) {
					logger.info("dentro de setUsername .. ");
				}
			};

			//TypedQuery<UserInfoVO> query = em.createNamedQuery("getUserByUsername", UserInfoVO.class);
			//query.setParameter("username", username);

			//List<UserInfoVO> result = query.getResultList();
			UserFederationDAO dao = new UserFederationDAOImpl(properties);
			UserInfoVO userinfo = dao.getUserByUsername(username);
			if(userinfo == null)
				return null;
			System.out.println("dentro de getuserbyusername 164, el usuario que se obtiene del dao: " + username);
			adapter.setUsername(userinfo.getUserName()); // TODO esto esta malo no?
			adapter.setEmail(userinfo.getEmail());
			adapter.setFirstName(userinfo.getFirstName());
			adapter.setLastName(userinfo.getLastName());
			loadedUsers.put(username, adapter);  // TODO que hace esta linea
			
			adapter.setCreatedTimestamp(System.currentTimeMillis());
			adapter.setEnabled(true);
			adapter.setSingleAttribute("ATRIBUTO", userinfo.getAtributo()); // TODO mcontreras revisar este atributo con jorwin
			
			logger.info("Linea 172 -- captura de lastname: " + adapter.getLastName());
			
		}

		return adapter;
	}

	protected UserModel createAdapter(RealmModel realm, String username) {
		return new AbstractUserAdapterFederatedStorage(session, realm, model) {
			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public void setUsername(String username) {
				String pw = (String) properties.remove(username);
				if (pw != null) {
					logger.info("Password no existe");
				}
			}
		};
	}


	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		logger.info("Linea 200 --dentro getUserByEmail AccesUser");
		//StorageId storageId = new StorageId(id);
		//String username = storageId.getExternalId();
		
		return null;
		//return getUserByUsername(username, realm); // este return esta mal
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		logger.info("dentro isConfiguredFor");
		String password = user.getUsername();
		//String password = properties.getProperty(user.getUsername());
		logger.info("isConfiguredFor - valor password: " + password);
		return credentialType.equals(CredentialModel.PASSWORD) && password != null;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		logger.info("dentro supportsCredentialType -> " + credentialType.equals(CredentialModel.PASSWORD));
		return credentialType.equals(CredentialModel.PASSWORD);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		
		
		String jdbcUrl = properties.getProperty("jdbcUrl");
		System.out.println("tirando jdbcURL: " + jdbcUrl);
		logger.info("Linea 222 --dentro isValid... ");
		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel))
			return false;

		UserCredentialModel cred = (UserCredentialModel) input;
		String usuario = user.getUsername();
		logger.info("Linea 231 --se obtiene username en isValid AccessUser");
		
		UserFederationDAO dao = new UserFederationDAOImpl(properties);
		return dao.isValid(usuario, cred.getValue());
				
	}
	
	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
		//referencia: https://howtodoinjava.com/jpa/jpa-native-update-sql-query-example/
		logger.info("Linea 253 --dentro updateCredential AccessUser");
		UserCredentialModel cred = (UserCredentialModel) input;
		//logger.info("updateCredential - password: " + cred.getValue()); //Verificando password		

		//Funcional:
		/*TO-DO: El atributo nuevo no esta entrando desde base de datos.*/
		/*TO-DO: Si el valor es nulo, dejar nullo y que no aparezca null en la base de datos*/
		
		UserFederationDAO dao = new UserFederationDAOImpl(properties);
		boolean resultado = dao.updateCredential(user.getId(),user.getUsername(),user.getEmail(),user.getFirstName(),user.getLastName(),cred.getValue());
//		String id, String username, String email, String first_name, String last_name,
//		String password
//		Query query = em.createQuery("update UserInfoVO set "
//				+ "atributo='no llega atributo de sesion', email='"+user.getEmail()+"', "
//				+ "first_name='"+user.getFirstName()+"', last_name='"+user.getLastName()+"', password='"+ cred.getValue() + "', userName='"+user.getUsername()+"' "
//				+ "WHERE userName='"+user.getUsername()+"'");

//		query.executeUpdate(); 
		if (resultado == true)
			return true;
		else {
			return false;
		}
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
		logger.info("dentro disableCredentialType");
	}

	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
		logger.info("dentro getDisableableCredentialTypes");
		return Collections.emptySet();
	}

	@Override
	public void close() {
		logger.info("5 - Linea 305 AccessUser --dentro close");
	}

	@Override
	public int getUsersCount(RealmModel realm) {
		logger.info("pasando por getUsersCount");
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getUsers(RealmModel realm) {
		logger.info("pasando por public List<UserModel> ");
		System.out.println("pasando por public List<UserModel> ");
		return (List<UserModel>) new Object();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
		logger.info("pasando por getUsers(RealmModel realm, int firstResult, int maxResults)");
		return (List<UserModel>) new Object();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm) {
		logger.info("pasando por searchForUser(String search, RealmModel realm)");
		return (List<UserModel>) new Object();
	}

	@Override
	// Método que busca a 1 usuario o varios
	public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
		logger.info("pasando por searchForUser(String search, RealmModel realm, int firstResult, int maxResults)");
		List<UserModel> users = new LinkedList<>();

		
		UserFederationDAO dao = new UserFederationDAOImpl(properties);
				
					
		//TypedQuery<UserInfoVO> query = em.createNamedQuery("searchForUser", UserInfoVO.class);
		//query.setParameter("search", search);
						
		List<UserInfoVO> listUserInfoVO = dao.searchForUser(search);

		logger.info("listUserInfoVO.size(): " + listUserInfoVO.size());

		for (UserInfoVO userResult : listUserInfoVO) {
			UserModel user = getUserByUsername(userResult.getUserName(), realm);
			users.add(user);
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
		logger.info("pasando por searchForUser(Map<String, String> params, RealmModel realm)");
		return (List<UserModel>) new Object();
	}

	@Override
	// Metodo que busca a todos los usuarios
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
			int maxResults) {
		logger.info("pasando por searchForUser... AccessUser");

		List<UserModel> users = new LinkedList<>();
		
		UserFederationDAO dao = new UserFederationDAOImpl(properties);
		List<UserInfoVO> listUserInfoVO = dao.getAllUsers(firstResult, maxResults);
		
		logger.info("listUserInfoVO.size(): " + listUserInfoVO.size());

		for (UserInfoVO userResult : listUserInfoVO) {
			UserModel user = getUserByUsername(userResult.getUserName(), realm);
			users.add(user);
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
		logger.info("pasando por getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults)");
		return (List<UserModel>) new Object();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
		logger.info("pasando por getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults)");
		return (List<UserModel>) new Object();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
		logger.info("pasando por searchForUserByUserAttribute");
		return (List<UserModel>) new Object();
	}

	@Override
	public UserModel addUser(RealmModel realm, String username) {
		
        logger.info("Dentro de AccessUser --Agrega usuario: " + username);
        
		UserInfoVO entity = new UserInfoVO();
        UserFederationDAO dao = new UserFederationDAOImpl(properties);
        dao.addUser("6", username,"gg@gmail.com","fran","dimitri","4321","abc");
		
        entity.setId(6);
        entity.setUserName(username);
        //em.persist(entity);
        //logger.info("added user: " + username);
        //return null;
        return new UserAdapter(session, realm, model, entity);
	}

	
	@Override
	public boolean removeUser(RealmModel realm, UserModel user) {
		logger.info("Dentro de AccessUser --Remove from user: " + user.getUsername());
        //String persistenceId = StorageId.externalId(user.getId());
        
        UserFederationDAO dao = new UserFederationDAOImpl(properties);
        return dao.removeUser(user.getUsername());
         
	}

    public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

	@Override
	public void onCache(RealmModel realm, CachedUserModel user, UserModel delegate) {
		logger.info("Linea 460 --Dentro de OnCache.....");
		logger.info("OnCache user.getEmail " + user.getEmail());
		logger.info("Oncache delegate.getEmail " + delegate.getEmail());
	}
	//ver los sysout

	@Override
	public FormAction create(KeycloakSession session) {
		logger.info(" Linea 468 --Dentro de FormAction create ..");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Scope config) {
		logger.info(" Linea 475 --Dentro de init(Scope config) ..");
		// TODO Auto-generated method stub	
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		// TODO Auto-generated method stub
		logger.info(" Linea 82 --Dentro de postInit(KeycloakSessionFactory factory) ..");
	}

	@Override
	public String getId() {
		logger.info("Linea 486 --pasando por getId");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayType() {
		logger.info("Linea 493 --pasando por getDisplayType");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReferenceCategory() {
		logger.info("Linea 501 --pasando por getReferenceCategory");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConfigurable() {
		System.out.println("Linea 508 --pasando por isConfigurable");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		logger.info("pasando por getRequerimentChoices");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserSetupAllowed() {
		logger.info("pasando por isUserSetupAllowed");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHelpText() {
		logger.info("pasando por getHelpText ..");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		logger.info("pasando por getConfigProperties ..");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildPage(FormContext context, LoginFormsProvider form) {
		logger.info("Linea 543 -- Pasando por buildPage(FormContext context, LoginFormsProvider form)");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate(ValidationContext context) {
		logger.info(" Linea 550 --Pasando por validate(ValidationContext context) ..");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void success(FormContext context) {
		logger.info(" Linea 557 --Pasando por success ..");
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requiresUser() {
		logger.info(" Linea 564 --Pasando por requiresUser ..");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		logger.info(" Linea 571 --Pasando por configuredFor(KeycloakSession session, RealmModel realm, UserModel user) ..");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		logger.info(" Linea 578 --Pasando por setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user)");
		// TODO Auto-generated method stub
		
	}
	
}