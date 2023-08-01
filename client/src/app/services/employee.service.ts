import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private ADD_EMPLOYEE_URL = "api/employee/add";

  constructor(private httpClient: HttpClient) { }

  // Promise
  addEmployee(employee: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.ADD_EMPLOYEE_URL, employee, { responseType: 'json' }));
  }
}
