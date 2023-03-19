package com.edu.ylab.homework3.Task3.OrgStructureParser.impl;

import com.edu.ylab.homework3.Task3.Entity.Employee;
import com.edu.ylab.homework3.Task3.OrgStructureParser.OrgStructureParser;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {

        Map<Long, Employee> employees = new HashMap<>();
        Long findBossId = -1L;

        try (FileInputStream fileInputStream = new FileInputStream(csvFile);
             Scanner scanner = new Scanner(fileInputStream)){
            scanner.nextLine();

            while (scanner.hasNextLine()) {

                String stringFromFile = scanner.nextLine();
                String[] data = stringFromFile.split(";");

                Employee newEmployee = new Employee();

                Long employeeId = Long.parseLong(data[0]);
                Long bossId = data[1].equals("") ? null : Long.parseLong(data[1]);
                String name = data[2];
                String position = data[3];

                if(employees.containsKey(employeeId)) {
                    newEmployee = employees.get(employeeId);
                    newEmployee.setBossId(bossId);
                    newEmployee.setName(name);
                    newEmployee.setPosition(position);

                } else {
                    newEmployee.setId(employeeId);
                    newEmployee.setBossId(employeeId);
                    newEmployee.setName(name);
                    newEmployee.setPosition(position);

                    employees.put(employeeId, newEmployee);
                }


                if(!employees.containsKey(bossId)) {
                    Employee newBoss = new Employee();
                    newBoss.setId(bossId);
                    employees.put(bossId,newBoss);
                }
                employees.get(bossId).getSubordinate().add(newEmployee);

                if (bossId == null) {findBossId = employeeId;}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees.get(findBossId);
    }
}
