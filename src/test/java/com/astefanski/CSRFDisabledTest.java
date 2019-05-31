package com.astefanski;

import com.astefanski.data.PopulateDatabase;
import com.astefanski.dto.ChangeNameDTO;
import com.astefanski.dto.CustomerDTO;
import com.astefanski.mapper.CustomerMapper;
import com.astefanski.model.AccountType;
import com.astefanski.model.Role;
import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import com.astefanski.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CSRFDisabledTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PopulateDatabase populateDatabase;

    @MockBean
    private CustomerMapper customerMapper;

    private User apiUser;
    private Role role;
    private ObjectMapper objectMapper;
    private ChangeNameDTO changeNameDTO;
    private CustomerDTO customerDTO;
    private static final String CUSTOMER = "CUSTOMER";

    @Autowired
    private RequestPostProcessor testUser() {
        return user("user").password("user").roles("CUSTOMER");
    }

    @Before
    public void setUp() throws Exception {

        apiUser = User.builder()
                .name("Test")
                .username("test")
                .password("test")
                .email("test@wp.pl")
                .build();

        List<User> users = new ArrayList<>();
        users.add(apiUser);

        role = Role.builder()
                .displayName("CUSTOMER")
                .name("CUSTOMER")
                .users(users)
                .build();

        apiUser.setRole(role);

        changeNameDTO = new ChangeNameDTO();

        customerDTO = CustomerDTO.builder()
                .name("Test")
                .password("test")
                .email("test@wp.pl")
                .street("test")
                .postcode(23323)
                .city("test")
                .accountType(AccountType.STANDARD)
                .build();

        objectMapper = new ObjectMapper();
    }


    @Test
    @WithAnonymousUser
    public void givenNoCsrf_whenChangeName_thenUnauthorized() throws Exception {
        String json = objectMapper.writeValueAsString(changeNameDTO);
        changeNameDTO.setName("TestChange");

        this.mockMvc.perform(put("/customer/editName")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {CUSTOMER})
    public void givenNoCsrf_whenChangeName_thenUpdated() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(changeNameDTO);
        changeNameDTO.setName("TestChange");

        //when
        when(customerService.changeName(anyString(), anyString())).thenReturn(apiUser);
        when(customerRepository.findByNameOrEmail(anyString(), anyString())).thenReturn(Optional.of(apiUser));
        when(customerMapper.map((Mockito.any(User.class)))).thenReturn(customerDTO);

        this.mockMvc.perform(put("/customer/editName")
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .with(testUser()))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithAnonymousUser
    public void givenNoCsrf_whenChangeName_thenForbidden() throws Exception {
        String json = objectMapper.writeValueAsString(changeNameDTO);
        changeNameDTO.setName("TestChange");

        this.mockMvc.perform(put("/customer/editName")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {CUSTOMER})
    public void givenCsrf_whenChangeName_thenUpdated() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(changeNameDTO);
        changeNameDTO.setName("TestChange");

        //when
        when(customerService.changeName(anyString(), anyString())).thenReturn(apiUser);
        when(customerRepository.findByNameOrEmail(anyString(), anyString())).thenReturn(Optional.of(apiUser));
        when(customerMapper.map((Mockito.any(User.class)))).thenReturn(customerDTO);

        this.mockMvc.perform(put("/customer/editName")
                .with(csrf())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .with(testUser()))
                .andExpect(status().isAccepted());
    }
}
