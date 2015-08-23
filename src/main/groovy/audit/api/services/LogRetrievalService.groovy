package audit.api.services

import static org.elasticsearch.index.query.FilterBuilders.*

import org.elasticsearch.client.Client
import org.elasticsearch.index.query.FilterBuilders
import org.elasticsearch.index.query.QueryBuilders

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

	def retrieveAllLogsForUser(userId,callback,errorCallback){
		def query = QueryBuilders.matchQuery("user", userId)
//		println query
		repository.scanAndScrollSearch(query,scanAndScrollLimit,callback,errorCallback)
	}
	def retrieveAllLogsWithinPeriod(fromDate,toDate,callback,errorCallback){
		def query= QueryBuilders.rangeQuery("date").from(fromDate).to(toDate)
		repository.scanAndScrollSearch(query,scanAndScrollLimit,callback,errorCallback)
	}
	def retrieveLogsWithinPeriodForUser(fromDate,toDate,userId,callback,errorCallback){
		def query = QueryBuilders.rangeQuery("date")
		.from(fromDate)
		.to(toDate)
		
		def filteredQuery =QueryBuilders.filteredQuery(query, FilterBuilders.boolFilter().must(FilterBuilders.termFilter("user", userId)))
		repository.scanAndScrollSearch(filteredQuery, scanAndScrollLimit,callback , errorCallback)
	}
	def retrieveAllLogsFromSource(source,callback,errorCallback){
		def query = QueryBuilders.matchQuery("source",source)
		repository.scanAndScrollSearch(query,scanAndScrollLimit,callback, errorCallback) 
	}
	def retrieveAllLogsWhereMessageContains(message,callback,errorCallback){
		def query = QueryBuilders.matchQuery("message",message)
		repository.scanAndScrollSearch(query,scanAndScrollLimit,callback, errorCallback)
	}
	
}
