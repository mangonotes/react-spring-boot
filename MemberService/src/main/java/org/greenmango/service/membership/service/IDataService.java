package org.greenmango.service.membership.service;

import org.greenmango.service.membership.ui.dto.BaseRequest;
import org.greenmango.service.membership.ui.dto.BaseResponse;
import org.greenmango.service.membership.ui.dto.MemberResponse;

import java.util.List;

public interface IDataService<T,U,V> {
    BaseResponse<V> save(T uiModel);
    BaseResponse<List<V>> getAll();
    BaseResponse<V> get(U id);
    BaseResponse<V> update(T uiModel, U id);
    BaseResponse<String> delete(U id);
}
