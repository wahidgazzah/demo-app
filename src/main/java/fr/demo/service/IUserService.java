package fr.demo.service;

import java.util.List;

import fr.demo.dto.UserDTO;
import fr.demo.exception.CustomException;

/**
 * Service interface for users.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public interface IUserService {

	public String create(UserDTO dto) throws CustomException;

	public String delete(String reference) throws CustomException;

	public String update(UserDTO dto) throws CustomException;

	public UserDTO get(String reference) throws CustomException;

	public List<UserDTO> getAll() throws CustomException;
}
