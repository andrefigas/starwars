package dev.figas.sw.model.google

class GoogleResponse(
    var kind: String,
    var context: Context,
    var queries: Queries,
    var searchInformation: SearchInformation,
    var items: Array<Items>,
    var url: Url
)
			
			