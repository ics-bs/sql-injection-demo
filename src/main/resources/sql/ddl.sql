CREATE TABLE Department
(
    DepartmentID INTEGER IDENTITY(1,1),
    -- Surrogate key
    DeptName VARCHAR(100) NOT NULL,
    DeptBudget DECIMAL(19,2),

    CONSTRAINT PK_Department_DepartmentID PRIMARY KEY (DepartmentID),
    CONSTRAINT UQ_Department_DeptName UNIQUE (DeptName)
);

CREATE TABLE Employee
(
    EmployeeID INTEGER IDENTITY(1,1),
    -- Surrogate key
    EmpNo VARCHAR(10) NOT NULL,
    EmpName VARCHAR(50),
    EmpSalary DECIMAL(19,2),

    CONSTRAINT PK_Employee_ID PRIMARY KEY (EmployeeID),
    CONSTRAINT UQ_Employee_EmpNo UNIQUE (EmpNo),
);

CREATE TABLE Work
(
    EmployeeID INTEGER,
    DepartmentID INTEGER,
    StartDate DATE,
    CONSTRAINT PK_Work_EmployeeID_DepartmentID PRIMARY KEY (EmployeeID, DepartmentID),
    CONSTRAINT FK_Work_Employee_EmployeeID FOREIGN KEY (EmployeeID) 
        REFERENCES Employee(EmployeeID) ON DELETE CASCADE,
    CONSTRAINT FK_Work_Department_DepartmentID FOREIGN KEY (DepartmentID) 
        REFERENCES Department(DepartmentID)
);

-- Insert departments
INSERT INTO Department
    (DeptName, DeptBudget)
VALUES
    ('HR', 150000.00),
    ('IT', 250000.00),
    ('Sales', 200000.00);


