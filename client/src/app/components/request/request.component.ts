import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PairingService } from 'src/app/services/pairing.service';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit {
  requestForm!: FormGroup;
  employeeId!: String
  param$!: Subscription;

  constructor(private fb: FormBuilder, private router: Router,
    private activatedRoute: ActivatedRoute, private reqSvc: RequestService,
    private pairingSvc: PairingService) { }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.employeeId = params['employeeId'];
      }
    );
    this.requestForm = this.createForm();
  }

  createForm() {
    return this.fb.group({
      preferredDate: this.fb.control('', [Validators.required]),
      preferredTime: this.fb.control('', [Validators.required]),
      preferredGender: this.fb.control('', Validators.required),
      employeeId: this.employeeId
    });
  }

  onSubmit() {
    if (this.requestForm.valid) {
      const requestDetails = this.requestForm.value;
      this.reqSvc.addRequest(requestDetails)
        .then(
          (requestId) => {
            console.log('Successfully added new request: ', requestId);
            // Fetch the added request by requestId
            this.reqSvc.getRequestByRequestId(requestId)
              .then(
                (addedRequest) => {
                  // Find Match from existing pool of open Requests
                  this.pairingSvc.findMatch(addedRequest)
                    .then(
                      (pairing) => {
                        if (pairing.pairingId) {
                          console.log('Match found. PairingId: ', pairing.pairingId);
                          alert("Match found!");
                          this.router.navigate(['/dashboard', this.employeeId]);
                        } else {
                          console.log('No match found.');
                          this.router.navigate(['/dashboard', this.employeeId]);
                        }
                      })
                    .catch((pairingError) => {
                      console.log('Error finding a match:', pairingError);
                      this.router.navigate(['/dashboard', this.employeeId]);
                    });
                })
              .catch((getRequestError) => {
                console.log('Error retrieving added request:', getRequestError);
              });
          })
        .catch((error: any) => {
          if (error?.error == 'Conflicting Request Time') {
            alert('Conflict in timing with existing lunch request. Please select a different date or time.');
          } else {
            alert('Error adding request, please try again.');
          }
          console.log('Error while adding request:', error);
        });
    }
  }
}
