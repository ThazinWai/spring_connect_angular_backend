package com.techie.springconnect.controller;

import com.techie.springconnect.dto.SubpostDto;
import com.techie.springconnect.service.SubpostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subpost")
@AllArgsConstructor
@Slf4j
public class SubpostController {

    private final SubpostService subpostService;

    @PostMapping("/createSubpost")
    public ResponseEntity<SubpostDto> createSubpost(@RequestBody SubpostDto subpostDto) {
        subpostService.save(subpostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
