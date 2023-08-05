import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
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
    private activatedRoute: ActivatedRoute, private reqSvc: RequestService) { }
  
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
        response => {
          console.log('Successfully added new request: ');
        },
        error => {
          console.log('Error while sending to server', error);
        }
      );
    } 
    this.router.navigate(['/dashboard', this.employeeId]);
  }
}
