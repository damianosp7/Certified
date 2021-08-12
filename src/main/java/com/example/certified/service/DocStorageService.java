package com.example.certified.service;

import com.example.certified.model.Docs;
import com.example.certified.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocStorageService {
    @Autowired
    private DocRepository docRepository;

    public Docs saveFile(MultipartFile file){
        String docname =  file.getOriginalFilename();
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = " ";
        if (authentication instanceof UserDetails){
            username = ((UserDetails)authentication).getUsername();
        }
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try {
            Docs doc = new Docs(username,docname , file.getContentType(), timestamp.toString(), file.getBytes());
            return docRepository.save(doc);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Docs> getFile(Long fileId){
        return docRepository.findById(fileId);
    }

    public List<Docs> getFiles(){
        return docRepository.findAll();
    }

}
