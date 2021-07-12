package com.adverity.marketing.data

import swagger.grails4.openapi.ApiDoc

@ApiDoc(tag = {
	description "CTR (ClickThroughRate) API described in external OpenAPI document"
})
class CtrController extends DailyStatsController {

	@Override
	def index(Integer max) {
		super.index(max)
	}

	@Override
	def show(Long id) {
		super.show(id)
	}

}
