package com.qupp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthTests {
	@Autowired
	private MockMvc mvc;

//	@Test
//	@DisplayName("POST /member/login 으로 올바른 email과 password 데이터를 넘기면 JWT키를 발급해준다.")
//	void t1() throws Exception {
//		// When
//		ResultActions resultActions = mvc
//				.perform(
//						post("/login")
//								.content("""
//                                        {
//                                            "email": "ksu987@gmail.com",
//                                            "password": "1234"
//                                        }
//                                        """.stripIndent())
//								.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
//				)
//				.andDo(print());
//		// Then
//		resultActions
//				.andExpect(status().is2xxSuccessful());
//	}

//	@Test
//	@DisplayName("로그인 후 얻은 JWT 토큰으로 현재 로그인 한 회원의 정보를 얻을 수 있다.")
//	void t2() throws Exception {
//		// When
//		ResultActions resultActions = mvc
//				.perform(
//						post("/login")
//								.content("""
//                                        {
//                                            "email": "ksu98712@gmail.com",
//                                            "password": "1234"
//                                        }
//                                        """.stripIndent())
//								.contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
//				)
//				.andDo(print());
//
//		// Then
//		resultActions
//				.andExpect(status().is2xxSuccessful());
//
//		MvcResult mvcResult = resultActions.andReturn();
//
//		MockHttpServletResponse response = mvcResult.getResponse();
//
//
//		String responseContentAsString = response.getContentAsString();
//
//		String accessToken = responseContentAsString.substring(6);
//
//
//
//		System.out.println("response 값: " + accessToken);
//
//		resultActions = mvc
//				.perform(
//						get("/user/43")
//								.param("jwtToken" )
//				)
//				.andDo(print());
//
//		// Then
//		resultActions
//				.andExpect(status().is2xxSuccessful());
//
//	}

}
