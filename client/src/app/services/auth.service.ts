import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  errorMessage: string = '';
  private isLoggedIn: boolean = false;

  private GET_EMPLOYEE_URL = "api/employee/details/{employeeId}";

  constructor(private httpClient: HttpClient) { }

  getEmployee(username: string): Promise<any> {
    const url = this.GET_EMPLOYEE_URL.replace('{employeeId}', username);
    return firstValueFrom(this.httpClient.get(url, { responseType: 'json' }));
  }

  async login(username: string): Promise<boolean> {
    try {
      const response = await this.getEmployee(username); // function will pause at await keyword until the Promise returned is resolved or rejected
      this.isLoggedIn = true;
      return true;
    } catch (error) {
      this.errorMessage = 'Invalid username, please try again';
      return false;
    }
  }

  isLoggedInUser(): boolean {
    return this.isLoggedIn;
  }

  logout() {
    this.isLoggedIn = false;
  }
  
}
