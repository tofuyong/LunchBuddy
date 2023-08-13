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
export class RequestComponent implements OnInit{
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
      preferredTime: this.fb.control('', [Validators.required] ),
      preferredGender: this.fb.control('', Validators.required ),
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
                  // Update Request's isMatched to true
                  this.reqSvc.updateRequestIsMatchedToTrue(true, requestId)
                    .then(
                      (updateResponse) => {
                        console.log('Request updated: isMatched set to true');
                        // Update Paired Employee's isMatched to true
                        this.reqSvc.updateRequestIsMatchedToTrue(true, pairing.pairedEmployeeId)
                          .then(
                            (updatePairedResponse) => {
                              console.log('Paired Employee request updated: isMatched set to true');
                              this.router.navigate(['/dashboard', this.employeeId]);
                            },
                            (updatePairedError) => {
                              console.log('Error updating paired employee request:', updatePairedError);
                            }
                          );
                      },
                      (updateError) => {
                        console.log('Error updating request:', updateError);
                      }
                    );
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
        .catch((error) => {
          console.log('Error while adding request', error);
          alert('Error adding request, please try again.')
        });
    } 
  }

}


// onSubmit() {
//   if (this.requestForm.valid) {
//     const requestDetails = this.requestForm.value;

//     // Create new request
//     this.reqSvc.addRequest(requestDetails)
//       .then(
//         (addResponse) => {
//           console.log('Successfully added new request: ');

//           // Find Match from existing pool of open Requests
//           this.pairingSvc.findMatch(requestDetails)
//             .then(
//               (matchResponse) => {
//                 if (matchResponse.pairingId) {
//                   console.log('Match found. PairingId: ', matchResponse.pairingId);
//                   alert("Match found!");

//                   // Update Request's isMatched to true
//                   this.reqSvc.updateRequestIsMatchedToTrue(true, addResponse.requestId)
//                     .then(
//                       (updateResponse) => {
//                         console.log('Request updated: isMatched set to true');
//                         // Update Paired Employee's isMatched to true
//                         this.reqSvc.updateRequestIsMatchedToTrue(true, matchResponse.pairedEmployeeId)
//                           .then(
//                             (updatePairedResponse) => {
//                               console.log('Paired Employee request updated: isMatched set to true');
//                               this.router.navigate(['/dashboard', this.employeeId]);
//                             },
//                             (updatePairedError) => {
//                               console.log('Error updating paired employee request:', updatePairedError);
//                             }
//                           );
//                       },
//                       (updateError) => {
//                         console.log('Error updating request:', updateError);
//                       }
//                     );
//                 } else {
//                   console.log('No match found.');
//                   this.router.navigate(['/dashboard', this.employeeId]);
//                 }
//               },
//               (matchError) => {
//                 console.log('Error while finding a match', matchError);
//               }
//             );
//         },
//         (addError) => {
//           console.log('Error while adding request', addError);
//         }
//       );
//   }
// }