import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PairingService {

  private GET_ALL_ACCEPTED_PAIRINGS_URL = "api/pairing/allAccepted";
  private GET_PENDING_LB_PAIRINGS_URL = "api/pairing/allPending";
  private GET_PENDING_YOUR_ACCEPTANCE_PAIRINGS_URL = "api/pairing/allPendingYourAcceptance";
  private FIND_MATCH_URL = "api/pairing/findMatch";
  private UPDATE_PAIRED_EMPLOYEE_ACCEPTED_URL = "api/pairing/updatePairedEmployeeAccepted/{pairingId}";

  constructor(private httpClient: HttpClient) { }

  getAllAcceptedPairings(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_ALL_ACCEPTED_PAIRINGS_URL, { params, responseType: 'json' }));
  }

  getPendingLBPairings(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_PENDING_LB_PAIRINGS_URL, { params, responseType: 'json' }));
  }

  getPendingYourAcceptancePairings(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_PENDING_YOUR_ACCEPTANCE_PAIRINGS_URL, { params, responseType: 'json' }));
  }

  findMatch(request: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.FIND_MATCH_URL, request, { responseType: 'json' }));
  }

  updatePairedEmployeeAccepted(pairedEmployeeAccepted: boolean, pairingId: string): Promise<any> {
    const url = this.UPDATE_PAIRED_EMPLOYEE_ACCEPTED_URL.replace("{pairingId}", pairingId);
    return firstValueFrom(this.httpClient.put(url, pairedEmployeeAccepted, { responseType: 'json' }));
  } 

}
