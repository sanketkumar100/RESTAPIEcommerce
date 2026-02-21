package com.sanket.store.repositories;

import com.sanket.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long>    //Using JpaRepository instead of CrudRepository, because now the findAll() used in UserController will return List instead of Iterable and we can use certain reecquired methods of List.
{
}
