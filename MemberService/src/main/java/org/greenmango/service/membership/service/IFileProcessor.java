package org.greenmango.service.membership.service;

import org.greenmango.service.membership.ui.dto.FileResponse;
import org.springframework.core.io.Resource;

public interface IFileProcessor {
    FileResponse downloadFile(Integer id);

}
