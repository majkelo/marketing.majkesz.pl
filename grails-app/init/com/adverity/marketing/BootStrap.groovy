package com.adverity.marketing

import com.adverity.marketing.data.DailyStats
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

	    if(Environment.getCurrent() == Environment.PRODUCTION){
		    File file = File.createTempFile("adverity_data",".csv")
		    file.deleteOnExit()
		    file << new URL("https://majkesz.pl/docs/2021/07_adverity/data.csv").bytes
		    file.toCsvReader(['charset':'UTF-8', "skipLines": '1']).eachLine { tokens ->
			    String datasourceName = tokens[0]
			    String campaignName = tokens[1]
			    Date date = new Date(tokens[2])
			    int clicks = tokens[3] as int
			    int impressions = tokens[4] as int

			    Datasource datasource = Datasource.findByName(datasourceName)
			    if(!datasource){
				    datasource = new Datasource(name: datasourceName).save()
			    }
			    Campaign campaign = Campaign.findByDatasourceAndName(datasource, campaignName)
			    if(!campaign){
				    campaign = new Campaign(name: campaignName, datasource: datasource).save()
			    }
			    DailyStats dailyStats = DailyStats.findByCampaignAndDate(campaign, date)
			    if(!dailyStats){
				    new DailyStats(campaign: campaign, clicks: clicks, impressions: impressions, date: date).save()
			    } else {
				    throw new Exception("Duplicated row for this campaign and date!")
			    }
		    }

	    }

    }
    def destroy = {
    }
}
