package com.adverity.marketing

trait RegularDimensionFilter {

	def filterByCampaign(filteredObjects, Long campaignId){
		return filteredObjects.findAll{
			(it.campaign.id == campaignId)
		}
	}

	def filterByDatasource(filteredObjects, Long datasourceId){
		return filteredObjects.findAll{
			(it.getDatasource().id == datasourceId)
		}
	}
}