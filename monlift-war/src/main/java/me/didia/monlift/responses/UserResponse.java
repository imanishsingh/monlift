package me.didia.monlift.responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.didia.monlift.exceptions.MonliftException;
import me.didia.monlift.visitors.ResponseLinkToVisitor;
import me.didia.monlift.visitors.ResponseVisitor;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements IResponse<me.didia.monlift.entities.User> {
	public Long id;
	public String firstname;
	public String lastname;
	public String email;
	public String phone;
	public String username;
	public String fullname;
	public Boolean isDriver;
	public HashMap<String, String> linkTo = new HashMap<String, String>();
	
	public void build(me.didia.monlift.entities.User user) {
		id = user.getId();
		firstname = user.getFirstname();
		lastname = user.getLastname();
		email = user.getEmail();
		phone = user.getPhone();
		fullname = firstname + " " + lastname;
		isDriver = user.isDriver();
		if(user.isDriver())
			username = user.getUsername();
		buildLinkTo();
		
		
		
	}
	
	public void build(me.didia.monlift.entities.User user, String[] fields) throws MonliftException
	{
		List<String> unknownFields = new ArrayList<String>();
		for(int i=0; i<fields.length;i++)
		{
			String field = fields[i];
			switch(field){
			case "username":
				username = user.getUsername();
				break;
			case "firstname" :
				firstname = user.getFirstname();
				break;
			case "lastname":
				lastname = user.getLastname();
				break;
			case "fullname":
				fullname = user.getFirstname() + " " + user.getLastname();
				break;
			case "driver":
				isDriver = user.isDriver();
				break;
			case "email":
				email = user.getEmail();
				break;
			case "phone":
				phone = user.getPhone();
				break;
			default:
				unknownFields.add(field);
				break;
			}
		}
		
		if(unknownFields.size() != 0)
		{
			throw new MonliftException("Bad Request: Unknown field: " + unknownFields.toString());
		}
		
		
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	@Override
	public void blurPrivate() {
		this.email = null;
		this.phone = null;
	}
	/**
	 * @return the driver
	 */
	public Boolean isDriver() {
		return isDriver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Boolean driver) {
		this.isDriver = driver;
	}

	@Override
	public void setLinkTo(HashMap<String, String> p_linkTo) {
		linkTo = p_linkTo;
		
	}

	@Override
	public void accept(ResponseVisitor p_visitor) {
		p_visitor.visit(this);
		
	}

	@Override
	public void buildLinkTo() {
		accept(new ResponseLinkToVisitor());
		
	}
	
	
}
