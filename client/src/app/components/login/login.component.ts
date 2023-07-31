import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username!: string;
  password!: string;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    // Call authentication service to perform the login
    const loginSuccessful = this.authService.login(this.username, this.password);
    if (loginSuccessful) {
      this.router.navigate(['/dashboard']);
    } else {
      this.errorMessage = 'Invalid ID, please try again';
    }
  }
}
