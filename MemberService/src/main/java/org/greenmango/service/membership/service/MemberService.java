package org.greenmango.service.membership.service;

import org.greenmango.service.membership.constants.ErrorCode;
import org.greenmango.service.membership.dao.enitty.MemberDO;
import org.greenmango.service.membership.dao.repository.MemberRepository;
import org.greenmango.service.membership.exception.DataErrorException;
import org.greenmango.service.membership.ui.dto.*;
import org.greenmango.service.membership.ui.dto.wrapper.HostWrapperResponse;
import org.greenmango.service.membership.ui.dto.wrapper.UserWrapperRequest;
import org.greenmango.service.membership.util.DAOConverters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Member service for all CRUD operations
 */
@Service
@Transactional
public class MemberService implements IDataService<MemberRequest, Integer, MemberResponse> {
    private static Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Value("${image.download.url.host}")
    private String hostName;
    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Save the member into database
     *
     * @param uiModel
     * @return MemberResponse
     */
    @Override
    public BaseResponse<MemberResponse> save(MemberRequest uiModel) {
        Optional<UserWrapperRequest<MemberRequest>> userWrapperRequest = Optional.of(new UserWrapperRequest<>("Admin", uiModel));
        Optional<MemberDO> memberDO = userWrapperRequest.map(DAOConverters::convertMemberDo);
        memberRepository.save(memberDO.get());
        Optional<HostWrapperResponse<MemberDO>> hostWrapperResponse = Optional.of(new HostWrapperResponse(hostName,memberDO.get()));
        return hostWrapperResponse.map(response ->  DAOConverters.convertMemberDoInverse(response))
                .map(response -> new BaseResponse<> (response,new Status())).get();
    }

    /**
     * @return List<MemberResponse> get All Members
     */
    @Override
    public BaseResponse<List<MemberResponse>> getAll() {
        List<MemberResponse> members = StreamSupport.stream(memberRepository.findAll().spliterator(), false)
                .map(memberDO -> {
            return DAOConverters.convertMemberDoInverse( new HostWrapperResponse(hostName,memberDO));
        }).collect(Collectors.toList());
        return new BaseResponse(members, new Status());
    }

    /**
     * Get Member by id
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse<MemberResponse> get(Integer id) {
        return memberRepository.findById(id)
                .filter(Objects::nonNull)
                .map(memberDo ->
                DAOConverters.convertMemberDoInverse(new HostWrapperResponse(hostName, memberDo)))
                .map(response -> new BaseResponse(response, new Status())).
                        orElseThrow(() -> new DataErrorException(ErrorCode.ELEMENT_NOT_FIND));
    }

    /**
     * Update member information , only non null values are updated into database
     *
     * @param uiModel
     * @param id
     * @return MemberResponse
     */
    @Override
    public BaseResponse<MemberResponse> update(MemberRequest uiModel, Integer id) {
        MemberDO updated = memberRepository.findById(id)
                .filter(Objects::nonNull)
                .map(memberDO -> DAOConverters.convertUpdateMemberDo
                        (new UserWrapperRequest<MemberRequest>("Admin",
                        uiModel), memberDO)
                ).orElseThrow(() -> new DataErrorException(ErrorCode.UPDATE_ERROR));
        memberRepository.save(updated);
        return new BaseResponse(DAOConverters.convertMemberDoInverse
                (new HostWrapperResponse(hostName, updated)), new Status());
    }


    /**
     * Delete member by id
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse<String> delete(Integer id) {
            memberRepository.findById(id).orElseThrow( () ->new DataErrorException(ErrorCode.ELEMENT_NOT_FIND));
            memberRepository.deleteById(id);
            return new BaseResponse(ErrorCode.SUCCESS_DELETE.getErrorDescription(), new Status());
    }
}
