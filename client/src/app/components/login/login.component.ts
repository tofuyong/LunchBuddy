import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  employeeId!: string;
  // password!: string;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  async login() {
    try {
      const loginSuccessful = await this.authService.login(this.employeeId);
      if (loginSuccessful) {
        this.router.navigate(['/dashboard', this.employeeId]);
      } else {
        this.errorMessage = 'Invalid ID, please try again';
      }
    } catch (error) {
      this.errorMessage = 'An error occurred during login, please try again';
    }
  }

  signup(){
    this.router.navigate(['/signup']);
  }

}
