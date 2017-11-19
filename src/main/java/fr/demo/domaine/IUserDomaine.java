package fr.demo.domaine;

import java.util.List;

import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;

public interface IUserDomaine {

	public String create(UserDTO dto) throws CustomException;

	public String delete(String reference) throws CustomException;

	public String update(UserDTO dto) throws CustomException;

	public UserDTO get(String reference) throws CustomException;

	public List<UserDTO> getAll() throws CustomException;
}
