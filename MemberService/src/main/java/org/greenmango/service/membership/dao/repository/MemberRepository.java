package org.greenmango.service.membership.dao.repository;

import org.greenmango.service.membership.dao.enitty.MemberDO;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberDO, Integer> {
}
