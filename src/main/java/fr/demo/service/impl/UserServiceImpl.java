package fr.demo.service.impl;

import java.util.List;

import fr.demo.domaine.IUserDomaine;
import fr.demo.domaine.impl.UserDomaineImpl;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;
import fr.demo.service.IUserService;

public class UserServiceImpl implements IUserService{
	
	private IUserDomaine userDomaine = new UserDomaineImpl();

	
	public String create(UserDTO dto) throws CustomException{
		
		return userDomaine.create(dto);
	}

	public String delete(String reference) throws CustomException{
		
		return userDomaine.delete(reference);
	}

	public String update(UserDTO dto) throws CustomException{
		
		return userDomaine.update(dto);
	}

	public UserDTO get(String reference) throws CustomException{
		
		return userDomaine.get(reference);
	}

	public List<UserDTO> getAll() throws CustomException{
		
		return userDomaine.getAll();
	}	

}
