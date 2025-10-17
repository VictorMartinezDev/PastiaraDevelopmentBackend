package com.pastiara.app.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.*;

import com.pastiara.app.model.User;
import com.pastiara.app.repository.UserInfoRepository;
import com.pastiara.app.repository.UserRepository;

@Component
@Order(1)
@Profile("mysql")
public class UserInfoDateLoader implements CommandLineRunner{
	
	
	//private final UserInfoRepository userInfoRepository;
	private final UserRepository userRepository;
	
	
	public UserInfoDateLoader(UserInfoRepository userInfoRepository, UserRepository userRepository) {
		super();
		//this.userInfoRepository = userInfoRepository;
		this.userRepository = userRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//Agregamos usuarios
		System.out.println("Probando guardar los datos de un usuario");
		userRepository.save(new User("vic@gmail.com","122345"));
		userRepository.save(new User("vicmauel@gmail.com","12234325"));
		userRepository.save(new User("pastiara@gmail.com","4234sdfs"));
		//Borramos usuarios
		userRepository.deleteById(2L);
		
	}

}
