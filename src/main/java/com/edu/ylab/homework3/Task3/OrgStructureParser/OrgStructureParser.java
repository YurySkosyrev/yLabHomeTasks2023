package com.edu.ylab.homework3.Task3.OrgStructureParser;

import com.edu.ylab.homework3.Task3.Entity.Employee;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    public Employee parseStructure(File csvFile) throws IOException;
}
