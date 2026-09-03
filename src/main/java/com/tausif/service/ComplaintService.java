package com.tausif.service;

import com.tausif.beans.Complaint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;

@Service
public class ComplaintService {

    @Value("${microWebservice.complaint}")
    private String complaintUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public boolean createComplaint(Complaint complaint, MultipartFile e1, MultipartFile e2, MultipartFile e3) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        //Complaint field
        body.add("username", complaint.getUsername());
        body.add("title", complaint.getTitle());
        body.add("category", complaint.getCategory());
        body.add("description", complaint.getDescription());
        body.add("incidentDate", complaint.getIncidentDate());
        body.add("latitude", complaint.getLatitude());
        body.add("longitude", complaint.getLongitude());
        body.add("location", complaint.getLocation());
        body.add("zipCode", complaint.getZipCode());

        //Evidence part handling

        body.add(
                "evidence1", new ByteArrayResource(e1.getBytes()){
                    @Override
                    public String getFilename(){
                        return e1.getOriginalFilename();
                    }
                }
        );

        //Evidence2 Handling
        if(e2 != null && !e2.isEmpty()){
            body.add(
                    "evidence2", new ByteArrayResource(e2.getBytes()){
                        @Override
                        public String getFilename(){
                            return e2.getOriginalFilename();
                        }
                    }
            );
        }

        if(e3 != null && !e3.isEmpty()){
            body.add(
                    "evidence3", new ByteArrayResource(e3.getBytes()){
                        @Override
                        public String getFilename(){
                            return e3.getOriginalFilename();
                        }
                    }
            );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.MULTIPART_FORM_DATA
        );

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(complaintUrl+"/registerComplain", request, Boolean.class);

    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> c = restTemplate.getForObject(complaintUrl+"/getAllComplaints", List.class);
        return c;
    }
}
