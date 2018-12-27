package com.nineswords.oauth.config.security.oauth2;

import com.nineswords.oauth.dal.OauthClientDetailRepository;
import com.nineswords.oauth.entity.OauthClientDetail;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Create by Jarvis.wang on Jarvis's device
 *
 * @author Jarvis.wang  Jarvis
 * @date 2018-12-27 11:29
 */
@Service
public class OAuthClientDetailService implements ClientDetailsService {
    @Resource
    private OauthClientDetailRepository oauthClientDetailRepository;

    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetail oauthClientDetail = oauthClientDetailRepository.findByClientId(clientId);

        if (oauthClientDetail == null) {
            throw new ClientRegistrationException("Not Found CliendId: " + clientId);
        }

        return new BaseClientDetails(oauthClientDetail.getClientId(),
                oauthClientDetail.getResourceIds(),
                oauthClientDetail.getScope(),
                oauthClientDetail.getAuthorizedGrantTypes(),
                oauthClientDetail.getAuthorities());
    }

}
