package java14.service;

import java14.enums.Position;
import java14.enums.Profession;
import java14.models.Job;

import java.util.List;

public interface JobService {
    void createJobTable();
    void addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);
    void deleteDescriptionColumn();
}
