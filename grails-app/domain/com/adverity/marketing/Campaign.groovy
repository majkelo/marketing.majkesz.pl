package com.adverity.marketing

import com.adverity.marketing.data.DailyStats

class Campaign {

	static belongsTo = [datasource: Datasource]
	static hasMany = [dailyStats: DailyStats]

	String name

    static constraints = {
	    name unique: 'datasource'
    }
}
