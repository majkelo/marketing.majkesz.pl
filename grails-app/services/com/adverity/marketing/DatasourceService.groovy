package com.adverity.marketing

import grails.gorm.services.Service

@Service(Datasource)
interface DatasourceService {

    Datasource get(Serializable id)

    List<Datasource> list(Map args)

    Long count()

    Datasource delete(Serializable id)

    Datasource save(Datasource datasource)

}
