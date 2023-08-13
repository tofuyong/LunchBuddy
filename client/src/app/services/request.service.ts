import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private ADD_REQUEST_URL = "api/request/add";
  private GET_REQUEST_BY_REQUEST_ID = "api/request/details/{requestId}"
  private GET_REQUESTS_BY_EMP_URL = "api/request/all";
  private UPDATE_IS_MATCHED_TO_TRUE_URL = "api/request/updateMatch/{requestId}";

  constructor(private httpClient: HttpClient) { }

  addRequest(request: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.ADD_REQUEST_URL, request, { responseType: 'text' }));
  }

  getRequestByRequestId(requestId: string): Promise<any> {
    const url = this.GET_REQUEST_BY_REQUEST_ID.replace("{requestId}", requestId);
    return firstValueFrom(this.httpClient.get(url, { responseType: 'json' }));
  }

  getAllRequests(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_REQUESTS_BY_EMP_URL, { params, responseType: 'json' }));
  }

  updateRequestIsMatchedToTrue(isMatched: boolean, requestId: string): Promise<any> {
    const url = this.UPDATE_IS_MATCHED_TO_TRUE_URL.replace("{requestId}", requestId);
    return firstValueFrom(this.httpClient.put(url, isMatched, { responseType: 'json' }));
  } 

}
