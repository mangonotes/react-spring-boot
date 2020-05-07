package org.greenmango.service.membership.controller;

import org.greenmango.service.membership.service.IDataService;
import org.greenmango.service.membership.ui.dto.BaseRequest;
import org.greenmango.service.membership.ui.dto.BaseResponse;
import org.greenmango.service.membership.ui.dto.MemberRequest;
import org.greenmango.service.membership.ui.dto.MemberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Create , delete , update and find Members
 */
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final  IDataService<MemberRequest, Integer, MemberResponse> memberService;

    @Autowired
    public MemberController(IDataService<MemberRequest, Integer, MemberResponse> memberService){
        this.memberService=memberService;
     }


    /** Create Member
     * @param request MemberRequest with image as Base64 String
     * @return MemberResponse
     */
    @PostMapping(value="/create")
    public BaseResponse<MemberResponse> createMember(@RequestBody BaseRequest<MemberRequest> request){
       return  memberService.save(request.getRequest());
    }

    /** find Member by id
     * @param id
     * @return MemberResponse
     */
    @GetMapping(value="/find/{id}")
    public BaseResponse<MemberResponse> getMember(@PathVariable Integer id){
         return  memberService.get(id);
    }

    @GetMapping(value="/find/all")
    public BaseResponse<List<MemberResponse>> getAllMembers(){
        return  memberService.getAll();
    }

    /** Delete member by id
     * @param id
     * @return String
     */
    @DeleteMapping(value ="delete/{id}")
    public BaseResponse<String> deleteById(@PathVariable Integer id){
        return memberService.delete(id);
    }

    /** Update member  information , delta updates
     * @param id
     * @param request
     * @return
     */
    @PutMapping(value="/update/{id}")
    public BaseResponse<MemberResponse> updateMember(@PathVariable Integer id,
                                                     @RequestBody BaseRequest<MemberRequest> request){
       return  memberService.update(request.getRequest(), id);
    }
}
