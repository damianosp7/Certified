package com.example.certified.web;

import com.example.certified.model.Docs;
import com.example.certified.repository.DocRepository;
import com.example.certified.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DocController {
    @Autowired
    private DocStorageService docStorageService;

    @Autowired
    private DocRepository docRepository;

    @GetMapping("/")
    public String get(Model model){
        List<Docs> docs = docStorageService.getFiles();
        model.addAttribute("docs", docs);
        return "doc";
    }

    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            docStorageService.saveFile(file);

        }
        return "redirect:/";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
        Docs doc = docStorageService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable Long fileId){
        this.docRepository.deleteById(fileId);
        return "redirect:/operation/user";
    }

    @GetMapping("/operation/user")
    public String ViewAccountPageUser(Model model){
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)authentication).getUsername();
        model.addAttribute("docs", docRepository.findByUser(username));
        return "history";
    }

    //display  all user's operation history
    @GetMapping("/operation/users")
    public String ViewAccountPageUsers(Model model){
        model.addAttribute("docs", docRepository.findAll());
        return "users_history";
    }

    @GetMapping("/operation/clear")
    public String ClearUserHistory(){
        //call clear history method
        this.docStorageService.deleteUserDocuments();
        return "history";
    }

}
