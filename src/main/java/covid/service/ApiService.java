package covid.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import covid.models.Week;

@Service
public class ApiService {
	public static HashMap<String, List<Week>> covidCases;
	private HashMap<String, HashMap<String, Week>> tempMap = new HashMap<String, HashMap<String, Week>>();

	@PostConstruct
	public void prepareInfo() throws IOException {
		URL url = new URL("https://opendata.ecdc.europa.eu/covid19/nationalcasedeath/json/");
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		Gson g = new Gson();
		Type listType = new TypeToken<List<JsonObject>>() {}.getType();
		List<JsonObject> jsonObjectList = g.fromJson(in, listType);
		covidCases = new HashMap<String, List<Week>>();
		jsonObjectList.stream().forEach(x -> getInfoFromJson(x));
		for (String country : tempMap.keySet()) {
			for (String date : tempMap.get(country).keySet()) {
				List<Week> tempWeek = covidCases.getOrDefault(country, new ArrayList<Week>());
				tempWeek.add(tempMap.get(country).get(date));
				tempWeek.sort(Comparator.comparing(Week::getWeek));
				covidCases.put(country, tempWeek);
			}
		}
	}

	public void getInfoFromJson(JsonObject json) {
		HashMap<String, Week> tempWeekMap = tempMap.getOrDefault(json.get("country").getAsString(),
				new HashMap<String, Week>());
		Week tempWeek = tempWeekMap.getOrDefault(json.get("year_week").getAsString(), new Week());
		tempWeek.setWeek(json.get("year_week").getAsString());
		if (json.get("indicator").getAsString().equals("cases")) {
			tempWeek.setCases(json.get("weekly_count").getAsInt());
		} else {
			tempWeek.setDeaths(json.get("weekly_count").getAsInt());
		}
		tempWeekMap.put(json.get("year_week").getAsString(), tempWeek);
		tempMap.put(json.get("country").getAsString(), tempWeekMap);
	}
}
