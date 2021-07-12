package com.adverity.marketing

class UrlMappings {

    static mappings = {

        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}
