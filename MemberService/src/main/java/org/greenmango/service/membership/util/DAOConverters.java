package org.greenmango.service.membership.util;

import org.apache.commons.lang3.StringUtils;
import org.greenmango.service.membership.dao.enitty.FileDO;
import org.greenmango.service.membership.dao.enitty.MemberDO;
import org.greenmango.service.membership.ui.dto.FileResponse;
import org.greenmango.service.membership.ui.dto.ImageRequest;
import org.greenmango.service.membership.ui.dto.MemberRequest;
import org.greenmango.service.membership.ui.dto.MemberResponse;
import org.greenmango.service.membership.ui.dto.wrapper.HostWrapperResponse;
import org.greenmango.service.membership.ui.dto.wrapper.UserWrapperRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

public final class DAOConverters {
   static  Logger logger = LoggerFactory.getLogger(DAOConverters.class);

    public static MemberDO convertMemberDo(UserWrapperRequest<MemberRequest> request){
        MemberDO memberDO = new MemberDO(request.getRequest().getFirstName(),
                request.getRequest().getLastName(),
                fromLocalDate(request.getRequest().getDateOfBirth()),
                request.getRequest().getPostalCode(),
                request.getUser(),  new Date());
        if (request.getRequest().getImage()!= null &&
                StringUtils.isNoneEmpty(request.getRequest().getImage().getFile())){
            FileDO file=  convertFile(request.getUser()).apply(request.getRequest());
            file.setMemberDO(memberDO);
            memberDO.setFileDO(file);
        }

        return memberDO;
    }

    public static MemberDO convertUpdateMemberDo(UserWrapperRequest<MemberRequest> request, MemberDO memberDO){
        memberDO.setFirstName(checkOldAndNewValues(request.getRequest().getFirstName() , memberDO.getFirstName()));
        memberDO.setLastName(checkOldAndNewValues(request.getRequest().getLastName() ,memberDO.getLastName()));
        memberDO.setDataOfBirth(checkOldAndNewValues(fromLocalDate(request.getRequest().getDateOfBirth()) ,  memberDO.getDataOfBirth()));
        memberDO.setPostalCode(checkOldAndNewValues(request.getRequest().getPostalCode() , memberDO.getPostalCode()));
        memberDO.setModifiedBy(request.getUser());
        FileDO file = memberDO.getFileDO();
        if (file == null && request.getRequest().getImage() != null) {
            file = DAOConverters.convertFile(request.getUser()).apply(request.getRequest());
        } else if (request.getRequest().getImage() != null && file != null) {
            file.setFileName(checkOldAndNewValues(request.getRequest().getImage().getFileName() ,  file.getFileName()));
            file.setFileType(checkOldAndNewValues(request.getRequest().getImage().getFileType(),  file.getFileType()));
            file.setFile(request.getRequest().getImage().getFile() != null ?
                    DAOConverters.convertByte(request.getRequest().getImage().getFile()) : file.getFile());
        }
        memberDO.setFileDO(file);
        return memberDO;
    }
    private static <T> T checkOldAndNewValues(T currentValue, T oldValue){
        if (currentValue== null || currentValue.equals(oldValue)){
            return oldValue;
        }
        return currentValue;
    }

    public static MemberResponse convertMemberDoInverse(HostWrapperResponse<MemberDO> response){
            return new MemberResponse(response.getResponse().getLastName(),
                    response.getResponse().getLastName(),
                    fromDate(response.getResponse().getDataOfBirth()),
                   response.getHost() + "download/" + response.getResponse().getMemberId(),
                    response.getResponse().getPostalCode(),
                    response.getResponse().getMemberId()
                    );
    }

    public static  Function<MemberRequest, FileDO> convertFile(String user){
        return member -> new FileDO(convertByte(member.getImage().getFile()),
                member.getImage().getFileName(),
                member.getImage().getFileType(),
                user, new Date());
    }

    public static FileResponse convertToFileResponse(MemberDO memberDO){
        Resource resource = new ByteArrayResource(memberDO.getFileDO().getFile(), memberDO.getFileDO().getFileName());
        return new FileResponse(resource, memberDO.getFileDO().getFileName(), memberDO.getFileDO().getFileType());
    }

    public static Function<MultipartFile, ImageRequest> convertImage(){
         return  file -> new ImageRequest(convertString(getByteFromFile(file)),
                 file.getContentType(),  file.getOriginalFilename(), null );

    }

    public static byte [] getByteFromFile(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            logger.error("bytes error" , e);
        }
        return null;
    }

    public static byte[] convertByte(String image ){
        return Base64.getDecoder().decode(image.getBytes());

    }

    public static String convertString(byte [] imageContent){
     return Base64
                .getEncoder()
                .encodeToString(imageContent);
    }

    public static Date fromLocalDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static LocalDate fromDate(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Instant.ofEpochMilli(date.getTime()).atZone(defaultZoneId).toLocalDate();
    }
}
