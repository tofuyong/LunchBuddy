import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Employee } from '../models/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private ADD_EMPLOYEE_URL = "api/employee/add";
  private GET_EMPLOYEE_URL = "api/employee/details/{employeeId}";

  constructor(private httpClient: HttpClient) { }

  addEmployee(employee: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.ADD_EMPLOYEE_URL, employee, { responseType: 'json' }));
  }

  getEmployee(employeeId: string): Promise<any> {
    const url = this.GET_EMPLOYEE_URL.replace("{employeeId}", employeeId);
    return firstValueFrom(this.httpClient.get<Employee>(url));
  } 
}
