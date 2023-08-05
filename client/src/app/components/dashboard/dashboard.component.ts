import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Request } from 'src/app/models/request';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  employeeId!: string;
  param$!: Subscription;
  requests!: Request[];

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
      private reqSvc: RequestService) { }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.employeeId = params['employeeId'];
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

}
