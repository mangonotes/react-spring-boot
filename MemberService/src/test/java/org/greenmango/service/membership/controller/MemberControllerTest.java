package org.greenmango.service.membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.vm.ci.meta.Local;
import org.greenmango.service.membership.constants.ErrorCode;
import org.greenmango.service.membership.exception.DataErrorException;
import org.greenmango.service.membership.service.IDataService;
import org.greenmango.service.membership.ui.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate

public class MemberControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IDataService<MemberRequest, Integer, MemberResponse> memberService;

    @Before
    public void init() {
        MemberResponse memberResponse = new MemberResponse("first", "lastbame", LocalDate.now(),"test", "123", 1);
        List<MemberResponse> memberResponseList = new ArrayList<MemberResponse>();
        memberResponseList.add(memberResponse);
        when(memberService.getAll()).thenReturn(new BaseResponse(memberResponseList, new Status()));
        when(memberService.get(1)).thenReturn(new BaseResponse(memberResponse, new Status()));
        when(memberService.save(any(MemberRequest.class))).thenReturn(new BaseResponse(memberResponse, new Status()));
        when(memberService.delete(1)).thenReturn(new BaseResponse<>("success", new Status()));
        when(memberService.delete(2)).thenThrow(new DataErrorException(ErrorCode.ELEMENT_NOT_FIND));

    }

    @Test
    public void find_getAllMembers_OK() throws Exception {
        mvc.perform(get("/api/v1/members//find/all")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status.code", is(200)))
                .andExpect(jsonPath("$.response", hasSize(1)));
    }
    @Test
    public void find_getMemberById_OK() throws Exception {

        mvc.perform(get("/api/v1/members//find/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status.code", is(200)))
                .andExpect(jsonPath("$.response.id", is(1)));
    }
    @Test
    public void create_Member_OK() throws Exception {
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        mvc.perform(post("/api/v1/members/create")
                .content(mapper.writeValueAsString(new BaseRequest(memberRequest)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status.code", is(200)))
                .andExpect(jsonPath("$.response.id", is(1)));
    }
    @Test
    public void create_Member_Error_Format() throws Exception {
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        mvc.perform(post("/api/v1/members/create")
                .content(mapper.writeValueAsString(new BaseRequest("test")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status.code", is(400)))
                ;
    }
    @Test
    public void delete_Member_OK() throws Exception {

        mvc.perform(delete("/api/v1/members/delete/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status.code", is(200)))
                .andExpect(jsonPath("$.response", is("success")));
    }

    @Test
    public void delete_Member_NO_OK() throws Exception {
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        mvc.perform(delete("/api/v1/members/delete/2")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.status.code", is(400)))
                ;
    }

    @Test
    public void update_Member_OK() throws Exception {
        MemberResponse memberResponse = new MemberResponse("first", "lastbame", LocalDate.now(), "test", "123", 1);
        when(memberService.update(any(MemberRequest.class), eq(1))).thenReturn(new BaseResponse(memberResponse, new Status()));
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        mvc.perform(put("/api/v1/members/update/1")
                .content(mapper.writeValueAsString(new BaseRequest(memberRequest)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status.code", is(200)))
                .andExpect(jsonPath("$.response.id", is(1)));
    }



}
