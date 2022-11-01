package com.codestates.stackoverflow.member.controller;

import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberServiceImpl memberService;

}
