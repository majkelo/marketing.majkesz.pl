package com.adverity.marketing

import grails.gorm.services.Service

@Service(Campaign)
interface CampaignService {

    Campaign get(Serializable id)

    List<Campaign> list(Map args)

    Long count()

    Campaign delete(Serializable id)

    Campaign save(Campaign campaign)

}
