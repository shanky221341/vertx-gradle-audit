package audit.api.services

import org.elasticsearch.client.Client
import audit.api.domain.Repository

class LogRetrievalService {

	Client client
	def _index
	def _type
	Repository repository
	def scanAndScrollLimit = 100

	LogRetrievalService(Client esClient,index,type) {
		this.client =esClient
		this._index = index
		this._type = type
		this.repository = new Repository(client, _index,_type)
	}
}
