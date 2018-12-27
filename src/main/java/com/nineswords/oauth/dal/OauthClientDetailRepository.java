package com.nineswords.oauth.dal;


import com.nineswords.oauth.entity.OauthClientDetail;


/**
 * Create by Jarvis.wang on Jarvis's device
 *
 * @author Jarvis.wang  Jarvis
 * @date 2018-12-27 11:29
 */
public interface OauthClientDetailRepository extends BaseRepo<OauthClientDetail,String>{

    /**
     * 获取对象
     * @param clientId
     * @return
     */
    OauthClientDetail findByClientId(String clientId);
}
