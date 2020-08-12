/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.repositories;

import com.MeetingSchedule.MeetingSchedule.entities.Employee;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Laila
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "select * from employee e where e.id = :id and e.password = :password", nativeQuery = true)
    Employee findLogin(@Param("id") String id, @Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO employee(id,name,password,role,departmentid,teamid) VALUES (?1,?2,?3,?4,?5,?6);", nativeQuery = true)
    void saveregister(String id, String name, String password, int role, String departmentid, String teamid);

}
