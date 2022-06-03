package com.zensar.AdmissionsService.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.AdmissionsService.model.patient.DiseasesList;
import com.zensar.AdmissionsService.model.patient.EmployeesList;
import com.zensar.AdmissionsService.model.patient.Patient;



@RestController
@RequestMapping("/admissions")
public class AdmissionsResource {
	
	@Autowired
	
	private RestTemplate restTemplate;
	
	//A hardcoded list of patients
	List<Patient> patients = Arrays.asList(				
		new Patient("P1", "Hulk", "Brazil"),
		new Patient("P2", "Thor", "American"),
		new Patient("P3", "Loki", "Indian")
		);
	
	//getPatients() returns a list of patients
	@RequestMapping("/patients")
	public List<Patient> getPatients() {
		return patients;
	}
	
	//getPatientById() returns a patient with a given Id
	@RequestMapping("/patients/{Id}")
	public Patient getPatientById(@PathVariable("Id") String Id) {
		Patient p = patients.stream()
				.filter(patient -> Id.equals(patient.getId()))
				.findAny()
				.orElse(null);
		return p;
	}
	
	
	//getPhysicians 
	@RequestMapping("/physicians")
	public EmployeesList getPhysicians() {
		EmployeesList physicians = 
				
				restTemplate.getForObject("http://hr-service/hr/employees", EmployeesList.class);
		return physicians;
	}
	
	//getDiseases 
	@RequestMapping("/diseases")
	public DiseasesList getDiseases() {
		DiseasesList diseases = 
				//restTemplate.getForObject("http://localhost:8083/pathology/diseases", DiseasesList.class);
				restTemplate.getForObject("http://pathology-service/pathology/diseases", DiseasesList.class);
		
		
				return diseases;
	}	
}