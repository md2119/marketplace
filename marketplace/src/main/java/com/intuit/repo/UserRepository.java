package com.intuit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.base.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	User findById(Long id);
	List<User> findByType(String type);

}
