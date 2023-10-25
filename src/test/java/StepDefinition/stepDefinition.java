package StepDefinition;

import org.jsoup.Connection.Request;

import Requsts.Requests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class stepDefinition {
	Requests req=new Requests();
	@Given("Genrate Cookie")
	public void genrate_cookie() {
	
	    req.Generate_cookie();
	}
	@When("Genrate Token")
	public void genrate_token() {
	    req.Generate_Token();
	
	}
	@When("Get Candidtes")
	public void gatCandidtes() {
	    req.getCandidtes();
	
	}
	@When("Delete a recent record in the list")
	public void delete_record() {
		  req.Delete();
	}
	@When("Add a candidate")
	public void Add_candidate() {
		  req.Add();
	}

	@When("Search with limit {int} and vacancy id {int} and sort order {string}")
	public void search_with_job_title_id_and_limit_and_sort_order(Integer id, Integer limit, String order) {
		  req.Search(id,limit,order);
	}
	
	
}
