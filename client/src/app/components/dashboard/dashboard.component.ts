import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, MinLengthValidator, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, min } from 'rxjs';
import { Employee } from 'src/app/models/employee';
import { Request } from 'src/app/models/request';
import { EmployeeService } from 'src/app/services/employee.service';
import { HobbyService } from 'src/app/services/hobby.service';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  employeeId!: string;
  employee!: Employee;
  param$!: Subscription;
  requests!: Request[];
  hobbies!: [string];
  addHobbyForm!: FormGroup;
  errorMsg!: string;


  constructor(private router: Router, private activatedRoute: ActivatedRoute,
      private reqSvc: RequestService, private empSvc: EmployeeService,
      private hobbySvc: HobbyService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.employeeId = params['employeeId'];
        // Employee
        this.empSvc.getEmployee(this.employeeId)
          .then(
            (response: Employee) => {
              this.employee = response;
              console.log('Employee retrieved successfully.');
          },
          error => {
            console.log('Error while retrieving Employee', error);
          }
        );
        // Hobbies
        this.addHobbyForm = this.createForm();
        this.hobbySvc.getAllHobbies(this.employeeId)
          .then(
            (response) => {
              this.hobbies = response.map((hobby: { hobby: any; }) => hobby.hobby)
            },
            error => {
              console.log('Error while retrieving Hobbies', error);
            }
          )
        // Requests
        this.reqSvc.getAllRequests(this.employeeId)
          .then(
            (response) => {
              const requests = response;
              this.requests = [];
              for (const request of requests) {
                const req = new Request(
                  request.requestId, request.preferredDate, request.preferredTime, 
                  request.preferredGender, request.employeeId, request.isMatched
                );
                this.requests.push(req);
              }
              if (this.requests.length === 0) {
                console.log('No active requests at the moment.');
              }
            },
            error => {
              console.log('Error while retrieving Requests', error);
              this.requests = [];
            }
          )
      }
    );
  }

  newRequest() {
    this.router.navigate(['/request', this.employeeId]);
  }

  createForm() {
    return this.fb.group({
      hobby: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      employeeId: this.employeeId
    }); 
  }

  async onSubmit() {
    if (this.addHobbyForm.valid) {
      const hobby = this.addHobbyForm.value;
      try {
        const existingHobbies = await this.hobbySvc.getAllHobbies(this.employeeId);
        const hobbyExists = existingHobbies.includes(hobby.hobby);
        if (hobbyExists) {
          console.log('Hobby already exists in the database.');
          this.errorMsg = "Hobby already exists."
        } else {
          await this.hobbySvc.addHobby(hobby);
          console.log('Successfully added new hobby');
          this.hobbies.push(hobby.hobby);
          this.addHobbyForm.reset();
        }
      }
      catch (error) {
        console.log('Error while sending to server', error);
      }
    }
  }

  delete(hobby: any) {
    const index = this.hobbies.indexOf(hobby);
    if (index !== -1) {
    this.hobbies.splice(index, 1); // Remove hobby from the hobbies array
    this.hobbySvc.deleteHobby(hobby)
      .then(
        () => {
          console.log('Successfully deleted hobby from the database.');
        },
        error => {
          console.log('Error while deleting hobby from the database', error);
        }
      );
    }
  }
  

}
