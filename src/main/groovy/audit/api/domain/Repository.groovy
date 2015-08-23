package audit.api.domain

import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.Client
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.search.SearchHit
class Repository {

	Client client
	def _index
	def _type

	Repository(client,index,type){
		this.client =client
		this._index =index
		this._type =type
	}
	def scanAndScrollSearch(query,scrollSize,callback,errorCallback){
		
		SearchResponse response = client.prepareSearch(_index)
				.setSearchType(SearchType.SCAN)
				.setScroll(new TimeValue(60000))
				.setQuery(query)
				.setSize(scrollSize)
				.execute()
				.actionGet()

		def documents = []

		while(true) {
			
			for(SearchHit hit :response.getHits().getHits()) {
				documents << hit.sourceAsMap()
			}
			
			response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet()
			
			if(response.getHits().getHits().length==0){
				break
			}
			
		}
		
//		println documents
		 callback(documents)
	}
}