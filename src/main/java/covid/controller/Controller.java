package covid.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import covid.models.Week;
import covid.service.ApiService;

@RestController
public class Controller {

	@CrossOrigin
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<String> getCountryNames() throws IOException {
		List<String> nameList = new ArrayList<>(ApiService.covidCases.keySet());
		Collections.sort(nameList);
		return nameList;
	}

	@CrossOrigin
	@RequestMapping(value = "/weeklist", method = RequestMethod.GET)
	public List<Week> getCountryCases(@RequestParam String country) throws IOException {
		return ApiService.covidCases.get(country);
	}
}
