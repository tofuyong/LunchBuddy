import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, MinLengthValidator, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, min } from 'rxjs';
import { Employee } from 'src/app/models/employee';
import { Pairing } from 'src/app/models/pairing';
import { Request } from 'src/app/models/request';
import { EmployeeService } from 'src/app/services/employee.service';
import { HobbyService } from 'src/app/services/hobby.service';
import { PairingService } from 'src/app/services/pairing.service';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  employeeId!: string;
  employee!: Employee;
  lunchBuddy!: Employee;
  param$!: Subscription;
  requests!: Request[];
  hobbies!: [string];
  addHobbyForm!: FormGroup;
  errorMsg!: string;
  acceptedPairings: Pairing[] = [];
  pendingPairings: Pairing[] = [];
  pendingYourAcceptancePairings: Pairing[] = [];

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
      private reqSvc: RequestService, private empSvc: EmployeeService,
      private hobbySvc: HobbyService, private pairingSvc: PairingService,
      private fb: FormBuilder) { }

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
        // Upcoming Lunches
        this.pairingSvc.getAllAcceptedPairings(this.employeeId)
            .then(
              (response) => {
                const acceptedPairings = response;
                this.acceptedPairings = [];
                for (const pairing of acceptedPairings) {
                  // depending on whether employee is the former or later in the pair, the corresponding lunch buddy will be displayed
                  this.empSvc.getEmployee(pairing.employeeId == this.employeeId ? pairing.pairedEmployeeId : pairing.employeeId)
                    .then(
                      (employeeResponse: Employee) => {
                        const acceptedPairing = new Pairing(
                          pairing.pairingId, pairing.employeeId, pairing.pairedEmployeeId,
                          pairing.pairingDate, pairing.lunchDate, pairing.lunchTime,
                          pairing.lunchVenue, pairing.pairedEmployeeAccepted, employeeResponse // Pass the employee object
                        );
                        this.acceptedPairings.push(acceptedPairing);
                        console.log('Accepted Employee retrieved successfully.');
                      },
                      error => {
                        console.log('Error while retrieving Accepted Employee', error);
                      })
                };
                if (this.acceptedPairings.length == 0) {
                  console.log('No accepted Pairings at the moment.');
                }
              },
              error => {
                console.log('Error while retrieving Pairings', error);
                this.acceptedPairings = [];
              }
            )
        // Await Your Acceptance
        this.pairingSvc.getPendingYourAcceptancePairings(this.employeeId)
          .then(
            (response) => {
              const pendingYourAcceptancePairings = response;
              this.pendingYourAcceptancePairings = [];
              for (const pairing of pendingYourAcceptancePairings) {
                this.empSvc.getEmployee(pairing.employeeId) // Matched lunch buddy is the one represented by employeeId
                  .then(
                    (employeeResponse: Employee) => {
                      const pendingPairing = new Pairing(
                        pairing.pairingId, pairing.employeeId, pairing.pairedEmployeeId,
                        pairing.pairingDate, pairing.lunchDate, pairing.lunchTime,
                        pairing.lunchVenue, pairing.pairedEmployeeAccepted, employeeResponse // Pass the employee object
                      );
                      this.pendingYourAcceptancePairings.push(pendingPairing);
                      console.log('Employee retrieved successfully.');
                    },
                    error => {
                      console.log('Error while retrieving Employee', error);
                    })
              };
              if (this.pendingPairings.length == 0) {
                console.log('No Pairings pending your acceptance at the moment.');
              }
            },
            error => {
              console.log('Error while retrieving Pairings', error);
              this.pendingPairings = [];
            }
          )

        // Await Lunch Buddy Acceptance
        this.pairingSvc.getPendingLBPairings(this.employeeId)
          .then(
            (response) => {
              const pendingPairings = response;
              this.pendingPairings = [];
              for (const pairing of pendingPairings) {
                this.empSvc.getEmployee(pairing.pairedEmployeeId)
                  .then(
                    (employeeResponse: Employee) => {
                      const pendingPairing = new Pairing(
                        pairing.pairingId, pairing.employeeId, pairing.pairedEmployeeId,
                        pairing.pairingDate, pairing.lunchDate, pairing.lunchTime,
                        pairing.lunchVenue, pairing.pairedEmployeeAccepted, employeeResponse // Pass the employee object
                      );
                      this.pendingPairings.push(pendingPairing);
                      console.log('Pending Employee retrieved successfully.');
                    },
                    error => {
                      console.log('Error while retrieving Pending Employee', error);
                    })
              };
              if (this.pendingPairings.length == 0) {
                console.log('No pending Pairings at the moment.');
              }
            },
            error => {
              console.log('Error while retrieving Pairings', error);
              this.pendingPairings = [];
            }
          )

        // Open Requests
        this.reqSvc.getAllOpenRequests(this.employeeId)
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
              if (this.requests.length == 0) {
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
        const hobbyNames = existingHobbies.map((existingHobby: { hobby: any; }) => existingHobby.hobby);
        const hobbyExists = hobbyNames.includes(hobby.hobby);
        if (hobbyExists) {
          console.log('Hobby already exists in the database.');
          this.errorMsg = "Hobby already exists."
        } else {
          await this.hobbySvc.addHobby(hobby);
          console.log('Successfully added new hobby');
          this.hobbies.push(hobby.hobby);
          this.addHobbyForm.controls['hobby'].setValue('');
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
  
  acceptPairing(pairingId: any) {
    this.pairingSvc.updatePairedEmployeeAccepted(true, pairingId)
      .then(
        () => {
          console.log('Successfully updated paired employee accepted.');
        },
        error => {
          console.log('Error updating paired employee accepted', error);
        }
      );
    this.ngOnInit()
  }

}
