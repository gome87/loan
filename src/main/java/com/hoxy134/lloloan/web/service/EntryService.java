package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.EntryDTO;

public interface EntryService {

    EntryDTO.Response create(Long applicationId, EntryDTO.Request request);

    EntryDTO.Response getFindId(Long applicationId);

    EntryDTO.UpdateResponse update(Long entryId, EntryDTO.Request request);

    void delete(Long entryId);

}
