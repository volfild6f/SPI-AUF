package cl.rhsso.vo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.NamedQueries;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */

//@Entity
//@Table(name="usuario") //Nombre de la tabla de los usuarios del cliente
public class UserInfoVO implements Serializable{

	private static final long serialVersionUID = 1L;
    
	//Todos los atributos a setear en la tabla de usuarios y se debe cambiar segun la tabla del cliente
	//@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    //@Column(name="id")
	private long id;
	
	//@Column(name="email", length=255) 
	private String email;
	
	//@Column(name="last_name", length=255)
	private String lastName;
	
	//@Column(name="first_name", length=255)
	private String firstName;
	
	//@Column(name="userName", length=255, unique = true)
	private String userName;
	
	//@Column(name="password", length=255)
	private String password;

	//@Column(name="atributo", length=255)
	private String atributo;
	
	public long getId() {       //se agrega el seteo del id
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "UserInfoVO [id=" + id + ", email=" + email + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", userName=" + userName + ", password=" + password + ", atributo=" + atributo + "]";
	}

}
