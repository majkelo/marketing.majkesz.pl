package com.adverity.marketing

import org.springframework.http.HttpStatus
import swagger.grails4.openapi.ApiDoc


@ApiDoc(tag = {
	description "Datasources API described in external OpenAPI document"
})
class DatasourceController{

    DatasourceService datasourceService

    static responseFormats = ['json', 'xml']

    def index(Integer max) {
	    try{
		    List<Datasource> datasources = datasourceService.list(params)
		    respond datasources, model:[datasourceCount: datasources?.size() ?: 0]
	    } catch( Exception exception){
		    log.error("General error during index()", exception)
		    render status: HttpStatus.INTERNAL_SERVER_ERROR
		    return
	    }
    }

    def show(Long id) {
        respond datasourceService.get(id)
    }

}
