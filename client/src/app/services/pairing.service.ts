import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PairingService {

  private GET_ALL_ACCEPTED_PAIRINGS_URL = "api/pairing/allAccepted";
  private FIND_MATCH_URL = "api/pairing/findMatch";

  constructor(private httpClient: HttpClient) { }

  getAllAcceptedPairings(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_ALL_ACCEPTED_PAIRINGS_URL, { params, responseType: 'json' }));
  }

  findMatch(request: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.FIND_MATCH_URL, request, { responseType: 'json' }));
  }

  
}
