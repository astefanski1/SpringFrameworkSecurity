package com.astefanski;

import com.astefanski.dto.CustomerDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class CSRFDisabledTest {

    @Autowired
    private MockMvc mockMvc;

    List<CustomerDTO> customerDTOS = new ArrayList<>();

    @Test
    public void givenNotAuth_whenAddFoo_thenUnauthorized() throws Exception {
        mockMvc.perform(
                get("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(customerDTOS))
        ).andExpect(status().isUnauthorized());
    }

//    @Test
//    public void givenAuth_whenAddFoo_thenCreated() throws Exception {
//        mockMvc.perform(
//                post("/foos").contentType(MediaType.APPLICATION_JSON)
//                        .content(createFoo())
//                        .with(testUser())
//        ).andExpect(status().isCreated());
//    }
}
