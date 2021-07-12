package com.adverity.marketing.data

import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {
	description "Impression API described in external OpenAPI document"
})
class ImpressionController extends DailyStatsController{

	@Override
	def index(Integer max) {
		super.index(max)
	}

	@Override
	def show(Long id) {
		super.show(id)
	}

}
