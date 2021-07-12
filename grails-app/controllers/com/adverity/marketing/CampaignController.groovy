package com.adverity.marketing

import org.springframework.http.HttpStatus
import swagger.grails4.openapi.ApiDoc


@ApiDoc(tag = {
	description "Campaign API described in external OpenAPI document"
})
class CampaignController {

    CampaignService campaignService

    static responseFormats = ['json', 'xml']

    def index(Integer max) {
	    try{
		    List<Datasource> campaigns = campaignService.list(params)
		    respond campaigns, model:[campaignCount: campaigns?.size() ?: 0]
	    } catch( Exception exception){
		    log.error("General error during index()", exception)
		    render status: HttpStatus.INTERNAL_SERVER_ERROR
		    return
	    }
    }

    def show(Long id) {
        respond campaignService.get(id)
    }

}
