package com.kasuo.crawler.service.crawler;

import com.kasuo.crawler.dao.TrademarkDao;
import com.kasuo.crawler.dao.mybatis.EmailDao;
import com.kasuo.crawler.dao.mybatis.MobileDao;
import com.kasuo.crawler.dao.mybatis.TelDao;
import com.kasuo.crawler.dao.mybatis.impl.OrgDaoImpl;
import com.kasuo.crawler.domain.Email;
import com.kasuo.crawler.domain.Mobile;
import com.kasuo.crawler.domain.Org;
import com.kasuo.crawler.domain.Tel;
import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.vo.OrgVO;
import com.kasuo.crawler.service.AssignmentService;
import com.kasuo.crawler.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
@Service
public class OrgService {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private OrgDaoImpl orgDao;

    @Autowired
    private MobileDao mobileDao;

    @Autowired
    private TelDao telDao;

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private TrademarkDao trademarkDao;

    public void save(com.kasuo.crawler.domain.org.Org orgSave, Long trademarkId) {

        OrgVO orgVO = orgDao.insertOrUpdate(orgSave);
        Org org = orgVO.getOrg();

        List<Contact> contactList = orgSave.getContacts();
        List<Mobile> mobileList = new ArrayList<>();
        List<Tel> telList = new ArrayList<>();
        List<Email> emailList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(contactList)) {
            for (Contact contact : contactList) {
                if (contact.getEmail() != null) {
                    Email email = new Email();
                    email.setOrgId(org.getId());
                    email.setEmail(contact.getEmail());
                    emailList.add(email);
                }

                if (contact.getTel() != null) {
                    if (RegexUtil.isMobile(contact.getTel())) {
                        Mobile mobile = new Mobile();
                        mobile.setOrgId(org.getId());
                        mobile.setMobileNo(contact.getTel());
                        mobileList.add(mobile);
                    } else {
                        Tel tel = new Tel();
                        tel.setOrgId(org.getId());
                        tel.setTelNo(contact.getTel());
                        telList.add(tel);
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(mobileList)) {
            mobileDao.batchInsert(mobileList);
        }

        if (!CollectionUtils.isEmpty(telList)) {
            telDao.batchInsert(telList);
        }

        if (!CollectionUtils.isEmpty(emailList)) {
            emailDao.batchInsert(emailList);
        }

        if (mobileList.size() > 0 || telList.size() > 0) {
            orgDao.updateHasContact(org.getId());
            org.setHasContact(true);
        }

        trademarkDao.updateCrawStatus(trademarkId, org.getId(), orgVO.getNew());

        assignmentService.assignOrg(org, trademarkId);
    }
}
