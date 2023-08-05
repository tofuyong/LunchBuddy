import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private ADD_REQUEST_URL = "api/request/add";
  private GET_REQUESTS_BY_EMP_URL = "api/request/all";

  constructor(private httpClient: HttpClient) { }

  addRequest(request: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.ADD_REQUEST_URL, request, { responseType: 'json' }));
  }

  getAllRequests(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_REQUESTS_BY_EMP_URL, { params, responseType: 'json' }));
  }

}
