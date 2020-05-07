package org.greenmango.service.membership.service;

import org.greenmango.service.membership.dao.enitty.MemberDO;
import org.greenmango.service.membership.dao.repository.MemberRepository;
import org.greenmango.service.membership.ui.dto.BaseResponse;
import org.greenmango.service.membership.ui.dto.MemberRequest;
import org.greenmango.service.membership.ui.dto.MemberResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private IDataService<MemberRequest, Integer, MemberResponse> memberService;
    @MockBean
    private MemberRepository memberRepository;

    @Before
    public void setUp() throws Exception {
        MemberDO memberDO = new MemberDO("first", "lastbame", new Date(), "test", "123", new Date());
        memberDO.setMemberId(1);
        List<MemberDO> memberDOList = Arrays.asList(memberDO);
        Iterable<MemberDO> iterableMemberDOS = memberDOList;
        when(memberRepository.findAll()).thenReturn(iterableMemberDOS);
        when(memberRepository.findById(1)).thenReturn(Optional.of(memberDO));
        doNothing().when(memberRepository).deleteById(1);


    }

    @Test
    public void save() {
        MemberDO memberDO = new MemberDO("first", "lastbame", new Date(), "test", "123", new Date());
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        when(memberRepository.save(any(MemberDO.class))).thenReturn(memberDO);
        BaseResponse<MemberResponse> response = memberService.save(memberRequest);
        int expected = 200;
        assertEquals(response.getStatus().getCode(), expected);
    }

    @Test
    public void getAll_OK() {
        BaseResponse<List<MemberResponse>> response = memberService.getAll();
        int expected = 1;
        assertEquals(response.getResponse().size(), expected);
    }

    @Test
    public void get_OK() {
        BaseResponse<MemberResponse> response = memberService.get(1);
        Integer expected = 1;
        assertEquals(response.getResponse().getId(), expected);
    }

    @Test
    public void update() {
        MemberDO memberDO = new MemberDO("first", "lastbame", new Date(), "test", "123", new Date());
        memberDO.setMemberId(1);
        MemberRequest memberRequest = new MemberRequest("first", "lastbame", LocalDate.now(), null, "123");
        when(memberRepository.findById(1)).thenReturn(Optional.of(memberDO));
        BaseResponse<MemberResponse> response = memberService.update(memberRequest, 1);
        int expected = 200;
        assertEquals(response.getStatus().getCode(), expected);
    }

    @Test
    public void delete() {
       BaseResponse<String> response= memberService.delete(1);
        int expected = 200;
        assertEquals(response.getStatus().getCode(), expected);
    }
}