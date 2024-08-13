package com.techie.springconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techie.springconnect.controller.PostController;
import com.techie.springconnect.dto.PostRequest;
import com.techie.springconnect.dto.PostResponse;
import com.techie.springconnect.security.JwtProvider;
import com.techie.springconnect.service.PostService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.isArray;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
public class PostControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private PostResponse postResponse;

    private PostRequest postRequest;

    private final ObjectMapper objectMapper = new ObjectMapper();

    //test for getAllPosts
    @Test
    @WithMockUser(username = "user")
    public void testGetAllPostsList() throws Exception{
        when(postService.getAllPostResponses()).thenReturn(Collections.singletonList(postResponse));
        mockMvc.perform(get("http://localhost:8080/api/posts/getAllPosts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }

    //test for create post
    @Test
    @WithMockUser(username = "TestUser4", roles = "USER")
    public void testCreatedPost() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setPostId(1L);
        postRequest.setSubpostName("subpostName");
        postRequest.setPostName("postName");
        postRequest.setUrl("http://example.com");
        postRequest.setDescription("This is a description");

        doNothing().when(postService).save(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/posts/createPost")
                        .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated());
    }


}
