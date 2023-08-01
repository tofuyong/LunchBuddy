import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signUpForm!: FormGroup;
  successMsg: string = "";

  constructor(private fb: FormBuilder, private empSvc: EmployeeService,
      private router: Router) { }

  ngOnInit(): void {
    this.signUpForm = this.createForm();
  }

  createForm() {
    return this.fb.group({
      employeeId: this.fb.control('', Validators.required ),
      isFinding: this.fb.control('false' ),
      firstName: this.fb.control('', Validators.required ),
      lastName: this.fb.control('', Validators.required ),
      salutation: this.fb.control('', Validators.required ),
      gender: this.fb.control('', Validators.required ),
      email: this.fb.control(''),
      department: this.fb.control('', Validators.required ),
      title: this.fb.control('', Validators.required )
    }); 
  }

  onSubmit() {
    if (this.signUpForm.valid) {
      const employeeDetails = this.signUpForm.value;
      this.empSvc.addEmployee(employeeDetails)
      .then(
        response => {
          console.log('Successfully added new user: ');
          this.successMsg = "Successfully added new user. Login with your employeeId."
        },
        error => {
          console.log('Error while sending to server', error);
          alert('Failed to add new user');
        }
      );
    } 
  }

  returnToLogin(){
    this.router.navigate(['/']);
  }

}
