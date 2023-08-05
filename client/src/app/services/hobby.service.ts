import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HobbyService {

  private GET_HOBBIES_BY_EMP_URL = "api/hobby/all";

  constructor(private httpClient: HttpClient) { }

  getAllHobbies(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_HOBBIES_BY_EMP_URL, { params, responseType: 'json' }));
  }

}
