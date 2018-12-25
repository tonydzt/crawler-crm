package com.kasuo.crawler.dao.mybatis.impl;

import com.kasuo.crawler.dao.mybatis.OrgDao;
import com.kasuo.crawler.domain.Org;
import com.kasuo.crawler.domain.vo.OrgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
@Repository
public class OrgDaoImpl {

    @Autowired
    private OrgDao orgDao;

    @Transactional(rollbackFor = Exception.class)
    public OrgVO insertOrUpdate(com.kasuo.crawler.domain.org.Org orgSave) {
        Org org = orgDao.findByNameForUpdate(orgSave.getOrgName());
        if (org != null) {
            return new OrgVO(org, false);
        }

        org = new Org();
        org.setName(orgSave.getOrgName());
        org.setLegalPerson(orgSave.getLegalPerson());
        org.setRegisteredCapital(orgSave.getRegisteredCapital());
        orgDao.insert(org);

        return new OrgVO(org, true);
    }

    public void updateHasContact(Long id) {
        orgDao.updateHasContact(id);
    }
}
