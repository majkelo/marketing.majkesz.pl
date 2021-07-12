package com.adverity.marketing

trait DateDimensionFilter {

	def filterByStartDate(filteredObjects, Date date) {
		return filteredObjects.findAll {
			(it.date >= date)
		}
	}

	def filterByEndDate(filteredObjects, Date date) {
		return filteredObjects.findAll {
			(it.date <= date)
		}
	}

	def filterByDateBetween(filteredObjects, Date startDate, Date endDate) {
		return filteredObjects.findAll { filteredObject ->
			(filteredObject.date >= startDate) && (filteredObject.date <= endDate)
		}
	}
}