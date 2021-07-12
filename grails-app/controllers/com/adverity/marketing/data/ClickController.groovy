package com.adverity.marketing.data

import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {
	description "Click API described in external OpenAPI document"
})
class ClickController extends DailyStatsController {

	@Override
	def index(Integer max) {
		super.index(max)
	}

	@Override
	def show(Long id) {
		super.show(id)
	}

}
