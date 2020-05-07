package org.greenmango.service.membership.service;

import org.greenmango.service.membership.constants.ErrorCode;
import org.greenmango.service.membership.dao.repository.MemberRepository;
import org.greenmango.service.membership.exception.DataErrorException;
import org.greenmango.service.membership.ui.dto.FileResponse;
import org.greenmango.service.membership.util.DAOConverters;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * File processor to download the file
 */
@Service
public class FileProcessor implements IFileProcessor {
    private final MemberRepository memberRepository;

    public FileProcessor(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** Download file from database by id
     * @param id
     * @return FileResponse
     */
    @Override
    public FileResponse downloadFile(Integer id) {
        return memberRepository.findById(id)
                .filter(Objects::nonNull)
                .filter(memberDO -> memberDO.getFileDO() != null)
                .map(DAOConverters::convertToFileResponse)
                .orElseThrow(() -> new DataErrorException(ErrorCode.ELEMENT_NOT_FIND));

    }
}
