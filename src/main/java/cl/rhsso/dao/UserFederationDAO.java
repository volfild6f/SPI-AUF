package cl.rhsso.dao;

import java.util.List;

import cl.rhsso.vo.UserInfoVO;

public interface UserFederationDAO {

//	@NamedQueries({
//        @NamedQuery(name="getUserByUsername", query="select u from UserInfoVO u where u.userName = :username"),
//        @NamedQuery(name="isValid", query="select u from UserInfoVO u where u.userName = :username and u.password = :password"),
//        @NamedQuery(name="getAllUsers", query="select u from UserInfoVO u order by u.userName"),
//        @NamedQuery(name="searchForUser", query="select u from UserInfoVO u where " +
//                "( lower(u.userName) like :search or u.email like :search ) order by u.userName"),
//        @NamedQuery(name="updateCredential", query = "update UserInfoVO set atributo= :atributo, email= :email, first_name= :firstName, last_name= :lastname, password= :password, userName= :username WHERE id= :id"),
//        @NamedQuery(name="addUser", query="insert into UserInfoVO (atributo, email, first_name, last_name, password, userName) VALUES (:atributo, :email, :firstname, :lastname, :password, :username)"),
//})
	
	public UserInfoVO getUserByUsername(String username);
	
	
	public boolean isValid(String username, String password);
	
	
	public List<UserInfoVO> getAllUsers(int firstResult, int maxResults);
	
	
	public List <UserInfoVO> searchForUser(String username);
	
	
	public boolean updateCredential(String id, String username, String email, String first_name, String last_name, String password);
	
	
	public boolean addUser(String id, String username, String email, String first_name, String last_name, String password, String atributo);
	
	public boolean removeUser(String id);
	 
}
