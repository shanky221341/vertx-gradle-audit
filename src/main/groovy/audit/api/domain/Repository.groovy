package audit.api.domain

import org.elasticsearch.client.Client

class Repository {

	Client client
	def _index
	def _type
	
	Repository(client,index,type){
		this.client =client
		this._index =index
		this._type =type
	}
}
