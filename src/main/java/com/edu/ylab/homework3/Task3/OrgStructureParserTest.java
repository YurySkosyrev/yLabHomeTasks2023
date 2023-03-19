package com.edu.ylab.homework3.Task3;

import com.edu.ylab.homework3.Task3.Entity.Employee;
import com.edu.ylab.homework3.Task3.OrgStructureParser.OrgStructureParser;
import com.edu.ylab.homework3.Task3.OrgStructureParser.impl.OrgStructureParserImpl;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParser parser = new OrgStructureParserImpl();
        File file = new File("Test.csv");


        Employee boss = parser.parseStructure(file);
        boss.getSubordinate().stream().map(e -> e.getName()).forEach(System.out::println);

        System.out.println(boss.getSubordinate().get(0).getSubordinate().get(0).getName());
    }
}
