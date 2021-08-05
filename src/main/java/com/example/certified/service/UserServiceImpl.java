package com.example.certified.service;

import com.example.certified.model.Role;
import com.example.certified.model.User;
import com.example.certified.repository.DocRepository;
import com.example.certified.repository.UserRepository;
import com.example.certified.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    /**
     * Use history repository to delete user's operations when users removed from the database
     */
    @Autowired
    private DocRepository historyRepository;
    /**
     * Encrypts the password with salt method for security(hide the user password in database)
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }


    public Object userExists(UserRegistrationDto userRegistrationDto) {
        /** Checks if User exists in database
         * @param userRegistrationDto
         *
         * */
        return userRepository.findByEmail(userRegistrationDto.getEmail());
    }

    @Override
    public String save(UserRegistrationDto registrationDto) {
        /**Save the user details(email,firstname,lastname,password) who wants to register to the application
         * @param registrationDto
         * @return success or fail to register*/
        User user = new User(registrationDto.getFirstName().trim(),
                registrationDto.getLastName().trim(), registrationDto.getEmail().trim(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        if (userExists(registrationDto) == null) {
            userRepository.save(user);
            return "redirect:/registration/success";
        }
        return "redirect:/registration?fail";
    }

    @Override
    public String saveUser(User usertosave) {
        /**Admin can add new user to database
         * @param usertosave*/
        User user = new User(usertosave.getFirstName().trim(),
                usertosave.getLastName().trim(), usertosave.getEmail().trim(),
                passwordEncoder.encode(usertosave.getPassword()), Arrays.asList(new Role("ROLE_USER")));
        if (userRepository.findByEmail(usertosave.getEmail()) == null) {
            userRepository.save(user);
            return "redirect:/showNewUserForm?success";
        }
        return "redirect:/showNewUserForm?fail";

    }

    /**
     * Update User method
     *
     * @param user
     * @return url string with success or fail messages
     * ADMIN :
     * -can update any users credentials
     * -cant update his credentials
     * USER :
     * -can update all his credentials
     * - email address must be unique
     * Email address must be unique, if not failure message will pop up
     */
    @Override
    public String updateUser(User user) {
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) authentication).getUsername();
        Long id = user.getId();
        if (user.getEmail().equals("root")) {
            user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
        } else {
            user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            this.userRepository.save(user);
            if (username.equals("root")) {
                return "redirect:/showFormForUpdateAdmin/" + id + "?success";
            } else {
                return "redirect:/showFormForUpdate?success";
            }
        } catch (Throwable throwable) {
            if (username.equals("root")) {
                System.out.println("------------------------------");
                System.out.println("ADMIN is logged in");
                return "redirect:/showFormForUpdateAdmin/" + id + "?fail";
            } else {
                System.out.println("------------------------------");
                System.out.println("USER is logged in");
                return "redirect:/showFormForUpdate?fail";
            }
        }
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException("User not found for id:" + id);
        }
        return user;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteUserById(long id) {
        User user = getUserById(id);
        if (!user.getEmail().equals("root")) {
            this.historyRepository.deleteByUser(user.getEmail());
            this.userRepository.deleteById(id);
        }
    }

    @Override
    public Page<User> findPaginate(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /**
     * Forgot My Password methods
     */
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }


}