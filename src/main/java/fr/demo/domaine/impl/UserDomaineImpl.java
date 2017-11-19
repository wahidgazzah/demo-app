package fr.demo.domaine.impl;

import java.util.List;

import fr.demo.data.accessdata.DataInstance;
import fr.demo.domaine.IUserDomaine;
import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;

public class UserDomaineImpl implements IUserDomaine{
	
	private DataInstance acessData = DataInstance.getInstance();
	
	public String create(UserDTO dto) throws CustomException{
				
		return acessData.createUser(dto);
	}

	public String delete(String reference) throws CustomException{
		
		return acessData.deleteUser(reference);
	}

	public String update(UserDTO dto) throws CustomException{
		
		return acessData.updateUser(dto);
	}

	public UserDTO get(String reference) throws CustomException{
		
		return acessData.getUser(reference);
	}

	public List<UserDTO> getAll() throws CustomException{
		
		return acessData.getUsers();
	}
}
