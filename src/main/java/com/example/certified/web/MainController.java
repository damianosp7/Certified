package com.example.certified.web;

import com.example.certified.model.User;
import com.example.certified.repository.UserRepository;
import com.example.certified.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User usertosave, BindingResult result){
        if (result.hasErrors()) {
            return "new_user";
        }
        //save user to database
        return userService.saveUser(usertosave);
//        return "redirect:/showNewUserForm?success";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result){
        //check id the logged in user is the administrator
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)authentication).getUsername();

        if (result.hasErrors() && username == "root") {
            return "update_users";
        }
        else if(result.hasErrors()){
            return "update_user";
        }
        //save user to database
        return userService.updateUser(user);
//        return "redirect:/";
    }


//    //display user
//    @GetMapping("/user/{username}")
//    public String ViewAccountPage(Model model, @PathVariable(value = "username") String username){
//        model.addAttribute("listUser", userRepository.findByEmail(username));
//        return "user";
//    }


    //display user  and take id from back-end
    @GetMapping("/user")
    public String ViewAccountPage(Model model){
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)authentication).getUsername();
        model.addAttribute("listUser", userRepository.findByEmail(username));
        return "user";
    }

    //display list of users
    @GetMapping("/users")
    public String ViewAllAccountsPage(Model model){
        return findPaginated(1,"firstName","asc", model);
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model){
        //create model attribute to bind form data
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }
//    if you want to show id in front end during update user disable this method's comments (not safe)
//    @GetMapping("/showFormForUpdate/{id}")
//    public String showFormForUpdate(@PathVariable ( value = "id") Long id, Model model) {
//        // get employee from the service
//        User user = userService.getUserById(id);
//        // set employee as a model attribute to pre-populate the form
//        model.addAttribute("user", user);
//        return "update_user";
//    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdateUser( Model model) {
        // get employee from the service
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)authentication).getUsername();
        Long id = userRepository.findByEmail(username).getId();
        User user = userService.getUserById(id);
        // set employee as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/showFormForUpdateAdmin/{id}")
    public String showFormForUpdateAdmin(@PathVariable ( value = "id") Long id, Model model) {
        // get employee from the service
        User user = userService.getUserById(id);
        if(user.getEmail().equals("root")){
            return "redirect:/users?admin";
        }
        else {
            // set employee as a model attribute to pre-populate the form
            model.addAttribute("user", user);
            return "update_users";
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable (value = "id") long id){
        //call delete user method
        User user = userService.getUserById(id);
        if(user.getEmail().equals("root")){
            return "redirect:/users?admin";
        }
        else {
            this.userService.deleteUserById(id);
            return "redirect:/users";
        }
    }
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 10 ;

        Page<User> page = userService.findPaginate(pageNo,pageSize, sortField, sortDir);
        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")? "desc": "asc");

        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/contact")
    public String showContactForm(Model model){
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)authentication).getUsername();
        Long id = userRepository.findByEmail(username).getId();
        User user = userService.getUserById(id);
        // set employee as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "contact";
    }


}
