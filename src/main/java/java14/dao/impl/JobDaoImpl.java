package java14.dao.impl;

import java14.config.DatabaseConfig;
import java14.dao.JobDao;
import java14.enums.Position;
import java14.enums.Profession;
import java14.models.Job;

import java.sql.*;
import java.util.List;

public class JobDaoImpl implements JobDao,AutoCloseable {
    private final Connection connection = DatabaseConfig.getConnection();
    @Override
    public void createJobTable() {
        String sql = """
            create table if not exists job(
            id serial primary key,
            position varchar(50),
            profession varchar(50),
            description varchar(255),
            experience int
            )
            """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Job table created");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String sql = "insert into job(position,profession,description,experience)"+
                "values(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, job.getPosition().toString());
            preparedStatement.setString(2, job.getProfession().toString());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        String sql = """
                select * from job
                where id=?
                """;
        Job job = new Job();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())throw new RuntimeException("Job not found "+jobId);
            job.setId(resultSet.getLong("id"));
            job.setPosition(Position.valueOf(resultSet.getString("position")));
            job.setProfession(Profession.valueOf(resultSet.getString("profession")));
            job.setDescription(resultSet.getString("description"));
            job.setExperience(resultSet.getInt("experience"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return null;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return null;
    }

    @Override
    public void deleteDescriptionColumn() {

    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
