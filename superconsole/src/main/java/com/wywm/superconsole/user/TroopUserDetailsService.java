package com.wywm.superconsole.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class TroopUserDetailsService implements UserDetailsService {
	@Autowired
	private TroopUserDetailsService service;
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new TroopUserDetails(user);
	}

	@Autowired
	private UserRepository repo2;

	public List<User> listAll() {
		return repo2.findAll(Sort.by("email").ascending());
	}

}
