package com.adverity.marketing

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired

@Integration
@Rollback
class DatasourceServiceSpec extends CommonIntegrationSpec {

	DatasourceService datasourceService
	@Autowired
	Datastore datastore

	private Long setupData() {
		createData().datasource.id
	}

	void "test get"() {
		setupData()

		expect:
			datasourceService.get(1) != null
	}

	void "test list"() {
		setupData()

		when:
			List<Datasource> datasourceList = datasourceService.list(max: 2, offset: 2)

		then:
			datasourceList.size() == 2
	}

	void "test count and delete"() {
		given:
			setupData()
			Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceNameX").save()
			Long datasourceId = datasource.id
			Integer counter = datasourceService.count()

		when:
			datasourceService.delete(datasourceId)
			datastore.currentSession.flush()

		then:
			datasourceService.count() == counter - 1
	}

	void "test save"() {
		given:
			Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceName")

		when:
			datasourceService.save(datasource)

		then:
			datasource.id != null
	}
}
