package com.go_bus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.go_bus.pojos.UserEntity;
import com.go_bus.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {
//depcy
	@Autowired
	private UserRepository travelerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// invoke dao' s method
		UserEntity travelerEntity = travelerRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email not found !!!!!"));
		return new CustomUserDetailsImpl(travelerEntity);
	}

}
