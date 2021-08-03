Referencias:

https://www.keycloak.org/docs/latest/server_development/index.html#_user-storage-spi
https://access.redhat.com/documentation/en-us/red_hat_single_sign-on/7.1/html/server_developer_guide/user-storage-spi#importsynchronization_interface
https://github.com/keycloak/keycloak/blob/master/federation/ldap/src/main/java/org/keycloak/storage/ldap/LDAPStorageProviderFactory.java


example class method
	@SuppressWarnings("unchecked")
	public List<UserInfoVO> testLista(){
		
		
		  // Hibernate 5.4 SessionFactory example without XML
		  Map<String, String> settings = new HashMap<>();
		  settings.put("hibernate.connection.driver_class", "net.sourceforge.jtds.jdbc.Driver");
		  settings.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		  settings.put("hibernate.connection.url", "jdbc:jtds:sqlserver://192.168.1.102:1433/master");
		  settings.put("hibernate.connection.username", "SA");
		  settings.put("hibernate.connection.password", "Asdqwe123*");
		  settings.put("hibernate.show_sql", "true");
		  settings.put("hibernate.format_sql", "true");
		  
		  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		                                    .applySettings(settings).build();

		  MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		  Metadata metadata = metadataSources.buildMetadata();

		  // here we build the SessionFactory (Hibernate 5.4)
		  SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		  
		  
		  Session session = sessionFactory.getCurrentSession();		
		
        List<UserInfoVO> empList = null;
        
		try{
            List<UserInfoVO> query =  session.createCriteria(UserInfoVO.class).list();
            
            for (int i = 0; i < query.size() ; i++) {
				System.out.println("lkjalsdjlaksjd lkasj dlksaj d");
			}
            
//            empList = query.list();
		}catch (RuntimeException e) {
			logger.error(ERROR_GENERIC_DATABASE, e);
		}
		return empList;	
	}
	