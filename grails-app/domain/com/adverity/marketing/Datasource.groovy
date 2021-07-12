package com.adverity.marketing

class Datasource {

	static hasMany = [campaigns: Campaign]

	String name

    static constraints = {
	    name unique: true
    }
}
