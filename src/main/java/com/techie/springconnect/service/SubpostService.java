package com.techie.springconnect.service;


import com.techie.springconnect.dto.SubpostDto;
import com.techie.springconnect.model.Subpost;
import com.techie.springconnect.repository.SubPostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class SubpostService {

    private SubPostRepository subPostRepository;

    @Transactional
    public void save(SubpostDto subpostDto) {
        Subpost subpost = new Subpost();
        subpost.setId(subpostDto.getId());
        subpost.setName(subpostDto.getName());
        subpost.setDescription(subpostDto.getDescription());
        subpost.setCreatedDate(Instant.now());
        subPostRepository.save(subpost);
    }
}
