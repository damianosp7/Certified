package com.example.certified.service;

import com.example.certified.model.Docs;
import com.example.certified.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocStorageService {
    @Autowired
    private DocRepository docRepository;

    public Docs saveFile(MultipartFile file){
        String docname =  file.getOriginalFilename();
        try {
            Docs doc = new Docs(null,docname , file.getContentType(), null, file.getBytes());
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
