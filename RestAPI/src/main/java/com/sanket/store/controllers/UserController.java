package com.sanket.store.controllers;

import com.sanket.store.dtos.ChangePasswordRequest;
import com.sanket.store.dtos.RegisterUserRequest;
import com.sanket.store.dtos.UpdateUserRequest;
import com.sanket.store.dtos.UserDto;
import com.sanket.store.mappers.UserMapper;
import com.sanket.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) //returns UserDto
    {
        if(!Set.of("name", "email").contains(sortBy))
        {
            sortBy ="name";
        }
       return userRepository.findAll(Sort.by(sortBy))
               .stream()//using stream API to map user object to user Dto
               .map(userMapper::toDto)
               .toList(); //map returns a stream object, So we are converting it to List.

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) //parameter id is of the same type as in User entity/table
    {
        var user = userRepository.findById(id).orElse(null);// reurns an alternate vaule "null" if user is not found or we can throw exception also
        if(user==null)
        {
            return ResponseEntity.notFound().build(); //or we can use=> return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //var userDto = new UserDto(user.getId(),user.getName(),user.getEmail());
        return ResponseEntity.ok(userMapper.toDto(user));//or we can use=> return new ResponseEntity<>(userDto,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder)
    {
        //1st mapping the request with the user entity
        var user = userMapper.toEntity(request);
        userRepository.save(user);//now, once we have got the user, we can save it in the repository and Database will assign id to this user.
        //next we will map it to the UserDto and return it:

        var userDto = userMapper.toDto(user);
        //NOW, once we map the user with Dto we can call the path().
        var uri=uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri(); //this returns the uri object

       // return ResponseEntity.created(uri);// now this returns a "BodyBuilder" type entity
        return ResponseEntity.created(uri).body(userDto);//now, providing userDto in the Body of the response

        //.created(): this method requires parameter as URI location, which is the location of the newly created user, like: localhost:8080/users/5


    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request)
    {
        //for updating resources first we should check whether the resource exists if not then show 400 error.
        var user = userRepository.findById(id).orElse(null);
        if(user==null)//returning 404 error if resource is not present for updating
        {
            return ResponseEntity.notFound().build();
        }

        //Now if resource exists then update it with the data in our request

        //1st way:  below is the one way to do it by manual mapping without using Usermapper, but it is suitable for small objects
            //user.setName(request.getName());
            //user.setEmail(request.getEmail());

        //2nd way: By using UserMapper>>

        //Now once we have the user object, we can call .update()
        userMapper.update(request,user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));//we are mapping the user to the userDto and sending it as response

    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id) //passing void because we dont want to pass any data to the client
    {
        //Just like Update method we should first get the user freom the UserRepository and if it doesnt exist return 404
        var user = userRepository.findById(id).orElse(null);
        if(user==null)//returning 404
        {
            return ResponseEntity.notFound().build();
        }

        //delete the user if it exists in the DB
        userRepository.delete(user);
        return ResponseEntity.noContent().build();


    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request)
    {
        //Just like Update method we should first get the user freom the UserRepository and if it doesnt exist return 404
        var user = userRepository.findById(id).orElse(null);
        if(user==null)//returning 404
        {
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword()))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //if the old password matches then change the password
        user.setPassword(request.getNewPassword());//we are not using mapper here becuase this is only small field
        return ResponseEntity.noContent().build();

    }

}
