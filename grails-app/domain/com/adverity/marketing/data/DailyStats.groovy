package com.adverity.marketing.data

import com.adverity.marketing.Campaign
import com.adverity.marketing.Datasource

class DailyStats {

	static belongsTo = [campaign: Campaign]

	Date date
	int clicks = 0
	int impressions = 0

	static constraints = {
		campaign unique: "date"
		clicks min: 0
		impressions min: 0
	}

	Datasource getDatasource() {
		return campaign.datasource
	}

	def getCTR() {
		return (impressions > 0) ? (clicks / impressions) : 0
	}
}
